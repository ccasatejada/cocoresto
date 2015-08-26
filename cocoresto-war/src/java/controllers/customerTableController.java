package controllers;

import entities.CustomerTable;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.beanTableCustomer;

public class customerTableController implements IController {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        beanTableCustomer btc = new beanTableCustomer();

        if (request.getParameter("task") != null) {
            if (request.getParameter("task").equals("edit") || request.getParameter("task").equals("add")) {
                
//                if(request.getParameter("id")){
//                    
//                }
                 
                return "/WEB-INF/admin/customerTableEdit.jsp";
            }
        }

        // default task : show list
        List<CustomerTable> customerTables = btc.findAll();

        request.setAttribute("customerTables", customerTables);

        return "/WEB-INF/admin/customerTableList.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
