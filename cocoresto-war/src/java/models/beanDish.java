package models;

import ejb.ejbDishLocal;
import entities.Dish;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanDish implements Serializable {
    ejbDishLocal ejbDish = lookupejbDishLocal();

    public beanDish() {
    }
       
    
    public boolean create(Dish dish){
       return ejbDish.create(null);
    }
    
    public boolean update(Dish dish){       
        return ejbDish.update(dish);
    }
    
    public Dish findById(Long id){
        Dish d = ejbDish.findById(id);
        
        return d;
    }
    
    public Collection<Dish> findAll(){
        
        return null;
    }
    
    public boolean delete(Dish dish){
        return ejbDish.delete(null);
    }
    

    private ejbDishLocal lookupejbDishLocal() {
        try {
            Context c = new InitialContext();
            return (ejbDishLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbDish!ejb.ejbDishLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
  
    
    
}
