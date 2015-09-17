package controllers;

import ejb.ejbRestaurantLocal;
import entities.Combo;
import entities.ComboOrderLine;
import entities.CustomerOrder;
import entities.Dish;
import entities.DishOrderLine;
import entities.Drink;
import entities.DrinkOrderLine;
import entities.Employee;
import entities.OrderStatus;
import helpers.Alert;
import helpers.Pagination;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanCategory;
import models.beanCombo;
import models.beanDish;
import models.beanDrink;
import models.beanEmployee;
import models.beanOrderCustomer;
import models.beanRate;
import models.beanTableCustomer;

public class dashboardController implements IController {

    ejbRestaurantLocal ejbRestaurant = lookupejbRestaurantLocal();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        beanOrderCustomer boc = new beanOrderCustomer();

        // redirect to login if no logged
        if (session.getAttribute("logged") == null || (boolean) session.getAttribute("logged") == false) {
            return "/WEB-INF/login.jsp";
        }

        Long groupId = (Long) session.getAttribute("group");
        Employee employee = null;
        if (groupId > 0) {
            employee = (Employee) session.getAttribute("loggedEmployee");
            request.setAttribute("name", employee.getFirstName() + " " + employee.getLastName());
        }

        if (groupId == 0) { // return customer dashboard

            if (session.getAttribute("order") == null || session.getAttribute("validatedCart") == null) {
                return "/WEB-INF/login.jsp";
            }

            CustomerOrder co = null;

            try {
                Integer table = (Integer) session.getAttribute("table");
                co = ejbRestaurant.getOrder(table);
            } catch (Exception e) {
                request.setAttribute("alert", Alert.setAlert("Attention", "Commande introuvable", "danger"));
                return "/WEB-INF/login.jsp";
            }

            // session cart already validated
            if ((boolean) session.getAttribute("validatedCart") == true || (!co.getStatus().equals(OrderStatus.OPENED) && !co.getStatus().equals(OrderStatus.CANCELLED))) {

                Collection<DishOrderLine> dishOrderLines = co.getDishes();
                request.setAttribute("dishOrderLines", dishOrderLines);

                Collection<DrinkOrderLine> drinkOrderLines = co.getDrinks();
                request.setAttribute("drinkOrderLines", drinkOrderLines);

                Collection<ComboOrderLine> comboOrderLines = co.getCombos();
                request.setAttribute("comboOrderLines", comboOrderLines);

                return "/WEB-INF/menu/tracking.jsp";

            } else { // get current cart

                Double cartTotal = 0.00;

                if (session.getAttribute("cartDishes") != null) {
                    List<Dish> cartDishes = (List<Dish>) session.getAttribute("cartDishes");
                    request.setAttribute("cartDishes", cartDishes);
                    for (Dish d : cartDishes) {
                        cartTotal += d.getTotalPrice();
                    }
                }
                if (session.getAttribute("cartDrinks") != null) {
                    List<Drink> cartDrinks = (List<Drink>) session.getAttribute("cartDrinks");
                    request.setAttribute("cartDrinks", cartDrinks);
                    for (Drink dr : cartDrinks) {
                        cartTotal += dr.getTotalPrice();
                    }
                }
                if (session.getAttribute("cartCombos") != null) {
                    List<Combo> cartCombos = (List<Combo>) session.getAttribute("cartCombos");
                    request.setAttribute("cartCombos", cartCombos);
                    for (Combo c : cartCombos) {
                        cartTotal += c.getTotalPrice();
                    }
                }

                request.setAttribute("cartTotal", String.format("%.2f", cartTotal));
            }

            return "/WEB-INF/dashboardCustomer.jsp";

        } else if (groupId == 1) { // return waiter dashboard

            Pagination pagination = new Pagination("option=dashboard", request.getParameter("page"), 10, boc.count());
            request.setAttribute("pagination", pagination.getPagination());
            request.setAttribute("nbHelp", boc.getNbHelp());
            List<CustomerOrder> customerOrders = boc.findAllByRangeByEmployee(pagination.getMin(), 10, employee.getId());
            request.setAttribute("customerOrders", customerOrders);

            return "/WEB-INF/dashboardWaiter.jsp";
        } else if (groupId == 2) { // return cooker dashboard

            List<CustomerOrder> cOrders = (List) session.getAttribute("cOrders");
            HashMap<Integer, CustomerOrder> orders = ejbRestaurant.getOrders();
            if (cOrders == null) {
                cOrders = new ArrayList();
                cOrders = boc.findOrdersByStatus(OrderStatus.VALIDATE, OrderStatus.PREPARED);
                for (CustomerOrder corrr : cOrders) {
                    System.out.println("recup bean : " + corrr.getDishes());
                }
            } else {
                cOrders.removeAll(cOrders);
                for (Map.Entry<Integer, CustomerOrder> e : orders.entrySet()) {
                    if (e.getValue().getStatus().equals(OrderStatus.VALIDATE)
                            || e.getValue().getStatus().equals(OrderStatus.PREPARED)) {
                        System.out.println("recup ejb: " + e.getValue().getDishes());
                        cOrders.add(e.getValue());
                    }
                }
            }

            for (CustomerOrder co : cOrders) {
                if (co.getStatus().equals(OrderStatus.VALIDATE)) {
                    for (DrinkOrderLine dr : co.getDrinks()) {
                        dr.setStatus(1);
                    }
                    for (DishOrderLine d : co.getDishes()) {
                        d.setStatus(1);
                    }
                    for (ComboOrderLine c : co.getCombos()) {
                        for (DishOrderLine di : c.getDishes()) {
                            di.setStatus(1);
                        }
                    }
                }
            }

            session.setAttribute("cOrders", cOrders);

            return "/WEB-INF/dashboardCooker.jsp";
        }

        // set dashboard counters
        request.setAttribute("countEmployee", new beanEmployee().count());
        request.setAttribute("countCustomerTable", new beanTableCustomer().count());
        request.setAttribute("countCustomerOrder", new beanOrderCustomer().count());
        request.setAttribute("countDiscount", new beanRate().discountCount());
        request.setAttribute("countCategory", new beanCategory().count());
        request.setAttribute("countDish", new beanDish().count());
        request.setAttribute("countDrink", new beanDrink().count());
        request.setAttribute("countCombo", new beanCombo().count());

        // else return admin dashboard
        return "/WEB-INF/admin/dashboard.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
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
