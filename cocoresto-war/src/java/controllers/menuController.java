package controllers;

import entities.Category;
import entities.Combo;
import entities.Discount;
import entities.Dish;
import entities.Drink;
import entities.Price;
import entities.Tax;
import helpers.Pagination;
import java.util.List;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanCategory;
import models.beanCombo;
import models.beanDish;
import models.beanDrink;
import models.beanNutritiveValue;

public class menuController implements IController {

    private beanDrink bDrink = new beanDrink();
    beanDish bDish = new beanDish();
    beanCombo bCombo = new beanCombo();
    beanNutritiveValue bNutritiveValue = new beanNutritiveValue();
    beanCategory bCategory = new beanCategory();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String url = "/WEB-INF/dashboardCustomer.jsp";

        HttpSession session = request.getSession();

        Drink drink = new Drink();
        Category category = new Category();
        Tax tax = new Tax();
        Discount discount = new Discount();
        Price price = new Price();
        Dish dish = new Dish();
        Combo combo = new Combo();



        if ("getDrinkDetail".equals(request.getParameter("task"))) {
            url = "/WEB-INF/menu/drinkDetail.jsp";
            drink = bDrink.findById(Long.valueOf(request.getParameter("id")));
            request.setAttribute("drink", drink);
            return url;
        }

        if ("getDrinks".equals(request.getParameter("task"))) {
            url = "/WEB-INF/menu/drinkMenu.jsp";
            getList(request, "option=menu", "drinks");
            return url;
        }

        if ("getDishDetail".equals(request.getParameter("task"))) {
            url = "/WEB-INF/menu/dishDetail.jsp";
            dish = bDish.findById(Long.valueOf(request.getParameter("id")));
            request.setAttribute("dish", dish);
            try {
                request.setAttribute("nutritiveValue", bNutritiveValue.findByDish(dish));
            } catch (EJBException e) {
                request.setAttribute("nutritiveValue", null);
            }
            return url;
        }
        if ("getDishes".equals(request.getParameter("task"))) {
            url = "/WEB-INF/menu/dishMenu.jsp";
            request.setAttribute("categories", bDish.findCategories());
            getList(request, "option=menu", "dishes");
            return url;
        }

        if ("getComboDetail".equals(request.getParameter("task"))) {
            url = "/WEB-INF/menu/comboDetail.jsp";
            combo = bCombo.findById(Long.valueOf(request.getParameter("id")));
            request.setAttribute("combo", combo);
            int i = 1;
            for (Dish d : combo.getDishes()) {
                request.setAttribute("dish" + i, d);
                i++;
            }
            return url;
        }

        if ("getCombos".equals(request.getParameter("task"))) {
            url = "/WEB-INF/menu/comboMenu.jsp";
            request.setAttribute("categories", bCombo.findCategories());
            getList(request, "option=menu", "combos");
            return url;
        }

        //getList(request, "option=menu", "");

        return url;
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void getList(HttpServletRequest request, String queryString, String category) {

        /* pagination */
        if ("drinks".equals(category)) {
            Pagination pagination = new Pagination(queryString, request.getParameter("page"), 10, bDrink.count());
            request.setAttribute("pagination", pagination.getPagination());

            List<Drink> drinks = bDrink.findAllByRange(pagination.getMin(), 10);
            request.setAttribute("drinks", drinks);
        }

        if ("dishes".equals(category)) {
            Pagination pagination = new Pagination(queryString, request.getParameter("page"), 10, bDish.count());
            request.setAttribute("pagination", pagination.getPagination());

            List<Dish> dishes = bDish.findAllByRange(pagination.getMin(), 10);
            request.setAttribute("dishes", dishes);
        }

        if ("combos".equals(category)) {
            Pagination pagination = new Pagination(queryString, request.getParameter("page"), 10, bCombo.count());
            request.setAttribute("pagination", pagination.getPagination());

            List<Combo> combos = bCombo.findAllByRange(pagination.getMin(), 10);
            request.setAttribute("combos", combos);
        }
    }
}
