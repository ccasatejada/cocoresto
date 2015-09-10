package controllers;

import entities.Combo;
import entities.CustomerOrder;
import entities.Dish;
import entities.Drink;
import entities.Employee;
import entities.OrderStatus;
import helpers.Pagination;
import java.util.ArrayList;
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
        if(groupId > 0) {
            employee = (Employee) session.getAttribute("loggedEmployee");
            request.setAttribute("name", employee.getFirstName() + " " + employee.getLastName());
        }
        
        if(groupId == 0) { // return customer dashboard
            
//            if(request.getParameter("task") == null || "dish".equals(request.getParameter("task"))) {
//                
//            }

            return "/WEB-INF/dashboardCustomer.jsp";
        } else if (groupId == 1) { // return waiter dashboard

            Pagination pagination = new Pagination("option=dashboard", request.getParameter("page"), 10, boc.count());
            request.setAttribute("pagination", pagination.getPagination());

            List<CustomerOrder> customerOrders = boc.findAllByRangeByEmployee(pagination.getMin(), 10, employee.getId());
            request.setAttribute("customerOrders", customerOrders);
            
            return "/WEB-INF/dashboardWaiter.jsp";
        } else if (groupId == 2) { // return cooker dashboard
            
//            Pagination pagination = new Pagination("option=dashboard", request.getParameter("page"), 10, boc.count());
//            request.setAttribute("pagination", pagination.getPagination());
//
//            List<CustomerOrder> customerOrders = boc.findAllByRange(pagination.getMin(), 10);
            
            List<CustomerOrder> coToDo = boc.findOrdersByStatus(OrderStatus.VALIDATE);
            List<Dish> dishesToDo = new ArrayList();
            List<Drink> drinksToDo = new ArrayList();
            List<Combo> combosToDo = new ArrayList();
            
            for(CustomerOrder co : coToDo) {
                for(Drink dr : co.getDrinks()){
                    dr.setStatus(1);
                    drinksToDo.add(dr);
                }
                for(Dish d : co.getDishes()) {
                    d.setStatus(1);
                    dishesToDo.add(d);
                }
                for(Combo c : co.getCombos()) {
                    for(Dish di : c.getDishes()) {
                        di.setStatus(1);
                    }
                    combosToDo.add(c);
                }
                co.setDrinks(drinksToDo);
                co.setDishes(dishesToDo);
                co.setCombos(combosToDo);
            }
            
            request.setAttribute("coToDo", coToDo);
            
            List<CustomerOrder> coOnPrep = boc.findOrdersByStatus(OrderStatus.PREPARED);
            request.setAttribute("coOnPrep", coOnPrep);
            
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
