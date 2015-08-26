
package controllers;

import entities.Category;
import entities.Drink;
import entities.Format;
import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanDrink;

public class drinkController implements IController{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        
        Drink drink;
        Category category;
        ArrayList<Format> formats;
        Format format;
        
        beanDrink bDrink = (beanDrink) session.getAttribute("bDrink");
        if(bDrink == null) {
            bDrink = new beanDrink();
            drink = new Drink();
            category = new Category();
            formats = new ArrayList();
            format = new Format();
            drink.setFormats(formats);
            bDrink.setDrink(drink);
            session.setAttribute("bDrink", bDrink);
        } else {
            drink = bDrink.getDrink();
            format = bDrink.getFormat();
            formats = bDrink.getFormats();
        }
        
        formats = bDrink.findFormats();
        
        if("edit".equals(request.getParameter("task"))) {
            session.setAttribute("formats", formats);
            session.removeAttribute("drink");
            return "/WEB-INF/admin/drinkEdit.jsp";
        }
        
        if("modify".equals(request.getParameter("task"))) {
            drink = bDrink.findById(Long.valueOf(request.getParameter("id")));
            session.setAttribute("formats", formats);
            session.setAttribute("drink", drink);
            return "/WEB-INF/admin/drinkEdit.jsp";
        }
        
        if("delete".equals(request.getParameter("task"))) {
            drink = bDrink.findById(Long.valueOf(request.getParameter("id")));
            drink.setActive(false);
            bDrink.delete(drink);
        }
        
        if(request.getParameter("createIt") != null) {
            drink.setActive(true);
            formats = (ArrayList<Format>)session.getAttribute("formats");
            for(Format fo : formats) {
                if(fo.getName().equals(request.getParameter("comboFormat"))) {
                    System.out.println(fo.getId());
                    format.setId(fo.getId());
                    format.setName(request.getParameter("comboFormat"));
                    break;
                }
            }
            drink.setFormats(formats);
//            emp.setFirstName(request.getParameter("firstName"));
//            emp.setLastName(request.getParameter("lastName"));
//            emp.setPassword(request.getParameter("password"));
            bDrink.create(drink);
            session.setAttribute("drink", drink);

        }
        
        if(request.getParameter("modifyIt") != null) {
            drink = (Drink)session.getAttribute("drink");
            for(Format fo : formats) {
                if(fo.getName().equals(request.getParameter("comboFormat"))) {
                    format.setId(fo.getId());
                    format.setName(request.getParameter("comboFormat"));
                    break;
                }
            }
            drink.setFormats(formats);
//            drink.setFirstName(request.getParameter("firstName"));
//            drink.setLastName(request.getParameter("lastName"));
//            drink.setPassword(request.getParameter("password"));
            bDrink.update(drink);
        }
        
        if("drink".equals(request.getParameter("option"))) {
            ArrayList<Drink> drinks = bDrink.findAll();
            session.setAttribute("drinks", drinks);
            return "/WEB-INF/admin/drinkList.jsp";
        }
        
        return "/WEB-INF/admin/drinkEdit.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
