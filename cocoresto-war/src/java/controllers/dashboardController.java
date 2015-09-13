package controllers;

import entities.Combo;
import entities.ComboOrderLine;
import entities.CustomerOrder;
import entities.Dish;
import entities.DishOrderLine;
import entities.Drink;
import entities.DrinkOrderLine;
import entities.Employee;
import entities.OrderStatus;
import helpers.Pagination;
import java.util.HashMap;
import java.util.List;
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

            if (session.getAttribute("validatedCart") == null) {
                return "/WEB-INF/login.jsp";
            }
            
            // session cart already validated
            if((boolean) session.getAttribute("validatedCart") == true) {
                
                
                
                
                
                
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

            List<CustomerOrder> cos = (List) session.getAttribute("cos");

            if (cos == null) {
                cos = boc.findOrdersByStatus(OrderStatus.VALIDATE, OrderStatus.PREPARED);
                for (CustomerOrder co : cos) {
                    for (DrinkOrderLine dr : co.getDrinks()) {
                        dr.setStatus(1);
                    }
                    for (DishOrderLine d : co.getDishes()) {
                        d.setStatus(1);
                    }
                    for (ComboOrderLine c : co.getCombos()) {
                        c.setStatus(new HashMap());
                        for (DishOrderLine di : c.getDishes()) {
                            c.getStatus().put(di.getId(), 1);
                        }
                    }
                }
            }

            session.setAttribute("cos", cos);

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

}
