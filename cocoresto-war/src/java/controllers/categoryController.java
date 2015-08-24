package controllers;

import entities.Category;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.beanCategory;

public class categoryController implements IController {

    beanCategory bc = new beanCategory();
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String url = "/WEB-INF/admin/listCategory.jsp";
                
        
        if(request.getParameter("categories") == null){
            request.setAttribute("categories", bc.findAll());   
        }
        
        if("edit".equals(request.getParameter("task"))){
          url = "/WEB-INF/admin/editCategory.jsp";
          
          if(request.getParameter("id") != null){
              Category c = bc.findById(Long.valueOf(request.getParameter("id")));
              request.setAttribute("category", c);
          } 

        }
        return url;
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
