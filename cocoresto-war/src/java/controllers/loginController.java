package controllers;

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
        
        if(bLogin == null) {
            bLogin = new beanLogin();
            session.setAttribute("bLogin", bLogin);
        }
        
        String password = request.getParameter("password");
        
        Boolean logged = bLogin.login(password);
        
        if(!logged){
            return "/WEB-INF/index.jsp";
        } else {
            return "/WEB-INF/dashboardWaiter.jsp";
        }

    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
