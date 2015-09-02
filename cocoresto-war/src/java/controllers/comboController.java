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
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanCategory;
import models.beanCombo;
import models.beanDish;
import models.beanRate;

public class comboController implements IController {

    beanCombo bco = new beanCombo();
    beanRate br = new beanRate();
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
                    for (Iterator it = co.getDishes().iterator(); it.hasNext();) {
                        Dish dish = (Dish) it.next();
                        request.setAttribute("dish" + i, dish);
                        System.out.println(dish);
                        System.out.println(i);
                        i++;
                    }
                }
            }
            if (request.getParameter("confirm") != null) {
                if (request.getParameter("id").isEmpty()) { // create

                } else { // update
                    co.setId(Long.valueOf(request.getParameter("id")));
                    co.setName(request.getParameter("comboName"));
                    co.setActive(true);

                }
            }

            request.setAttribute("dishes", bd.findAll());
            request.setAttribute("taxes", br.findAllTaxes());
            request.setAttribute("categories", bco.findCategories());
            getList(request);
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

    private void getList(HttpServletRequest request) {
        int max = 10;
        int currentPage = 1;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.valueOf(request.getParameter("page"));
            } catch (NumberFormatException e) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "La page n'est pas un nombre", "danger"));
            }
        }
        Pagination pagination = new Pagination("combo", currentPage, max, bco.count());
        request.setAttribute("pagination", pagination.getPagination());

        List<Combo> combos = bco.findAllByRange(pagination.getMin(), max);
        request.setAttribute("combos", combos);
    }

}
