package controllers;

import entities.Category;
import entities.Discount;
import entities.Dish;
import entities.NutritiveValue;
import entities.Price;
import entities.Tax;
import entities.Unit;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanCategory;
import models.beanDish;
import models.beanPrice;
import models.beanRate;

public class dishController implements IController {

    beanDish bd = new beanDish();
    beanCategory bc = new beanCategory();
    beanPrice bp = new beanPrice();
    beanRate br = new beanRate();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String url = "/WEB-INF/admin/dishList.jsp";

        Dish d = new Dish();
        Category c = new Category();
        Discount di = new Discount();
        Tax t = new Tax();
        Price p = new Price();
        List<NutritiveValue> lnv = new ArrayList();

        if ("edit".equals(request.getParameter("task"))) {
            url = "/WEB-INF/admin/dishEdit.jsp";

            session.removeAttribute("dish");

            if (request.getParameter("id") != null) {
                d = bd.findById(Long.valueOf(request.getParameter("id")));
                session.setAttribute("dish", d);
            }
        }
        if (request.getParameter("confirm") != null) {
            if (session.getAttribute("dish") == null) {
                // set Dish
                d.setName(request.getParameter("dishName"));
                d.setActive(true);
                d.setCountry(request.getParameter("dishCountry"));
                d.setDescription(request.getParameter("description"));
                d.setImage(request.getParameter("imageDish"));
                d.setInventory(Integer.valueOf(request.getParameter("dishInventory")));
                d.setWeight(Double.valueOf(request.getParameter("dishWeight")));

                // set category_id
                c.setId(Long.valueOf(request.getParameter("dishCategory")));
                c = bc.findById(c.getId());
                d.setCategory(c);
                
                // set price
                p.setPrice(Double.valueOf(request.getParameter("dishPrice")));
                d.setPrice(p);
                
                // set tax
                t.setId(Long.valueOf(request.getParameter("dishTax")));
                t = br.findTaxById(t.getId());
                d.setTax(t);
                
                // set discount
                
//                d.setDiscount(Double.valueOf(request.getParameter("dishDiscount")));
                
                // set nutritiveValue
                lnv.add(new NutritiveValue("kilocalories", Double.valueOf(request.getParameter("dishKcal")), Unit.KiloCalories));
                lnv.add(new NutritiveValue("protéines", Double.valueOf(request.getParameter("dishProtein")), Unit.Grammes));
                lnv.add(new NutritiveValue("glucides", Double.valueOf(request.getParameter("dishGlucid")), Unit.Grammes));
                lnv.add(new NutritiveValue("lipides", Double.valueOf(request.getParameter("dishLipid")), Unit.Grammes));
                d.setNutritiveValues(lnv);
                
                bd.create(d);
            } else {
                d = (Dish) session.getAttribute("dish");
                d.setName(request.getParameter("dishName"));
                c.setId(Long.valueOf(request.getParameter("dishCategory")));
                c = bc.findById(c.getId());
                d.setCategory(c);
                d.setCountry(request.getParameter("dishCountry"));
                d.setDescription(request.getParameter("description"));
//                d.setDiscount(Double.valueOf(request.getParameter("dishDiscount")));
                d.setImage(url);
                d.setInventory(Integer.valueOf(request.getParameter("dishInventory")));
//                d.setPrice(null);
//                d.setTax(null);
                d.setWeight(Double.valueOf(request.getParameter("dishWeight")));
//                d.setNutritiveValues(null);
                bd.update(d);
                session.removeAttribute("dish");
            }
        }

        if ("delete".equals(request.getParameter("task"))) {
            d = bd.findById(Long.valueOf(request.getParameter("id")));
            bd.delete(d);
            url = "/WEB-INF/admin/dishList.jsp";

        }
        request.setAttribute("taxes", br.findAllTaxes());
        request.setAttribute("categories", bc.findAll());
        request.setAttribute("dishes", bd.findAll());
        return url;
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
