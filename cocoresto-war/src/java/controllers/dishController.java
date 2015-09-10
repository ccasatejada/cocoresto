package controllers;

import entities.Category;
import entities.Discount;
import entities.Dish;
import entities.NutritiveValue;
import entities.Price;
import entities.Tax;
import entities.Unit;
import helpers.Alert;
import helpers.Pagination;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                    if (!request.getParameter("dishCountry").trim().isEmpty()) {
                        d.setCountry(request.getParameter("dishCountry"));
                    }
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
                    if (request.getParameter("dishDiscount").trim().isEmpty() || request.getParameter("dishBeginDiscount").trim().isEmpty()
                            || request.getParameter("dishEndDiscount").trim().isEmpty()) {

                        d.setDiscount(null);
                    } else {
                        di.setRate(Double.valueOf(request.getParameter("dishDiscount")));

                        try {
                            di.setBeginDate(formatter.parse(request.getParameter("dishBeginDiscount")));
                            di.setEndDate(formatter.parse(request.getParameter("dishEndDiscount")));
                        } catch (ParseException ex) {
                            request.setAttribute("alert", Alert.setAlert("Erreur", "Le format de la Date rencontre une erreur", "danger"));
                        }

                        try {
                            di = br.findByDates(di.getRate(), di.getBeginDate(), di.getEndDate());
                            d.setDiscount(di);
                        } catch (EJBException ex) {
                            d.setDiscount(di);
                            br.create(di);
                        }
                    }

                    // set image
                    d.setImage(request.getParameter("imageDish"));

                    // create dish
                    bd.create(d);

                    // set nutritiveValue
                    NutritiveValue nv1;
                    NutritiveValue nv2;
                    NutritiveValue nv3;
                    NutritiveValue nv4;
                    if (!request.getParameter("dishKcal").trim().isEmpty()) {
                        nv1 = new NutritiveValue("kilocalories", Double.valueOf(request.getParameter("dishKcal")), Unit.KiloCalories);
                    } else {
                        nv1 = new NutritiveValue("kilocalories", 0d, Unit.KiloCalories);
                    }
                    if (!request.getParameter("dishProtein").trim().isEmpty()) {
                        nv2 = new NutritiveValue("protéines", Double.valueOf(request.getParameter("dishProtein")), Unit.Grammes);
                    } else {
                        nv2 = new NutritiveValue("protéines", 0d, Unit.Grammes);
                    }
                    if (!request.getParameter("dishGlucid").trim().isEmpty()) {
                        nv3 = new NutritiveValue("glucides", Double.valueOf(request.getParameter("dishGlucid")), Unit.Grammes);
                    } else {
                        nv3 = new NutritiveValue("glucides", 0d, Unit.Grammes);
                    }
                    if (!request.getParameter("dishLipid").trim().isEmpty()) {
                        nv4 = new NutritiveValue("lipides", Double.valueOf(request.getParameter("dishLipid")), Unit.Grammes);
                    } else {
                        nv4 = new NutritiveValue("lipides", 0d, Unit.Grammes);
                    }
                    nv1.setDish(d);
                    nv2.setDish(d);
                    nv3.setDish(d);
                    nv4.setDish(d);
                    bnv.create(nv1);
                    bnv.create(nv2);
                    bnv.create(nv3);
                    bnv.create(nv4);

                    request.setAttribute("alert", Alert.setAlert("Succès", "Le plat a été ajouté", "success"));
                } else { //update
                    d.setId(Long.valueOf(request.getParameter("id")));
                    d = bd.findById(d.getId());
                    d.setName(request.getParameter("dishName"));
                    d.setActive(true);
                    if (!request.getParameter("dishCountry").trim().isEmpty()) {
                        d.setCountry(request.getParameter("dishCountry"));
                    }
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
                    if (!request.getParameter("dishDiscount").trim().isEmpty()) {
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
                    }

                    // set image
                    if (request.getParameter("imageDish") != null) {
                        System.out.println("imagedish " +request.getParameter("imageDish"));
                        d.setImage(request.getParameter("imageDish"));
                    } 
                    else {
                        System.out.println("attach image " + request.getParameter("attachedImage"));
                        System.out.println("d get image " + bd.findById(d.getId()).getImage());
                        d.setImage(bd.findById(d.getId()).getImage());
                    }

                    // update dish
                    bd.update(d);

                    // set nutritiveValue
                    for (NutritiveValue nv : bnv.findByDish(d)) {
                        if ("kilocalories".equals(nv.getName())) {
                            if (request.getParameter("dishKcal").trim().isEmpty()) {
                                nv.setQuantity(0d);
                            } else {
                                nv.setQuantity(Double.valueOf(request.getParameter("dishKcal")));
                            }
                            nv.setDish(d);
                        }
                        if ("protéines".equals(nv.getName())) {
                            if (request.getParameter("dishProtein").trim().isEmpty()) {
                                nv.setQuantity(0d);
                            } else {
                                nv.setQuantity(Double.valueOf(request.getParameter("dishProtein")));
                            }
                            nv.setDish(d);
                        }
                        if ("glucides".equals(nv.getName())) {
                            if (request.getParameter("dishGlucid").trim().isEmpty()) {
                                nv.setQuantity(0d);
                            } else {
                                nv.setQuantity(Double.valueOf(request.getParameter("dishGlucid")));
                            }
                            nv.setDish(d);
                        }
                        if ("lipides".equals(nv.getName())) {
                            if (request.getParameter("dishLipid").trim().isEmpty()) {
                                nv.setQuantity(0d);
                            } else {
                                nv.setQuantity(Double.valueOf(request.getParameter("dishLipid")));
                            }
                            nv.setDish(d);
                        }
                        bnv.update(nv);
                    }

                    request.setAttribute("alert", Alert.setAlert("Succès", "Le plat a été mis à jour", "success"));
                }
            }

            if ("delete".equals(request.getParameter("task"))) {
                try {
                    d = bd.findById(Long.valueOf(request.getParameter("id")));

                    // delete nutritiveValue
                    for (NutritiveValue nv : bnv.findByDish(d)) {
                        bnv.delete(nv);
                    }
                    // delete dish
                    bd.delete(d);
                    request.setAttribute("alert", Alert.setAlert("Succès", "Le plat a été supprimé", "success"));
                } catch (NumberFormatException | EJBException e) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Ce plat n'existe pas", "danger"));
                }
                url = "/WEB-INF/admin/dishList.jsp";

            }

            request.setAttribute("taxes", br.findAllTaxes());
            request.setAttribute("categories", bd.findCategories());
            getList(request, "option=dish");
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

    private void getList(HttpServletRequest request, String queryString) {

        Pagination pagination = new Pagination(queryString, request.getParameter("page"), 10, bd.count());
        request.setAttribute("pagination", pagination.getPagination());

        List<Dish> dishes = bd.findAllByRange(pagination.getMin(), 10);
        request.setAttribute("dishes", dishes);
    }

}
