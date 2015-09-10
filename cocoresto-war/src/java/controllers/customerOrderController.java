package controllers;

import ejb.ejbRestaurantLocal;
import entities.Combo;
import entities.CustomerOrder;
import entities.CustomerTable;
import entities.Dish;
import entities.Drink;
import entities.Employee;
import entities.OrderStatus;
import helpers.Alert;
import helpers.Pagination;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import library.FieldValidation;
import models.beanOrderCustomer;
import models.beanTableCustomer;

public class customerOrderController implements IController {

    ejbRestaurantLocal ejbRestaurant = lookupejbRestaurantLocal();

    private final beanOrderCustomer boc = new beanOrderCustomer();
    private final beanTableCustomer btc = new beanTableCustomer();
    private final String newUrl = "/WEB-INF/order/customerOrderNew.jsp";
    private final String editWaiterUrl = "/WEB-INF/order/customerOrderEdit.jsp";
    private final String editUrl = "/WEB-INF/admin/customerOrderEdit.jsp";
    private final String listUrl = "/WEB-INF/admin/customerOrderList.jsp";
    private final String helpUrl = "/WEB-INF/order/customerOrderHelp.jsp";
    private final String cookUrl = "/WEB-INF/dashboardCooker.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        // set session values
        HttpSession session = request.getSession();
        boolean logged = false;
        Long groupId = 0L;
        Employee employee = null;

        if (session.getAttribute("logged") != null && session.getAttribute("group") != null && session.getAttribute("loggedEmployee") != null) {
            logged = (boolean) session.getAttribute("logged");
            groupId = (Long) session.getAttribute("group");
            employee = (Employee) session.getAttribute("loggedEmployee");
        }

        if (logged && groupId >= 3) {

            if ("edit".equals(request.getParameter("task")) && request.getParameter("id") != null && !request.getParameter("id").trim().isEmpty()) {
                try {
                    CustomerOrder co = boc.findById(Long.valueOf(request.getParameter("id")));
                    request.setAttribute("customerOrder", co);
                    request.setAttribute("statusList", boc.findAllOrderStatus());
                    return editUrl;
                } catch (NumberFormatException | EJBException e) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Cette commande n'existe pas", "danger"));
                }
            }

            getList(request, "option=customerOrder");

