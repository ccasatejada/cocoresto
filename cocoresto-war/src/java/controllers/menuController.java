package controllers;

import entities.Combo;
import entities.Dish;
import entities.Drink;
import helpers.Alert;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.beanCombo;
import models.beanDish;
import models.beanDrink;

public class menuController implements IController {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        if ("detail".equals(request.getParameter("task")) && request.getParameter("type") != null && request.getParameter("id") != null) {

            if ("Plat".equals(request.getParameter("type"))) {
                beanDish bdish = new beanDish();
                try {
                    Dish d = bdish.findById(Long.valueOf(request.getParameter("id")));
                    request.setAttribute("dish", d);
                } catch (NumberFormatException | EJBException e) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Cet item n'existe pas", "danger"));
                }
                return "/WEB-INF/menu/dishDetail.jsp";
            }

            if ("Boisson".equals(request.getParameter("type"))) {
                beanDrink bdrink = new beanDrink();
                try {
                    Drink d = bdrink.findById(Long.valueOf(request.getParameter("id")));
                    request.setAttribute("drink", d);
                } catch (NumberFormatException | EJBException e) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Cet item n'existe pas", "danger"));
                }
                return "/WEB-INF/menu/drinkDetail.jsp";
            }

            if ("Menu".equals(request.getParameter("type"))) {
                beanCombo bcombo = new beanCombo();
                try {
                    Combo c = bcombo.findById(Long.valueOf(request.getParameter("id")));
                    request.setAttribute("combo", c);
                } catch (NumberFormatException | EJBException e) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Ce menu n'existe pas", "danger"));
                }
                return "/WEB-INF/menu/comboDetail.jsp";
            }

        }

        return "";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
