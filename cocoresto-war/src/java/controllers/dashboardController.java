package controllers;

import entities.Employee;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class dashboardController extends AbstractController implements IController {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        
        // redirect to login if no logged
        if(session.getAttribute("logged") == null || (boolean) session.getAttribute("logged") == false) {
            return "/WEB-INF/index.jsp";
        }

        Long groupId = (Long) session.getAttribute("group");
        Employee employee = (Employee) session.getAttribute("employee");
        request.setAttribute("name", employee.getFirstName() + " " + employee.getLastName());
        
        if(groupId == 1){ //waiter
            return "/WEB-INF/dashboardWaiter.jsp";
        } else if (groupId == 2) { // cooker
            return "/WEB-INF/dashboardCooker.jsp";
        }
        
        // else return admin dashboard
        return "/WEB-INF/admin/dashboard.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
    
}
