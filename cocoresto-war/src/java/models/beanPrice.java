
package models;

import ejb.ejbPriceLocal;
import entities.Price;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanPrice implements Serializable {
    ejbPriceLocal ejbPrice = lookupejbPriceLocal();

    public beanPrice() {
    }

    public void create(Price price){
        ejbPrice.create(price);
    }
    
    public void update(Price price){
        ejbPrice.update(price);
    }
    
    public void delete(Price price){
        ejbPrice.delete(price);
    }
    
    public Price findById(Long id){
        if(ejbPrice.findById(id) != null){
            return ejbPrice.findById(id);
        }
        
        return null;
    }
    
    public ArrayList<Price> findAll(){
        ArrayList<Price> ap = new ArrayList();
        for(Price p : ejbPrice.findAll()){
            ap.add(p);
        }
        return ap;
    }
    
    public Price findLastInserted() {
        return ejbPrice.findLastInserted();
    }
    
    private ejbPriceLocal lookupejbPriceLocal() {
        try {
            Context c = new InitialContext();
            return (ejbPriceLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbPrice!ejb.ejbPriceLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
