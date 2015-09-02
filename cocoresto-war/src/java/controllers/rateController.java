package controllers;

import entities.Discount;
import entities.Drink;
import entities.Tax;
import helpers.Alert;
import helpers.Pagination;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanDrink;
import models.beanRate;

public class rateController implements IController {

    private beanRate bRate = new beanRate();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        boolean logged = false;
        Long groupId = 0L;

        Tax tax;
        Discount discount;

        bRate = (beanRate) session.getAttribute("bRate");
        if (bRate == null) {
            bRate = new beanRate();
            tax = new Tax();
            discount = new Discount();
            session.setAttribute("bRate", bRate);
        } else {
            tax = bRate.getTax();
            discount = bRate.getDiscount();
        }

        if (session.getAttribute("logged") != null && session.getAttribute("group") != null) {
            logged = (boolean) session.getAttribute("logged");
            groupId = (Long) session.getAttribute("group");
        }

        if (logged && groupId >= 3) {

            if ("editTax".equals(request.getParameter("task"))) {
                session.removeAttribute("tax");
                return "/WEB-INF/admin/taxEdit.jsp";
            }

            if ("editDiscount".equals(request.getParameter("task"))) {
                session.removeAttribute("discount");
                return "/WEB-INF/admin/discountEdit.jsp";
            }

            if ("modifyTax".equals(request.getParameter("task"))) {
                tax = bRate.findTaxById(Long.valueOf(request.getParameter("id")));
                session.setAttribute("tax", tax);
                return "/WEB-INF/admin/taxEdit.jsp";
            }

            if ("modifyDiscount".equals(request.getParameter("task"))) {
                discount = bRate.findDiscountById(Long.valueOf(request.getParameter("id")));
                session.setAttribute("discount", discount);
                return "/WEB-INF/admin/discountEdit.jsp";
            }

            if ("deleteTax".equals(request.getParameter("task"))) {
                tax = bRate.findTaxById(Long.valueOf(request.getParameter("id")));
                bRate.delete(tax);
            }

            if ("deleteDiscount".equals(request.getParameter("task"))) {
                discount = bRate.findDiscountById(Long.valueOf(request.getParameter("id")));
                bRate.delete(discount);
            }

            if (request.getParameter("createTax") != null) {
                tax.setRate(Double.valueOf(request.getParameter("amount")));
                bRate.create(tax);
            }

            if (request.getParameter("createDiscount") != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                discount.setRate(Double.valueOf(request.getParameter("amount")));
                try {
                    discount.setBeginDate(formatter.parse(request.getParameter("beginDate")));
                    discount.setEndDate(formatter.parse(request.getParameter("endDate")));
                } catch (ParseException ex) {
                    ex.getMessage();
                }

                bRate.create(discount);
            }

            if (request.getParameter("modifyTax") != null) {
                tax = (Tax) session.getAttribute("tax");
                tax.setRate(Double.valueOf(request.getParameter("amount")));
                bRate.update(tax);
            }

            if (request.getParameter("modifyDiscount") != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                discount = (Discount) session.getAttribute("discount");
                discount.setRate(Double.valueOf(request.getParameter("amount")));
                try {
                    discount.setBeginDate(formatter.parse(request.getParameter("beginDate")));
                    discount.setEndDate(formatter.parse(request.getParameter("endDate")));
                } catch (ParseException ex) {
                    ex.getMessage();
                }
                bRate.update(discount);
            }

            if (request.getParameter("attachDiscount") != null) {
                Drink drink = (Drink) session.getAttribute("drink");
                beanDrink bDrink = (beanDrink) session.getAttribute("bDrink");
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                discount.setRate(Double.valueOf(request.getParameter("amount")));
                try {
                    discount.setBeginDate(formatter.parse(request.getParameter("beginDate")));
                    discount.setEndDate(formatter.parse(request.getParameter("endDate")));
                } catch (ParseException ex) {
                    ex.getMessage();
                }
                bRate.create(discount);
                drink.setDiscount(discount);
                bDrink.update(drink);
                session.setAttribute("drink", drink);
                session.setAttribute("bDrink", bDrink);
                System.out.println(request.getRequestURI());
                try {
                    response.sendRedirect(request.getRequestURI() + "?option=drink&task=modify&id=" + drink.getId());
                } catch (IOException ex) {
                    ex.getMessage();
                }
                return "/WEB-INF/admin/drinkEdit.jsp";
            }

            if ("rate".equals(request.getParameter("option"))) {
                getTaxList(request, "option=rate");
                getDiscountList(request, "option=rate");
                return "/WEB-INF/admin/rateList.jsp";
            }

        } else {
            try {
                // not logged or wrong groupId
                response.sendRedirect("FrontController?option=dashboard");
            } catch (IOException ex) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));
            }
        }

        return "/WEB-INF/admin/rateList.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void getTaxList(HttpServletRequest request, String queryString) {

        /* pagination */
        Pagination pagination = new Pagination(queryString, request.getParameter("page"), 3, bRate.taxCount());
        request.setAttribute("taxPagination", pagination.getPagination());

        List<Tax> taxes = bRate.findAllTaxesByRange(pagination.getMin(), 3);
        request.setAttribute("taxes", taxes);
    }

    private void getDiscountList(HttpServletRequest request, String queryString) {

        /* pagination */
        Pagination pagination = new Pagination(queryString, request.getParameter("page"), 3, bRate.discountCount());
        request.setAttribute("discountPagination", pagination.getPagination());

        List<Discount> discounts = bRate.findAllDiscountsByRange(pagination.getMin(), 3);
        request.setAttribute("discounts", discounts);
    }

}
