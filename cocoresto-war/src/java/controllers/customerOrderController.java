package controllers;

import ejb.ejbRestaurantLocal;
import entities.CustomerOrder;
import entities.CustomerTable;
import entities.Employee;
import entities.OrderStatus;
import helpers.Alert;
import helpers.Pagination;
import java.io.IOException;
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
//
//            if ("add".equals(request.getParameter("task")) && request.getParameter("id") == null) {
//                return editUrl;
//            }
//
//            if ("delete".equals(request.getParameter("task")) && request.getParameter("id") != null) {
//                try {
//                    CustomerOrder co = boc.findById(Long.valueOf(request.getParameter("id")));
//                    boc.delete(co);
//                    request.setAttribute("alert", Alert.setAlert("Succès", "La commande été supprimée", "success"));
//                } catch (NumberFormatException | EJBException e) {
//                    request.setAttribute("alert", Alert.setAlert("Erreur", "Cette commande n'existe pas", "danger"));
//                }
//            }
//
//            // edit form has been send
//            if (request.getParameter("confirm") != null) {
//                boolean ok = edit(request);
//                if (!ok) {
//                    return editUrl;
//                }
//            }

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

                    // create customerOrder
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
                    order.setEmployee(employee);
                    order.setOrderDate(new Date());
                    order.setStatus(OrderStatus.OPENED);
                    order.setNumber(String.valueOf(tableId) + String.valueOf(new Date().getTime()));
                    order.setActive(true);

                    boc.create(order);

                    // if all ok : redirect to dashboard
                    redirectToDashboard(request, response);
                }

                return newUrl;
            }

            if ("edit".equals(request.getParameter("task"))) {

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
            
            // cancel order
            if(request.getParameter("cancel") != null) {

                Long id = 0L;
                if (FieldValidation.checkInteger(request.getParameter("id"), true, 1)) {
                    id = Long.valueOf(request.getParameter("id"));
                } else {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Cette commande n'existe pas", "danger"));
                    return editWaiterUrl;
                }      
                
                CustomerOrder co = boc.findById(id);
                boc.cancelCustomerOrder(co);
                
                CustomerTable ct = co.getCustomerTable();
                ct.setBusy(false);
                btc.update(ct);

                redirectToDashboard(request, response);
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
