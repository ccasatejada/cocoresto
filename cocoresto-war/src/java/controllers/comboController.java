package controllers;

import entities.Category;
import entities.Combo;
import entities.Discount;
import entities.Dish;
import entities.Price;
import entities.Tax;
import helpers.Alert;
import helpers.Pagination;
import java.io.IOException;
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
import models.beanCombo;
import models.beanDish;
import models.beanPrice;
import models.beanRate;

public class comboController implements IController {

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    beanCombo bco = new beanCombo();
    beanRate br = new beanRate();
    beanPrice bp = new beanPrice();
    beanCategory bc = new beanCategory();
    beanDish bd = new beanDish();

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
            String url = "/WEB-INF/admin/comboList.jsp";

            Combo co = new Combo();
            Category c = new Category();
            Discount di = new Discount();
            Dish d = new Dish();
            Tax t = new Tax();
            Price p = new Price();

            if ("edit".equals(request.getParameter("task"))) {
                url = "/WEB-INF/admin/comboEdit.jsp";
                if (request.getParameter("id") != null) {
                    co = bco.findById(Long.valueOf(request.getParameter("id")));
                    request.setAttribute("combo", co);
                    int i = 1;
                    for (Dish dish : co.getDishes()) {
                        request.setAttribute("dish" + i, dish);
                        i++;
                    }
                }
            }
            if (request.getParameter("confirm") != null) {
                if (request.getParameter("id").isEmpty()) { // create
                    // set combo
                    co.setName(request.getParameter("nameCombo"));
                    co.setActive(true);

                    // set dish
                    co.setDishes(new ArrayList());
                    try {
                        for (int i = 1; i <= 3; i++) {
                            if (!request.getParameter("comboDish" + i).trim().isEmpty()) {
                                Long id = Long.valueOf(request.getParameter("comboDish" + i));
                                Dish dish = bd.findById(id);
                                co.getDishes().add(dish);
                            }
                        }
                    } catch (EJBException ex) {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Vous devez sélectionner des plats différents", "danger"));
                    }

                    // set category_id
                    c.setId(Long.valueOf(request.getParameter("comboCategory")));
                    c = bc.findById(c.getId());
                    co.setCategory(c);

                    // set price
                    p.setPrice(Double.valueOf(request.getParameter("comboPrice")));
                    try {
                        p = bp.findByValue(p.getPrice());
                        co.setPrice(p);
                    } catch (EJBException ex) {
                        co.setPrice(p);
                        bco.create(co);
                    }

                    // set tax
                    t.setId(Long.valueOf(request.getParameter("comboTax")));
                    t = br.findTaxById(t.getId());
                    co.setTax(t);

                    // set discount
                    if (request.getParameter("comboDiscount").trim().isEmpty() || request.getParameter("comboBeginDiscount").trim().isEmpty()
                            || request.getParameter("comboEndDiscount").trim().isEmpty()) {
                        co.setDiscount(null);
                    } else {
                        di.setRate(Double.valueOf(request.getParameter("comboDiscount")));

                        try {
                            di.setBeginDate(formatter.parse(request.getParameter("comboBeginDiscount")));
                            di.setEndDate(formatter.parse(request.getParameter("comboEndDiscount")));
                        } catch (ParseException ex) {
                            request.setAttribute("alert", Alert.setAlert("Erreur", "Le format de la Date rencontre une erreur", "danger"));
                        }

                        try {
                            di = br.findByDates(di.getRate(), di.getBeginDate(), di.getEndDate());
                            co.setDiscount(di);
                        } catch (EJBException ex) {
                            co.setDiscount(di);
                            br.create(di);
                        }
                    }

                    bco.create(co);
                } else { // update
                    co.setId(Long.valueOf(request.getParameter("id")));
                    co = bco.findById(co.getId());
                    co.setName(request.getParameter("comboName"));
                    co.setActive(true);

                    // set dish
                    co.setDishes(new ArrayList());
                    try {
                        for (int i = 1; i <= 3; i++) {
                            if (!request.getParameter("comboDish" + i).trim().isEmpty()) {
                                Long id = Long.valueOf(request.getParameter("comboDish" + i));
                                Dish dish = bd.findById(id);
                                co.getDishes().add(dish);
                            }
                        }
                    } catch (EJBException ex) {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Vous devez sélectionner des plats différents", "danger"));
                    }

                    // set category_id
                    c.setId(Long.valueOf(request.getParameter("comboCategory")));
                    c = bc.findById(c.getId());
                    co.setCategory(c);

                    // set price
                    p.setPrice(Double.valueOf(request.getParameter("comboPrice")));
                    try {
                        p = bp.findByValue(p.getPrice());
                        co.setPrice(p);
                    } catch (EJBException ex) {
                        co.setPrice(p);
                        bco.create(co);
                    }

                    // set tax
                    t.setId(Long.valueOf(request.getParameter("comboTax")));
                    t = br.findTaxById(t.getId());
                    co.setTax(t);

                    // set discount
                    if (request.getParameter("comboDiscount").trim().isEmpty() || request.getParameter("comboBeginDiscount").trim().isEmpty()
                            || request.getParameter("comboEndDiscount").trim().isEmpty()) {
                        co.setDiscount(null);
                    } else {
                        di.setRate(Double.valueOf(request.getParameter("comboDiscount")));

                        try {
                            di.setBeginDate(formatter.parse(request.getParameter("comboBeginDiscount")));
                            di.setEndDate(formatter.parse(request.getParameter("comboEndDiscount")));
                        } catch (ParseException ex) {
                            request.setAttribute("alert", Alert.setAlert("Erreur", "Le format de la Date rencontre une erreur", "danger"));
                        }

                        try {
                            di = br.findByDates(di.getRate(), di.getBeginDate(), di.getEndDate());
                            co.setDiscount(di);
                        } catch (EJBException ex) {
                            co.setDiscount(di);
                            br.create(di);
                        }
                    }

                    bco.update(co);
                }
            }

            if ("delete".equals(request.getParameter("task"))) {
                try {
                    co = bco.findById(Long.valueOf(request.getParameter("id")));
                    bco.delete(co);
                    request.setAttribute("alert", Alert.setAlert("Succès", "Le menu a été supprimé", "succes"));
                } catch (NumberFormatException | EJBException e) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Ce menu n'existe pas", "danger"));
                }

                url = "/WEB-INF/admin/comboList.jsp";
            }

            request.setAttribute("dishes", bd.findAll());
            request.setAttribute("taxes", br.findAllTaxes());
            request.setAttribute("categories", bco.findCategories());
            getList(request, "option=combo");
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
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response
    ) {
        return null;
    }

    private void getList(HttpServletRequest request, String queryString) {

        Pagination pagination = new Pagination(queryString, request.getParameter("page"), 10, bco.count());
        request.setAttribute("pagination", pagination.getPagination());

        List<Combo> combos = bco.findAllByRange(pagination.getMin(), 10);
        request.setAttribute("combos", combos);
    }

}
