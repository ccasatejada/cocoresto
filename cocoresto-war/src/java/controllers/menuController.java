package controllers;

import entities.Combo;
import entities.Dish;
import entities.Drink;
import helpers.Alert;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanCombo;
import models.beanDish;
import models.beanDrink;

public class menuController implements IController {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        boolean logged = false;
        Long groupId = 0L;

        if (session.getAttribute("logged") != null && session.getAttribute("group") != null) {
            logged = (boolean) session.getAttribute("logged");
            groupId = (Long) session.getAttribute("group");
        }

        if (logged && groupId < 2) {

            if ("recap".equals(request.getParameter("task"))) {

                boolean emptyCart = true;

                // load carts in session
                if (session.getAttribute("cartDishes") != null) {
                    List<Dish> cartDishes = (List<Dish>) session.getAttribute("cartDishes");
                    request.setAttribute("cartDishes", cartDishes);
                    if (cartDishes.size() > 0) {
                        emptyCart = false;
                    }
                }
                if (session.getAttribute("cartDrinks") != null) {
                    List<Drink> cartDrinks = (List<Drink>) session.getAttribute("cartDrinks");
                    request.setAttribute("cartDrinks", cartDrinks);
                    if (cartDrinks.size() > 0) {
                        emptyCart = false;
                    }
                }
                if (session.getAttribute("cartCombos") != null) {
                    List<Combo> cartCombos = (List<Combo>) session.getAttribute("cartCombos");
                    request.setAttribute("cartCombos", cartCombos);
                    if (cartCombos.size() > 0) {
                        emptyCart = false;
                    }
                }

                if (emptyCart) {
                    redirectToDashboard(request, response);
                }

                return "/WEB-INF/menu/recap.jsp";

            }

            if ("detail".equals(request.getParameter("task")) && request.getParameter("type") != null && request.getParameter("id") != null) {

                if ("Plat".equals(request.getParameter("type"))) {
                    beanDish bdish = new beanDish();
                    try {
                        Dish d = bdish.findById(Long.valueOf(request.getParameter("id")));
                        request.setAttribute("dish", d);
                    } catch (NumberFormatException | EJBException e) {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Cet item n'existe pas", "danger"));
                    }
                    return "/WEB-INF/menu/dishDetail.jsp";
                }

                if ("Boisson".equals(request.getParameter("type"))) {
                    beanDrink bdrink = new beanDrink();
                    try {
                        Drink d = bdrink.findById(Long.valueOf(request.getParameter("id")));
                        request.setAttribute("drink", d);
                    } catch (NumberFormatException | EJBException e) {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Cet item n'existe pas", "danger"));
                    }
                    return "/WEB-INF/menu/drinkDetail.jsp";
                }

                if ("Menu".equals(request.getParameter("type"))) {
                    beanCombo bcombo = new beanCombo();
                    try {
                        Combo c = bcombo.findById(Long.valueOf(request.getParameter("id")));
                        request.setAttribute("combo", c);
                    } catch (NumberFormatException | EJBException e) {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Ce menu n'existe pas", "danger"));
                    }
                    return "/WEB-INF/menu/comboDetail.jsp";
                }

            }
        }

        return "/WEB-INF/error.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    private void redirectToDashboard(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("FrontController?option=dashboard");
        } catch (IOException | IllegalStateException ex) {
            request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));
        }
    }
}
