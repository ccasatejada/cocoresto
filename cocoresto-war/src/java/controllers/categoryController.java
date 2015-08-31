package controllers;

import entities.Category;
import helpers.Alert;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanCategory;

public class categoryController implements IController {

    beanCategory bc = new beanCategory();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        boolean logged = false;
        Long groupId = 0L;
        
        if(session.getAttribute("logged") != null && session.getAttribute("group") != null){
            logged = (boolean) session.getAttribute("logged");
            groupId = (Long) session.getAttribute("group");
        }
        
        if(logged && groupId >= 3){
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
                request.setAttribute("alert", Alert.setAlert("Succès", "La catégorie a été ajoutée", "success"));
            } else { // update
                c.setId(Long.valueOf(request.getParameter("id")));
                c.setName(request.getParameter("nameCategory"));
                bc.update(c);
                request.setAttribute("alert", Alert.setAlert("Succès", "La catégorie a été mise à jour", "succes"));
            }
        }

        if ("delete".equals(request.getParameter("task"))) {
            c = bc.findById(Long.valueOf(request.getParameter("id")));
            bc.delete(c);
            request.setAttribute("alert", Alert.setAlert("Succès", "La commande a été supprimée", "success"));
            url = "/WEB-INF/admin/categoryList.jsp";
        }

        
        request.setAttribute("categories", bc.findAll());
        return url;
        } else {
            try{
                response.sendRedirect("FrontController?option=dashboard");
            } catch (IOException ex){
                request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));                
            }
        }
        
        return "/WEB-INF/index.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
