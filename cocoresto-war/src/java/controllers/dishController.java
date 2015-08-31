package controllers;

import entities.Category;
import entities.Discount;
import entities.Dish;
import entities.NutritiveValue;
import entities.Price;
import entities.Tax;
import entities.Unit;
import helpers.Alert;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanCategory;
import models.beanDish;
import models.beanNutritiveValue;
import models.beanPrice;
import models.beanRate;

public class dishController implements IController {

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    beanDish bd = new beanDish();
    beanCategory bc = new beanCategory();
    beanPrice bp = new beanPrice();
    beanRate br = new beanRate();
    beanNutritiveValue bnv = new beanNutritiveValue();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        boolean logged = false;
        Long groupId = 0L;

        if (session.getAttribute("logged") != null && session.getAttribute("group") != null) {
            logged = (boolean) session.getAttribute("logged");
            groupId = (Long) session.getAttribute("group");
        }

        if (logged && groupId >= 3) {

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
                    try {
                        request.setAttribute("nutritiveValue", bnv.findByDish(d));
                    } catch (EJBException e) {
                        request.setAttribute("nutritiveValue", null);
                    }
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
                    try {
                        p = bp.findByValue(p.getPrice());
                        d.setPrice(p);
                    } catch (EJBException ex) {
                        d.setPrice(p);
                        bp.create(p);
                    }

                    // set tax
                    t.setId(Long.valueOf(request.getParameter("dishTax")));
                    t = br.findTaxById(t.getId());
                    d.setTax(t);

                    // set discount
                    di.setRate(Double.valueOf(request.getParameter("dishDiscount")));

                    try {
                        di.setBeginDate(formatter.parse(request.getParameter("dishBeginDiscount")));
                        di.setEndDate(formatter.parse(request.getParameter("dishEndDiscount")));
                    } catch (ParseException ex) {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Le format rencontre une erreur", "danger"));
                    }

                    try {
                        di = br.findByDates(di.getRate(), di.getBeginDate(), di.getEndDate());
                        d.setDiscount(di);
                    } catch (EJBException ex) {
                        d.setDiscount(di);
                        br.create(di);
                    }

                    // create dish
                    bd.create(d);

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
//                    lnv.add(nv1);
//                    lnv.add(nv2);
//                    lnv.add(nv3);
//                    lnv.add(nv4);
//                    d.setNutritiveValues(lnv);
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
                    try {
                        p = bp.findByValue(p.getPrice());
                        d.setPrice(p);
                    } catch (EJBException ex) {
                        d.setPrice(p);
                        bp.create(p);
                    }

                    // set tax
                    t.setId(Long.valueOf(request.getParameter("dishTax")));
                    t = br.findTaxById(t.getId());
                    d.setTax(t);

                    // set discount
                    di.setRate(Double.valueOf(request.getParameter("dishDiscount")));

                    try {
                        di.setBeginDate(formatter.parse(request.getParameter("dishBeginDiscount")));
                        di.setEndDate(formatter.parse(request.getParameter("dishEndDiscount")));
                    } catch (ParseException ex) {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Le format rencontre une erreur", "danger"));
                    }

                    try {
                        di = br.findByDates(di.getRate(), di.getBeginDate(), di.getEndDate());
                        d.setDiscount(di);
                    } catch (EJBException ex) {
                        d.setDiscount(di);
                        br.create(di);
                    }

                    // update dish
                    bd.update(d);

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
                        bnv.update(nv);
                    }

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

        } else {
            try {
                response.sendRedirect("FrontController?option=dashboard");
            } catch (IOException ex) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));
            }
        }

        return "/WEB-INF/index.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
