package controllers;

import entities.Employee;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanCategory;
import models.beanCombo;
import models.beanDish;
import models.beanOrderCustomer;
import models.beanTableCustomer;

public class dashboardController implements IController {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        
        // redirect to login if no logged
        if(session.getAttribute("logged") == null || (boolean) session.getAttribute("logged") == false) {
            return "/WEB-INF/login.jsp";
        }

        Long groupId = (Long) session.getAttribute("group");
        Employee employee = (Employee) session.getAttribute("loggedEmployee");
        request.setAttribute("name", employee.getFirstName() + " " + employee.getLastName());
        
        if(groupId == 1){ // return waiter dashboard
            beanOrderCustomer boc = new beanOrderCustomer();
            request.setAttribute("customerOrders", boc.findAllByRangeByEmployee(0, 10, employee));
            
            
            return "/WEB-INF/dashboardWaiter.jsp";
        } else if (groupId == 2) { // return cooker dashboard
            return "/WEB-INF/dashboardCooker.jsp";
        }
        
        // set dashboard counters
        request.setAttribute("countCustomerTable", new beanTableCustomer().count());
        request.setAttribute("countCustomerOrder", new beanOrderCustomer().count());
        request.setAttribute("countDish", new beanDish().count());
        request.setAttribute("countCategory", new beanCategory().count());
        request.setAttribute("countCombo", new beanCombo().count());
        
        // else return admin dashboard
        return "/WEB-INF/admin/dashboard.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
    
}
