package controllers;

import entities.Employee;
import helpers.Alert;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        if(session.getAttribute("logged") != null) {
            boolean logged = (boolean) session.getAttribute("logged");
        }
        
        
        
        
        

        String password = request.getParameter("password");

        Employee loggedEmployee = bLogin.login(password);

        if (loggedEmployee == null) {
            request.setAttribute("alert", Alert.setAlert("Erreur", "Votre code est invalide", "danger"));
            return "/WEB-INF/index.jsp";
        } else {
            session.setAttribute("logged", true);

            return "/WEB-INF/dashboardWaiter.jsp";
        }

    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
    
}
