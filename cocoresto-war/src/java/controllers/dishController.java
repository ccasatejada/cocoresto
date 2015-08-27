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
import models.beanCategory;
import models.beanDish;
import models.beanNutritiveValue;
import models.beanPrice;
import models.beanRate;

public class dishController implements IController {

    beanDish bd = new beanDish();
    beanCategory bc = new beanCategory();
    beanPrice bp = new beanPrice();
    beanRate br = new beanRate();
    beanNutritiveValue bnv = new beanNutritiveValue();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String url = "/WEB-INF/admin/dishList.jsp";

        Dish d = new Dish();
        Category c = new Category();
        Discount di = new Discount();
        Tax t = new Tax();
        Price p = new Price();
        List<NutritiveValue> lnv = new ArrayList();

        if ("edit".equals(request.getParameter("task"))) {
            url = "/WEB-INF/admin/dishEdit.jsp";

            if (request.getParameter("id") != null) {
                d = bd.findById(Long.valueOf(request.getParameter("id")));
                request.setAttribute("dish", d);
            }
        }
        if (request.getParameter("confirm") != null) {
            if (request.getParameter("id").isEmpty()) { //create
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
                bp.create(p); // or update
                d.setPrice(p);

                // set tax
                t.setId(Long.valueOf(request.getParameter("dishTax")));
                t = br.findTaxById(t.getId());
                d.setTax(t);

                bd.create(d);

                // set discount
                // set nutritiveValue
                NutritiveValue nv1 = new NutritiveValue("kilocalories", Double.valueOf(request.getParameter("dishKcal")), Unit.KiloCalories);
                NutritiveValue nv2 = new NutritiveValue("protéines", Double.valueOf(request.getParameter("dishProtein")), Unit.Grammes);
                NutritiveValue nv3 = new NutritiveValue("glucides", Double.valueOf(request.getParameter("dishGlucid")), Unit.Grammes);
                NutritiveValue nv4 = new NutritiveValue("lipides", Double.valueOf(request.getParameter("dishLipid")), Unit.Grammes);
                nv1.setDish(d);
                nv2.setDish(d);
                nv3.setDish(d);
                nv4.setDish(d);
                bnv.create(nv1);
                bnv.create(nv2);
                bnv.create(nv3);
                bnv.create(nv4);
//                lnv.add(nv1);
//                lnv.add(nv2);
//                lnv.add(nv3);
//                lnv.add(nv4);
//                d.setNutritiveValues(lnv);
//
//                bd.create(d);
            } else { //update
                d.setId(Long.valueOf(request.getParameter("id")));
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
                // set nutritiveValue
                for (NutritiveValue nv : bnv.findByDish(d)) {
                    if ("kilocalories".equals(nv.getName())) {
                        nv.setQuantity(Double.valueOf(request.getParameter("dishKcal")));
                    }
                    if ("protéines".equals(nv.getName())) {
                        nv.setQuantity(Double.valueOf(request.getParameter("dishProtein")));
                    }
                    if ("glucides".equals(nv.getName())) {
                        nv.setQuantity(Double.valueOf(request.getParameter("dishGlucid")));
                    }
                    if ("lipides".equals(nv.getName())) {
                        nv.setQuantity(Double.valueOf(request.getParameter("dishLipid")));
                    }

                    lnv.add(nv);
                }

                d.setNutritiveValues(lnv);

                bd.update(d);
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
