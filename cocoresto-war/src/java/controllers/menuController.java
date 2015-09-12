package controllers;

import ejb.ejbRestaurant;
import ejb.ejbRestaurantLocal;
import entities.Combo;
import entities.CustomerOrder;
import entities.Dish;
import entities.Drink;
import helpers.Alert;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanCombo;
import models.beanDish;
import models.beanDrink;

public class menuController implements IController {
    ejbRestaurantLocal ejbRestaurant = lookupejbRestaurantLocal();

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

                // init cart lists
                List<Dish> cartDishes = null;
                List<Drink> cartDrinks = null;
                List<Combo> cartCombos = null;

                if (session.getAttribute("cartDishes") != null) {
                    cartDishes = (List<Dish>) session.getAttribute("cartDishes");
                }
                if (session.getAttribute("cartDrinks") != null) {
                    cartDrinks = (List<Drink>) session.getAttribute("cartDrinks");
                }
                if (session.getAttribute("cartCombos") != null) {
                    cartCombos = (List<Combo>) session.getAttribute("cartCombos");
                }

                // control empty cart
                boolean emptyCart = true;

                if (cartDishes != null && cartDishes.size() > 0) {
                    emptyCart = false;
                }
                if (cartDrinks != null && cartDrinks.size() > 0) {
                    emptyCart = false;
                }
                if (cartCombos != null && cartCombos.size() > 0) {
                    emptyCart = false;
                }

                if (emptyCart) {
                    redirectToDashboard(request, response);
                }

                // cart validation and customerorder setting
                if (request.getParameter("confirm") != null) {

                    //get order
                    CustomerOrder co = ejbRestaurant.getOrder(Integer.valueOf(session.getAttribute("table").toString()));

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

    private ejbRestaurantLocal lookupejbRestaurantLocal() {
        try {
            Context c = new InitialContext();
            return (ejbRestaurantLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbRestaurant!ejb.ejbRestaurantLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
