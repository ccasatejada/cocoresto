package controllers;

import ejb.ejbRestaurantLocal;
import entities.Combo;
import entities.ComboOrderLine;
import entities.CustomerOrder;
import entities.CustomerTable;
import entities.Dish;
import entities.DishOrderLine;
import entities.Drink;
import entities.DrinkOrderLine;
import entities.Employee;
import entities.OrderStatus;
import helpers.Alert;
import helpers.Pagination;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
                
                if("remove".equals(request.getParameter("action"))){
                    session.removeAttribute("cartDishes");
                    session.removeAttribute("cartDrinks");
                    session.removeAttribute("cartCombos");
                }
                
                request.setAttribute("customerHelpOrders", boc.getNeedHelpOrders());
                return helpUrl;
            }

            if ("menu".equals(request.getParameter("task"))) {

                Integer table = null;
                try {
                    table = Integer.valueOf(request.getParameter("table"));
                    session.setAttribute("table", table);
                } catch (NumberFormatException e) {
                    try {
                        response.sendRedirect("FrontController?option=customerOrder&task=help");
                    } catch (IOException ex) {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));
                    }
                }

                CustomerOrder order = ejbRestaurant.getOrder(table);
                if (order == null) {
                    try {
                        response.sendRedirect("FrontController?option=customerOrder&task=help");
                    } catch (IOException ex) {
                        request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));
                    }
                }
                
                session.setAttribute("order", order);
                
                Double cartTotal = 0.00;
                
                // get dishes already in order
                if (session.getAttribute("cartDishes") == null) {
                    List<Dish> dishes = new ArrayList();
                    Collection<DishOrderLine> dishOrderLines = order.getDishes();
                    if(dishOrderLines != null && dishOrderLines.size() > 0) {
                        for(DishOrderLine orderline : dishOrderLines) {
                            dishes.add(orderline.getDish());
                            cartTotal += orderline.getDish().getTotalPrice();
                            order.getDishes().remove(orderline); // remove in ejb to avoid duplicate content
                        }
                    }
                    session.setAttribute("cartDishes", dishes);
                }
                
                // get drinks already in order
                if (session.getAttribute("cartDrinks") == null) {
                    List<Drink> drinks = new ArrayList();
                    Collection<DrinkOrderLine> drinkOrderLines = order.getDrinks();
                    if(drinkOrderLines != null && drinkOrderLines.size() > 0) {
                        for(DrinkOrderLine orderline : drinkOrderLines) {
                            drinks.add(orderline.getDrink());
                            cartTotal += orderline.getDrink().getTotalPrice();
                            order.getDrinks().remove(orderline); // remove in ejb to avoid duplicate content
                        }
                    }
                    session.setAttribute("cartDrinks", drinks);
                }
                
                // get combos already in order
                if (session.getAttribute("cartCombos") == null) {
                    List<Combo> combos = new ArrayList();
                    Collection<ComboOrderLine> comboOrderLines = order.getCombos();
                    if(comboOrderLines != null && comboOrderLines.size() > 0) {
                        for(ComboOrderLine orderline : comboOrderLines) {
                            combos.add(orderline.getCombo());
                            cartTotal += orderline.getCombo().getTotalPrice();
                            order.getCombos().remove(orderline); // remove in ejb to avoid duplicate content
                        }
                    }
                    session.setAttribute("cartCombos", combos);                
                }

                
                request.setAttribute("cartTotal", String.format("%.2f", cartTotal));
                
                
                
                return "/WEB-INF/dashboardCustomer.jsp";
            }

        } else if (logged && groupId == 2) {

            if ("swap".equals(request.getParameter("task"))) {

                CustomerOrder co = boc.findById(Long.valueOf(request.getParameter("id")));
                CustomerOrder ejbCo = ejbRestaurant.getOrder(Integer.valueOf(request.getParameter("tNb")));
                co.setStatus(OrderStatus.PREPARED);
                ejbCo.setStatus(OrderStatus.PREPARED);
                boc.update(co);
                List<CustomerOrder> cos = (List) session.getAttribute("cos");
                co = boc.findById(Long.valueOf(request.getParameter("id")));
                for (CustomerOrder cOrder : cos) {
                    if (cOrder.getId().equals(co.getId())) {
                        co = cOrder;
                        co.setStatus(OrderStatus.PREPARED);
                        ejbCo = cOrder;
                        ejbCo.setStatus(OrderStatus.PREPARED);
                        break;
                    }
                }
                if (request.getParameter("id") != null) {
                    if (request.getParameter("dNb") != null && request.getParameter("cId") == null
                            && request.getParameter("dcNb") == null) {
                        for (DishOrderLine d : co.getDishes()) {
                            if (d.getId().equals(Long.valueOf(request.getParameter("dNb")))
                                    && co.getId().equals(Long.valueOf(request.getParameter("id")))) {
                                d.setStatus(2);
                                break;
                            }
                        }
                        for (DishOrderLine d : ejbCo.getDishes()) {
                            if (d.getId().equals(Long.valueOf(request.getParameter("dNb")))
                                    && ejbCo.getId().equals(Long.valueOf(request.getParameter("id")))) {
                                d.setStatus(2);
                                break;
                            }
                        }
                    }
                    if (request.getParameter("drNb") != null) {
                        for (DrinkOrderLine dr : co.getDrinks()) {
                            if (dr.getId().equals(Long.valueOf(request.getParameter("drNb")))
                                    && co.getId().equals(Long.valueOf(request.getParameter("id")))) {
                                dr.setStatus(2);
                                break;
                            }
                        }
                        for (DrinkOrderLine dr : ejbCo.getDrinks()) {
                            if (dr.getId().equals(Long.valueOf(request.getParameter("drNb")))
                                    && ejbCo.getId().equals(Long.valueOf(request.getParameter("id")))) {
                                dr.setStatus(2);
                                break;
                            }
                        }
                    }
                    if (request.getParameter("dcNb") != null && request.getParameter("dNb") == null
                            && request.getParameter("cId") != null) {
                        for (ComboOrderLine c : co.getCombos()) {
                            if (c.getId().equals(Long.valueOf(request.getParameter("cId")))) {
                                for (DishOrderLine di : c.getDishes()) {
                                    if (di.getId().equals(Long.valueOf(request.getParameter("dcNb")))
                                            && !di.getStatus().equals(2)) {
                                        di.setStatus(2);
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        for (ComboOrderLine c : ejbCo.getCombos()) {
                            if (c.getId().equals(Long.valueOf(request.getParameter("cId")))) {
                                for (DishOrderLine di : c.getDishes()) {
                                    if (di.getId().equals(Long.valueOf(request.getParameter("dcNb")))) {
                                        di.setStatus(2);
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                for (int i = 0; i < cos.size(); i++) {
                    if (cos.get(i).getId().equals(co.getId())) {
                        cos.remove(i);
                        break;
                    }
                }

                Set ord = ejbRestaurant.getOrders().keySet();
                Iterator it = ord.iterator();
                while (it.hasNext()) {
                    Integer cle = (Integer) it.next();
                    CustomerOrder valeur = ejbRestaurant.getOrders().get(cle);
                    if (valeur.getId().equals(ejbCo.getId())) {
                        ejbRestaurant.getOrders().remove(Integer.valueOf(request.getParameter("tNb")));
                        break;
                    }
                }
//                for (int i = 0; i < ejbRestaurant.getOrders().keySet().size(); i++) {
//                    for (Map.Entry<Integer, CustomerOrder> e : ejbRestaurant.getOrders().entrySet()) {
//                        if (ejbRestaurant.getOrders().get) {
//                            if (e.getValue().getId().equals(ejbCo.getId())) {
//                                ejbRestaurant.getOrders().remove(Integer.valueOf(request.getParameter("tNb")));
//                                break;
//                            }
//                        }
//                    }
//                }

                cos.add(co);
                ejbRestaurant.addCustomerOrder(ejbCo);

                session.setAttribute("cos", cos);

                return cookUrl;

            }

            if ("ready".equals(request.getParameter("task"))) {

                boolean dishReady = true;
                boolean drinkReady = true;
                boolean comboReady = true;
                CustomerOrder co = boc.findById(Long.valueOf(request.getParameter("id")));
                CustomerOrder ejbCo = ejbRestaurant.getOrder(Integer.valueOf(request.getParameter("tNb")));
                List<CustomerOrder> cos = (List) session.getAttribute("cos");

                for (CustomerOrder cOrder : cos) {
                    if (cOrder.getId().equals(co.getId())) {
                        co = cOrder;
                        break;
                    }
                }

                if (request.getParameter("id") != null) {
                    if (request.getParameter("dNb") != null && request.getParameter("cId") == null
                            && request.getParameter("dcNb") == null) {
                        for (DishOrderLine d : co.getDishes()) {
                            if (d.getId().equals(Long.valueOf(request.getParameter("dNb")))
                                    && co.getId().equals(Long.valueOf(request.getParameter("id")))) {
                                d.setStatus(3);
                                break;
                            }
                        }
                        for (DishOrderLine d : ejbCo.getDishes()) {
                            if (d.getId().equals(Long.valueOf(request.getParameter("dNb")))
                                    && ejbCo.getId().equals(Long.valueOf(request.getParameter("id")))) {
                                d.setStatus(3);
                                break;
                            }
                        }
                    }
                    if (request.getParameter("drNb") != null) {
                        for (DrinkOrderLine dr : co.getDrinks()) {
                            if (dr.getId().equals(Long.valueOf(request.getParameter("drNb")))
                                    && co.getId().equals(Long.valueOf(request.getParameter("id")))) {
                                dr.setStatus(3);
                                break;
                            }
                        }
                        for (DrinkOrderLine dr : ejbCo.getDrinks()) {
                            if (dr.getId().equals(Long.valueOf(request.getParameter("drNb")))
                                    && ejbCo.getId().equals(Long.valueOf(request.getParameter("id")))) {
                                dr.setStatus(3);
                                break;
                            }
                        }
                    }
                    if (request.getParameter("dcNb") != null && request.getParameter("dNb") == null
                            && request.getParameter("cId") != null) {
                        for (ComboOrderLine c : co.getCombos()) {
                            if (c.getId().equals(Long.valueOf(request.getParameter("cId")))) {
                                for (DishOrderLine di : c.getDishes()) {
                                    if (di.getId().equals(Long.valueOf(request.getParameter("dcNb")))
                                            && !di.getStatus().equals(3)) {
                                        di.setStatus(3);
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        for (ComboOrderLine c : ejbCo.getCombos()) {
                            if (c.getId().equals(Long.valueOf(request.getParameter("cId")))) {
                                for (DishOrderLine di : c.getDishes()) {
                                    if (di.getId().equals(Long.valueOf(request.getParameter("dcNb")))) {
                                        di.setStatus(3);
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                for (DrinkOrderLine dr : co.getDrinks()) {
                    if (dr.getStatus() < 3) {
                        drinkReady = false;
                        break;
                    }
                }
                for (DishOrderLine d : co.getDishes()) {
                    if (d.getStatus() < 3) {
                        dishReady = false;
                        break;
                    }
                }
                for (ComboOrderLine c : co.getCombos()) {
                    for (DishOrderLine di : c.getDishes()) {
                        if (di.getStatus() < 3) {
                            comboReady = false;
                            break;
                        }
                    }
                }

                for (int i = 0; i < cos.size(); i++) {
                    if (cos.get(i).getId().equals(co.getId())) {
                        cos.remove(i);
                        break;
                    }
                }

                for (Map.Entry<Integer, CustomerOrder> e : ejbRestaurant.getOrders().entrySet()) {
                    if (e.getValue().getId().equals(ejbCo.getId())) {
                        ejbRestaurant.getOrders().remove(Integer.valueOf(request.getParameter("tNb")));
                        break;
                    }
                }

                ejbRestaurant.addCustomerOrder(ejbCo);

                if (dishReady && drinkReady && comboReady) {
                    co.setStatus(OrderStatus.FINISHED);
                    ejbCo.setStatus(OrderStatus.FINISHED);
                    boc.update(co);

                } else {
                    cos.add(co);
                }

                session.setAttribute("cos", cos);

                return cookUrl;
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
