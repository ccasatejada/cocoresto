package controllers;

import entities.Category;
import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanCategory;

public class categoryController implements IController {

    beanCategory bc = new beanCategory();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String url = "/WEB-INF/admin/categoryList.jsp";

        Category c = new Category();

        if ("edit".equals(request.getParameter("task"))) {
            url = "/WEB-INF/admin/categoryEdit.jsp";
            if (request.getParameter("id") != null) {
                c = bc.findById(Long.valueOf(request.getParameter("id")));
                request.setAttribute("category", c);
            }
        }
        
        if (request.getParameter("confirm") != null) {
            if (request.getParameter("id").isEmpty()) { //create
                c.setName(request.getParameter("nameCategory"));
                c.setActive(true);
                bc.create(c);
            } else { // update
                c.setId(Long.valueOf(request.getParameter("id")));
                c.setName(request.getParameter("nameCategory"));
                bc.update(c);
            }
        }

        if ("delete".equals(request.getParameter("task"))) {
            c = bc.findById(Long.valueOf(request.getParameter("id")));
            bc.delete(c);
            url = "/WEB-INF/admin/categoryList.jsp";
        }

        
        request.setAttribute("categories", bc.findAll());
        return url;
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
