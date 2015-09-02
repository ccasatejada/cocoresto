
package models;

import ejb.ejbRateLocal;
import entities.Discount;
import entities.Tax;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanRate implements Serializable {
    
    ejbRateLocal ejbRate = lookupejbRateLocal();
    
    Tax tax;
    Discount discount;

    public beanRate() {
        tax = new Tax();
        discount = new Discount();
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
    
    public int discountCount() throws EJBException {
        if(ejbRate.discountCount() != 0) {
        return ejbRate.discountCount();
        }
        return 0;
    }
    
    public int taxCount() throws EJBException {
        if(ejbRate.taxCount() != 0) {
        return ejbRate.taxCount();
        }
        return 0;
    }
    
    public void create(Tax tax) throws EJBException {
        ejbRate.create(tax);
    }
    
    public void create(Discount discount) throws EJBException {
        ejbRate.create(discount);
    }

    public void delete(Tax tax) throws EJBException {
        ejbRate.delete(tax);
    }
    
    public void delete(Discount discount) throws EJBException {
        ejbRate.delete(discount);
    }

    public void update(Tax tax) throws EJBException {
        ejbRate.update(tax);
    }
    
    public void update(Discount discount) throws EJBException {
        ejbRate.update(discount);
    }

    public Tax findTaxById(Long id) throws EJBException {
        return ejbRate.findTaxById(id);
    }
    
    public Discount findDiscountById(Long id) throws EJBException {
        return ejbRate.findDiscountById(id);
    }

    public ArrayList<Tax> findAllTaxes() throws EJBException {
        return ejbRate.findAllTaxes();
    }
    
    public ArrayList<Discount> findAllDiscounts() throws EJBException {
        return ejbRate.findAllDiscounts();
    }
    
    public List<Tax> findAllTaxesByRange(int firstResult, int maxResults) {
        return ejbRate.findAllTaxesByRange(firstResult, maxResults);
    }
    
    public List<Discount> findAllDiscountsByRange(int firstResult, int maxResults) {
        return ejbRate.findAllDiscountsByRange(firstResult, maxResults);
    }

    
    public Discount findByDates(Double rate, Date beginDate, Date endDate) throws EJBException{
        return ejbRate.findByDates(rate, beginDate, endDate);
    }
    
    
    
    private ejbRateLocal lookupejbRateLocal() {
        try {
            Context c = new InitialContext();
            return (ejbRateLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbRate!ejb.ejbRateLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
    
}
