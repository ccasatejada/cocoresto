package controllers;

import entities.Dish;
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

        if ("edit".equals(request.getParameter("task"))) {
            url = "/WEB-INF/admin/dishEdit.jsp";
            if (request.getParameter("id") != null) {
                d = bd.findById(Long.valueOf(request.getParameter("id")));
                session.setAttribute("dish", d);
            }
        }
        if (request.getParameter("confirm") != null) {
            if (session.getAttribute("dish") == null) {
                d.setName(request.getParameter("dishName"));
                d.setActive(true);
                d.setCategory(bc.findById(Long.valueOf(request.getParameter("category"))));
                d.setCountry(request.getParameter("dishCountry"));
                d.setDescription(request.getParameter("description"));
//                d.setDiscount(Double.valueOf(request.getParameter("dishDiscount")));
                d.setImage(url);
                d.setInventory(Integer.valueOf(request.getParameter("dishInventory")));
//                d.setPrice(null);
//                d.setTax(null);
                d.setWeight(Double.valueOf(request.getParameter("dishWeight")));
//                d.setNutritiveValues(null);
                bd.create(d);
            } else {
                d = (Dish) session.getAttribute("dish");
                d.setName(request.getParameter("dishName"));
                d.setCategory(bc.findById(Long.valueOf(request.getParameter("category"))));
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
