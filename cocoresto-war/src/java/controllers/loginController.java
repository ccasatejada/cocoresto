package controllers;

import entities.Employee;
import helpers.Alert;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanLogin;

public class loginController implements IController {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        
        beanLogin bLogin = (beanLogin) session.getAttribute("bLogin");
        
        if (bLogin == null) {
            bLogin = new beanLogin();
            session.setAttribute("bLogin", bLogin);
        }
        
        if (session.getAttribute("logged") != null) {
            boolean logged = (boolean) session.getAttribute("logged");
        }
        
        // login form has been send
        if (request.getParameter("password") != null) {
            
            String password = request.getParameter("password");
            
            Employee loggedEmployee = bLogin.login(password);
            
            if (loggedEmployee == null) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Votre code est invalide", "danger"));
                return "/WEB-INF/index.jsp";
            } else {
                Long idGroup = loggedEmployee.getEmployeeGroup().getId();
                session.setAttribute("logged", true);
                session.setAttribute("group", idGroup);
                request.setAttribute("alert", Alert.setAlert("Bienvenue", loggedEmployee.getFirstName() + " " + loggedEmployee.getLastName(), "success"));
                if(idGroup == 1) { // Waiter
                    return "/WEB-INF/dashboardWaiter.jsp";
                } else if (idGroup == 2) { // Cooker
                    return "/WEB-INF/dashboardCooker.jsp";
                } else { // Admin
                    return "/WEB-INF/admin/dashboard.jsp";
                }
            }
        }
        
        // Disconnect
        if (request.getParameter("disconnect") != null) {
            session.setAttribute("logged", false);
            session.setAttribute("group", -1);
            session.removeAttribute("logged");
            session.removeAttribute("group");
        }
        
        return "/WEB-INF/index.jsp";
        
    }
    
    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
    
}
