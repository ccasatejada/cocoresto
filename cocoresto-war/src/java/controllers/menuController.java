
package controllers;

import entities.Category;
import entities.Discount;
import entities.Drink;
import entities.Format;
import entities.Price;
import entities.Tax;
import helpers.Pagination;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanDrink;
import models.beanFormat;
import models.beanPrice;
import models.beanRate;

public class menuController implements IController {

    private beanDrink bDrink = new beanDrink();
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String url = "/WEB-INF/menu/drinkMenu.jsp";
        
        HttpSession session = request.getSession();
        
        Drink drink;
        Category category;
        Tax tax;
        Discount discount;
        Price price;

        ArrayList<Format> formats;
        ArrayList<Category> categories;
        ArrayList<Discount> discounts;
        ArrayList<Tax> taxes;
        ArrayList<Price> prices;

        bDrink = (beanDrink) session.getAttribute("bDrink");
        beanRate bRate = (beanRate) session.getAttribute("bRate");
        beanPrice bPrice = (beanPrice) session.getAttribute("bPrice");
        beanFormat bFormat = (beanFormat) session.getAttribute("bFormat");

        if (bDrink == null) {
            bDrink = new beanDrink();
            drink = new Drink();
            category = new Category();
            formats = new ArrayList();
            price = new Price();
            tax = new Tax();
            discount = new Discount();
            drink.setFormats(formats);
            bDrink.setDrink(drink);
            session.setAttribute("bDrink", bDrink);
        } else {
            drink = bDrink.getDrink();
            category = bDrink.getCategory();
            price = bDrink.getPrice();
            tax = bDrink.getTax();
            discount = bDrink.getDiscount();
        }
        if (bRate == null) {
            bRate = new beanRate();
            drink = new Drink();
            category = new Category();
            session.setAttribute("bRate", bRate);
        }
        if (bPrice == null) {
            bPrice = new beanPrice();
            price = new Price();
            drink = new Drink();
            category = new Category();
            session.setAttribute("bPrice", bPrice);
        }
        if (bFormat == null) {
            bFormat = new beanFormat();
            session.setAttribute("bFormat", bFormat);
        }

        formats = bDrink.findFormats();
        categories = bDrink.findCategories();
        discounts = bRate.findAllDiscounts();
        taxes = bRate.findAllTaxes();
        prices = bPrice.findAll();
        
        if("getDrinkDetail".equals(request.getParameter("task"))) {
            url = "/WEB-INF/menu/drinkDetail.jsp";
            drink = bDrink.findById(Long.valueOf(request.getParameter("id")));
            session.setAttribute("drink", drink);
            return url;
        }
        
        getList(request, "option=menu");
        
        return url;
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void getList(HttpServletRequest request, String queryString) {

        /* pagination */
        Pagination pagination = new Pagination(queryString, request.getParameter("page"), 10, bDrink.count());
        request.setAttribute("pagination", pagination.getPagination());

        List<Drink> drinks = bDrink.findAllByRange(pagination.getMin(), 10);
        request.setAttribute("drinks", drinks);
    }
    
}
