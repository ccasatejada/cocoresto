package controllers;

import ejb.ejbRestaurantLocal;
import entities.CustomerOrder;
import entities.CustomerTable;
import entities.Employee;
import helpers.Alert;
import java.io.IOException;
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
import models.beanLogin;
import models.beanTableCustomer;

public class loginController implements IController {
    ejbRestaurantLocal ejbRestaurant = lookupejbRestaurantLocal();

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

        // employee login form has been send
        if (request.getParameter("password") != null) {

            String password = request.getParameter("password");

            Employee loggedEmployee = bLogin.login(password);

            if (loggedEmployee == null) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Votre code est invalide", "danger"));
                return "/WEB-INF/login.jsp";
            } else {

                if(ejbRestaurant.isEmployeeLogged(loggedEmployee)){
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Vous êtes déjà connecté sur une autre machine", "danger"));
                    return "/WEB-INF/login.jsp";
                }
                
                ejbRestaurant.addEmployee(loggedEmployee);
                Long idGroup = loggedEmployee.getEmployeeGroup().getId();
                session.setAttribute("logged", true);
                session.setAttribute("group", idGroup);
                session.setAttribute("loggedEmployee", loggedEmployee);
                session.setAttribute("userName", loggedEmployee.getFirstName() + " " + loggedEmployee.getLastName());
                
                try {
                    response.sendRedirect("FrontController?option=dashboard");
                } catch (IOException ex) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'accéder au tableau de bord", "danger"));
                }
            }
        }

        // client login form has been send
        if (request.getParameter("tableNumber") != null) {
            
            // test integer input value
            Integer numberTable;
            if (FieldValidation.checkInteger(request.getParameter("tableNumber"), true, 1)) {
                numberTable = Integer.valueOf(request.getParameter("tableNumber"));
            } else {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Veuillez entrer un numéro de table valide", "danger"));
                return "/WEB-INF/login.jsp";
            }
            
            // test if table exist
            beanTableCustomer btc = new beanTableCustomer();
            try {
                CustomerTable ct = btc.findByNumber(numberTable);
            } catch (EJBException e) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Cette table n'existe pas ou n'est pas assignée à une commande", "danger"));
                return "/WEB-INF/login.jsp";
            }
            
            // test if there is an associated order
            CustomerOrder co = ejbRestaurant.getOrder(numberTable);
            if(co == null) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Cette table n'est pas assignée à une commande", "danger"));
                return "/WEB-INF/login.jsp";
            } 
            
            // test how many tablets are connected
            Integer totalTablets = co.getNbTablet();
            Integer currentTablets = co.getCurrentTablets() == null ? 0 : co.getCurrentTablets();
            if(currentTablets < totalTablets) {
                co.setCurrentTablets(++currentTablets);
            } else {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Toutes les tablettes assignées à la commande sont activées", "danger"));
                return "/WEB-INF/login.jsp";
            }
            
            session.setAttribute("table", numberTable);
            session.setAttribute("logged", true);
            session.setAttribute("group", 0L); // client group

            try {
                response.sendRedirect("FrontController?option=dashboard");
            } catch (IOException ex) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'accéder au tableau de bord", "danger"));
            }
        }

        // Disconnect
        if ("disconnect".equals(request.getParameter("task"))) {
            ejbRestaurant.removeEmployee((Employee) session.getAttribute("loggedEmployee"));
            session.setAttribute("logged", false);
            session.setAttribute("group", 0);
            session.setAttribute("loggedEmployee", null);
            session.removeAttribute("logged");
            session.removeAttribute("group");
            session.removeAttribute("loggedEmployee");
        }

        return "/WEB-INF/login.jsp";

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
