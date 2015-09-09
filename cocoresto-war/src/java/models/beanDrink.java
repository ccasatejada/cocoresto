
package models;

import ejb.ejbDrinkLocal;
import entities.Category;
import entities.Discount;
import entities.Drink;
import entities.Format;
import entities.Price;
import entities.Tax;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanDrink implements Serializable {
    
    ejbDrinkLocal ejbDrink = lookupejbDrinkLocal();
    
    Drink drink;
    Format format;
    Category category;
    Price price;
    Tax tax;
    Discount discount;
    ArrayList<Format> formats;

    public beanDrink() {
        drink = new Drink();
        formats = new ArrayList();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
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

    
    
    public ArrayList<Format> getFormats() {
        return formats;
    }

    public void setFormats(ArrayList<Format> formats) {
        this.formats = formats;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    
    
    
    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }
    
    public int count() throws EJBException{
        if(ejbDrink.count() != 0) {
        return ejbDrink.count();
        }
        return 0;  
        
    }
    
    public void create(Drink drink) throws EJBException {
        ejbDrink.create(drink);
    }

    public void delete(Drink drink) throws EJBException {
        ejbDrink.delete(drink);
    }

    public void update(Drink drink) throws EJBException {
        ejbDrink.update(drink);
    }

    public Drink findById(Long id) throws EJBException {
        return ejbDrink.findById(id);
    }

    public ArrayList<Drink> findAll() throws EJBException {
        return ejbDrink.findAll();
    }
    
    public List<Drink> findAllByCategory(String type) {
        return ejbDrink.findAllByCategory(type);
    }
    
    public List<Drink> findAllByRange(int firstResult, int maxResults) {
        return ejbDrink.findAllByRange(firstResult, maxResults);
    }

    public ArrayList<Format> findFormats() throws EJBException {
        return ejbDrink.findFormats();
    }
    
    public ArrayList<Category> findCategories() throws EJBException {
        return ejbDrink.findCategories();
    }

    private ejbDrinkLocal lookupejbDrinkLocal() {
        try {
            Context c = new InitialContext();
            return (ejbDrinkLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbDrink!ejb.ejbDrinkLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
