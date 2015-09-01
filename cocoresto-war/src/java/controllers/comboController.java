package controllers;

import entities.Combo;
import helpers.Alert;
import helpers.Pagination;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanCombo;

public class comboController implements IController {

    beanCombo bc = new beanCombo();
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        boolean logged = false;
        Long groupId = 0L;

        if (session.getAttribute("logged") != null && session.getAttribute("group") != null) {
            logged = (boolean) session.getAttribute("logged");
            groupId = (Long) session.getAttribute("group");
        }
        if (logged && groupId >= 3) {
           String url = "/WEB-INF/admin/comboList.jsp";
           
           Combo c = new Combo();
           
           if("edit".equals(request.getParameter("task"))){
               url = "/WEB-INF/admin/comboEdit.jsp";
               if(request.getParameter("id") != null) {
                   c = bc.findById(Long.valueOf(request.getParameter("id")));
                   request.setAttribute("combo", c);
               }
           }
           
           getList(request);
           return url; 
        } else {
            try {
                response.sendRedirect("FrontController?option=dashboard");
            } catch (IOException ex) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));
            }
        }
        return "/WEB-INF/index.jsp";

    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response
    ) {
        return null;
    }
    
    private void getList(HttpServletRequest request){
        int max = 10;
        int currentPage = 1;
        if(request.getParameter("page") != null){
            try{
                currentPage = Integer.valueOf(request.getParameter("page"));
            } catch (NumberFormatException e) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "La page n'est pas un nombre", "danger"));
            }
        }
        Pagination pagination = new Pagination("combo", currentPage, max, bc.count());
        request.setAttribute("pagination", pagination.getPagination());
        
        List<Combo> combos = bc.findAllByRange(pagination.getMin(), max);
        request.setAttribute("combos", combos);
    }

}
