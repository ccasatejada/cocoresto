
package controllers;

import entities.Discount;
import entities.Employee;
import entities.EmployeeGroup;
import entities.Tax;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanEmployee;
import models.beanRate;

public class rateController implements IController{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        
        Tax tax;
        Discount discount;
        
        beanRate bRate = (beanRate) session.getAttribute("bRate");
        if(bRate == null) {
            bRate = new beanRate();
            tax = new Tax();
            discount = new Discount();
            session.setAttribute("bRate", bRate);
        } else {
            tax = bRate.getTax();
            discount = bRate.getDiscount();
        }
                
        if("editTax".equals(request.getParameter("task"))) {
            session.removeAttribute("tax");
            return "/WEB-INF/admin/taxEdit.jsp";
        }
        
        if("editDiscount".equals(request.getParameter("task"))) {
            session.removeAttribute("discount");
            return "/WEB-INF/admin/discountEdit.jsp";
        }
        
        if("modifyTax".equals(request.getParameter("task"))) {
            tax = bRate.findTaxById(Long.valueOf(request.getParameter("id")));
            session.setAttribute("tax", tax);
            return "/WEB-INF/admin/taxEdit.jsp";
        }
        
        if("modifyDiscount".equals(request.getParameter("task"))) {
            discount = bRate.findDiscountById(Long.valueOf(request.getParameter("id")));
            session.setAttribute("discount", discount);
            return "/WEB-INF/admin/discountEdit.jsp";
        }
        
        if("deleteTax".equals(request.getParameter("task"))) {
            tax = bRate.findTaxById(Long.valueOf(request.getParameter("id")));
            bRate.delete(tax);
        }
        
        if("deleteDiscount".equals(request.getParameter("task"))) {
            discount = bRate.findDiscountById(Long.valueOf(request.getParameter("id")));
            bRate.delete(discount);
        }
        
        if(request.getParameter("createTax") != null) {
            tax.setRate(Double.valueOf(request.getParameter("amount")));
            bRate.create(tax);
        }
        
        if(request.getParameter("createDiscount") != null) {
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
        
        if(request.getParameter("modifyTax") != null) {
            tax = (Tax)session.getAttribute("tax");
            tax.setRate(Double.valueOf(request.getParameter("amount")));
            bRate.update(tax);
        }
        
        if(request.getParameter("modifyDiscount") != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            discount = (Discount)session.getAttribute("discount");
            discount.setRate(Double.valueOf(request.getParameter("amount")));
            try {
                discount.setBeginDate(formatter.parse(request.getParameter("beginDate")));
                discount.setEndDate(formatter.parse(request.getParameter("endDate")));
            } catch (ParseException ex) {
                ex.getMessage();
            }
            bRate.update(discount);
        }
        
        if("rate".equals(request.getParameter("option"))) {
            ArrayList<Tax> taxes = bRate.findAllTaxes();
            session.setAttribute("taxes", taxes);
            ArrayList<Discount> discounts = bRate.findAllDiscounts();
            session.setAttribute("discounts", discounts);
            return "/WEB-INF/admin/rateList.jsp";
        }
        
        
        return "/WEB-INF/admin/rateList.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
