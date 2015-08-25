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
        try{
           ejbDish.create(dish);
           return true;
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean update(Dish dish){  
        try{
           ejbDish.update(dish);
           return true;
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public Dish findById(Long id){
        if(ejbDish.findById(id) != null){
            return ejbDish.findById(id);
        }
        return null;
    }
    
    public Collection<Dish> findAll(){
        if(ejbDish.findAll() != null){
            return ejbDish.findAll();
        }
        return null;
    }
    
    public boolean delete(Dish dish){
        try{
           ejbDish.delete(dish);
           return true;
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
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
