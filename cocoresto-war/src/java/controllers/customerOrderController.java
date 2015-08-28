package controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.beanOrderCustomer;

public class customerOrderController implements IController {

    private beanOrderCustomer boc = new beanOrderCustomer();
    private String editUrl = "/WEB-INF/admin/customerOrderEdit.jsp";
    private String listUrl = "/WEB-INF/admin/customerOrderList.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        
        
        return "";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
    
}
