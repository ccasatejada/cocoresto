package controllers;

import entities.Category;
import helpers.Alert;
import helpers.Pagination;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJBException;
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
                c.setType(request.getParameter("categoryType"));
                c.setActive(true);
                bc.create(c);
                request.setAttribute("alert", Alert.setAlert("Succès", "La catégorie a été ajoutée", "success"));
            } else { // update
                c.setId(Long.valueOf(request.getParameter("id")));
                c.setName(request.getParameter("nameCategory"));
                c.setType(request.getParameter("categoryType"));
                bc.update(c);
                request.setAttribute("alert", Alert.setAlert("Succès", "La catégorie a été mise à jour", "success"));
            }
        }

        if ("delete".equals(request.getParameter("task"))) {
            c = bc.findById(Long.valueOf(request.getParameter("id")));
            try{
            bc.delete(c);
            request.setAttribute("alert", Alert.setAlert("Succès", "La catégorie a été supprimée", "success"));
            } catch (EJBException e){
                request.setAttribute("alert", Alert.setAlert("Erreur", "Cette catégorie n'existe pas", "danger"));
            }
            url = "/WEB-INF/admin/categoryList.jsp";
        }

        
        getList(request);
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
    
    private void getList(HttpServletRequest request){

        Pagination pagination = new Pagination("category", request.getParameter("page"), 10, bc.count());
        request.setAttribute("pagination", pagination.getPagination());
        
        List<Category> categories = bc.findAllByRange(pagination.getMin(), 10);
        request.setAttribute("categories", categories);
    }

}