            return listUrl;

        } else if (logged && groupId == 1) { // waiter

            if ("new".equals(request.getParameter("task"))) {

                try {
                    request.setAttribute("customerTableCapacityMax", btc.countMaxCapacity());
                } catch (EJBException ex) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Il n'y a plus de tables disponibles pour le moment", "danger"));
                }

                if (request.getParameter("confirm") != null) {

                    // check and set people value
                    int people;
                    if (FieldValidation.checkInteger(request.getParameter("people"), true, 1)) {
                        people = Integer.valueOf(request.getParameter("people"));
                    } else {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Veuillez entrer un nombre de couverts valide", "danger"));
                        return newUrl;
                    }

                    // check and set table value
                    Long tableId;
                    if (FieldValidation.checkInteger(request.getParameter("customerTable"), true, 1)) {
                        tableId = Long.valueOf(request.getParameter("customerTable"));
                    } else {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Veuillez choisir une table", "danger"));
                        return newUrl;
                    }

                    // check and set nbTablet value
                    int nbTablet;
                    if (FieldValidation.checkInteger(request.getParameter("nbTablet"), true, 1)) {
                        nbTablet = Integer.valueOf(request.getParameter("nbTablet"));
                    } else {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Veuillez choisir un nombre de tablettes valide", "danger"));
                        return newUrl;
                    }

                    // test restaurant max capacity
                    if (people > btc.countMaxCapacity()) {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Il n'y a plus assez de places disponibles", "danger"));
                        return newUrl;
                    }

                    if (employee == null) {
                        try {
                            response.sendRedirect("FrontController");
                        } catch (IOException ex) {
                            request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));
                        }
                    }

                    // test if table is still not busy
                    CustomerTable ct = btc.findById(tableId);
                    if (ct.isBusy() || !ct.isActive()) {
                        request.setAttribute("alert", Alert.setAlert("Désoléé", "La table n'est plus disponible", "danger"));
                        return newUrl;
                    }

                    ct.setBusy(true);
                    btc.update(ct);

                    // create order
                    CustomerOrder order = new CustomerOrder();
                    order.setPeople(people);
                    order.setCustomerTable(ct);
                    order.setNbTablet(nbTablet);
                    order.setCurrentTablets(0);
                    order.setEmployee(employee);
                    order.setOrderDate(new Date());
                    order.setStatus(OrderStatus.OPENED);
                    order.setNumber(String.valueOf(tableId) + String.valueOf(new Date().getTime()));
                    order.setNeedHelp(false);
                    order.setActive(true);

                    boc.create(order);

                    // if all ok : redirect to dashboard
                    redirectToDashboard(request, response);
                }

                return newUrl;
            }

            if ("edit".equals(request.getParameter("task"))) {

                // edit form has been send to update
                if (request.getParameter("confirm") != null) {
                    if (edit(request)) {
                        redirectToDashboard(request, response);
                    }
                }

                // edit form has been send to cancel order
                if (request.getParameter("cancel") != null) {
                    if (cancel(request)) {
                        redirectToDashboard(request, response);
                    }
                }

                // no id parameter
                if (request.getParameter("id") == null || request.getParameter("id").trim().isEmpty()) {
                    redirectToDashboard(request, response);
                }

                Long id = 0L;
                if (FieldValidation.checkInteger(request.getParameter("id"), true, 1)) {
                    id = Long.valueOf(request.getParameter("id"));
                } else {
                    redirectToDashboard(request, response);
                }

                try {
                    request.setAttribute("customerTableCapacityMax", btc.countMaxCapacity());
                } catch (EJBException ex) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Il n'y a plus de tables disponibles pour le moment", "danger"));
                }

                CustomerOrder co = boc.findById(id);
                request.setAttribute("customerOrder", co);

                return editWaiterUrl;
            }

            if ("help".equals(request.getParameter("task"))) {
                request.setAttribute("customerHelpOrders", boc.getNeedHelpOrders());

                return helpUrl;
            }

        } else if (logged && groupId == 2) {

            if ("swap".equals(request.getParameter("task"))) {

                List<Dish> dishesOnPrep = new ArrayList();
                List<Drink> drinksOnPrep = new ArrayList();
                List<Combo> combosOnPrep = new ArrayList();

                CustomerOrder co = boc.findById(Long.valueOf(request.getParameter("id")));
                co.setStatus(OrderStatus.PREPARED);
                boc.update(co);
                if (request.getParameter("dNb") != null) {
                    for (Dish d : co.getDishes()) {
                        if (d.getId().equals(Long.valueOf(request.getParameter("dNb")))) {
                            d.setStatus(2);
                            dishesOnPrep.add(d);
                            break;
                        }
                    }
                } else if (request.getParameter("drNb") != null) {
                    for (Drink dr : co.getDrinks()) {
                        if (dr.getId().equals(Long.valueOf(request.getParameter("drNb")))) {
                            dr.setStatus(2);
                            drinksOnPrep.add(dr);
                            break;
                        }
                    }
                } else if (request.getParameter("dcNb") != null) {
                    for (Combo c : co.getCombos()) {
                        for (Dish d : c.getDishes()) {
                            if (d.getId().equals(Long.valueOf(request.getParameter("dcNb")))) {
                                d.setStatus(2);
                                dishesOnPrep.add(d);
                                break;
                            }
                        }
                    }
                }
                
                return cookUrl;

            }

            if ("ready".equals(request.getParameter("task"))) {

                CustomerOrder co = boc.findById(Long.valueOf(request.getParameter("id")));
                co.setStatus(OrderStatus.FINISHED);
            }

        } else { // not logged or wrong groupId
            redirectToDashboard(request, response);
        }

        return "/WEB-INF/login.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    private void getList(HttpServletRequest request, String queryString) {

        /* pagination */
        Pagination pagination = new Pagination(queryString, request.getParameter("page"), 10, boc.count());
        request.setAttribute("pagination", pagination.getPagination());

        List<CustomerOrder> customerOrders = boc.findAllByRange(pagination.getMin(), 10);
        request.setAttribute("customerOrders", customerOrders);
    }

    private boolean edit(HttpServletRequest request) {

        // test required input
        if (request.getParameter("people").trim().isEmpty() || request.getParameter("nbTablet").trim().isEmpty()) {
            request.setAttribute("alert", Alert.setAlert("Attention", "Les champs * sont obligatoires", "warning"));
            return false;
        }

        // get order
        CustomerOrder co;
        try {
            co = ejbRestaurant.getOrder(Integer.valueOf(request.getParameter("customerTable")));
        } catch (Exception e) {
            request.setAttribute("alert", Alert.setAlert("Attention", "Commande introuvable", "danger"));
            return false;
        }

        co.setPeople(Integer.valueOf(request.getParameter("people")));
        co.setNbTablet(Integer.valueOf(request.getParameter("nbTablet")));

        boc.update(co);

        return true;
    }

    private boolean cancel(HttpServletRequest request) {

        Long id = 0L;
        if (FieldValidation.checkInteger(request.getParameter("id"), true, 1)) {
            id = Long.valueOf(request.getParameter("id"));
        } else {
            request.setAttribute("alert", Alert.setAlert("Erreur", "Cette commande n'existe pas", "danger"));
            return false;
        }

        CustomerOrder co = boc.findById(id);
        boc.cancelCustomerOrder(co);

        CustomerTable ct = co.getCustomerTable();
        ct.setBusy(false);
        btc.update(ct);

        return true;
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

    private void redirectToDashboard(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("FrontController?option=dashboard");
        } catch (IOException | IllegalStateException ex) {
            request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));
        }
    }
}
