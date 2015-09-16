package controllers;

import ejb.ejbRestaurantLocal;
import entities.Combo;
import entities.ComboOrderLine;
import entities.CustomerOrder;
import entities.Dish;
import entities.DishOrderLine;
import entities.Drink;
import entities.DrinkOrderLine;
import helpers.Alert;
import java.io.IOException;
import java.util.ArrayList;
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
import models.beanOrderCustomer;

public class menuController implements IController {

    ejbRestaurantLocal ejbRestaurant = lookupejbRestaurantLocal();
    private final beanOrderCustomer boc = new beanOrderCustomer();

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
                
                //get current order
                CustomerOrder co = ejbRestaurant.getOrder(Integer.valueOf(session.getAttribute("table").toString()));

                if(co.isNeedHelp() && groupId == 0) {
                    request.setAttribute("alert", Alert.setAlert("Patientez", "Votre table a demandé l'aide d'un serveur. Celui-ci ne va pas tarder.", "info"));
                    return "/WEB-INF/dashboardCustomer.jsp";
                }
                
                // init cart lists
                List<Dish> cartDishes = null;
                List<Drink> cartDrinks = null;
                List<Combo> cartCombos = null;

                // set cart lists with session
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
                if ((cartDishes == null || 0 >= cartDishes.size()) && (cartDrinks == null || 0 >= cartDrinks.size()) && (cartCombos == null || 0 >= cartCombos.size())) {
                    redirectToDashboard(request, response);
                }

                // cart validation and customerorder setting
                if (null != request.getParameter("confirmCart")) {

                    // test if there is still carts to validate
                    if (co.getSavedCarts() >= co.getNbTablet()) {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Tous les paniers de la commande ont déjà été validés", "danger"));
                        return "/WEB-INF/menu/recap.jsp";
                    }

                    if (cartDishes != null && cartDishes.size() > 0) {
                        for (Dish dish : cartDishes) {
                            DishOrderLine dishOrderLine = new DishOrderLine();
                            dishOrderLine.setCustomerOrders(co);
                            dishOrderLine.setDish(dish);
                            dishOrderLine.setStatus(1);
                            co.getDishes().add(dishOrderLine);
                            co.getDishes().size();
                        }
                    }

                    if (cartDrinks != null && cartDrinks.size() > 0) {
                        for (Drink drink : cartDrinks) {
                            DrinkOrderLine drinkOrderLine = new DrinkOrderLine();
                            drinkOrderLine.setCustomerOrders(co);
                            drinkOrderLine.setDrink(drink);
                            drinkOrderLine.setStatus(1);
                            co.getDrinks().add(drinkOrderLine);
                            co.getDrinks().size();
                        }
                    }

                    if (cartCombos != null && cartCombos.size() > 0) {
                        for (Combo combo : cartCombos) {
                            ComboOrderLine comboOrderLine = new ComboOrderLine();
                            comboOrderLine.setCustomerOrders(co);
                            comboOrderLine.setCombo(combo);
                            // add dishOrderLines
                            List<DishOrderLine> dishOrderLines = new ArrayList();
                            for (Dish dish : combo.getDishes()) {
                                DishOrderLine dishOrderLine = new DishOrderLine();
                                dishOrderLine.setDish(dish);
                                dishOrderLine.setComboOrderLine(comboOrderLine);
                                dishOrderLine.setStatus(1);
                                dishOrderLines.add(dishOrderLine);
                            }
                            comboOrderLine.setDishes(dishOrderLines);
                            co.getCombos().add(comboOrderLine);
                            co.getCombos().size();
                        }
                    }

                    if(groupId == 0) { // customer save cart : load in ejb
                        boolean saved = boc.saveCart(co);
                        if (saved) {
                            session.setAttribute("validatedCart", true);
                            redirectToDashboard(request, response);
                        } else {
                            request.setAttribute("alert", Alert.setAlert("Erreur", "Tous les paniers de la commande ont déjà été validés", "danger"));
                        }
                    } else if (groupId == 1) { // waiter save cart : load in ejb and persist immédiately
                        boc.saveCartOverride(co);
                        session.removeAttribute("cartDishes");
                        session.removeAttribute("cartDrinks");
                        session.removeAttribute("cartCombos");
                        redirectToDashboard(request, response);
                    }

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
