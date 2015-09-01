package models;

import ejb.ejbDishLocal;
import entities.Category;
import entities.Dish;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanDish implements Serializable {

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    ejbDishLocal ejbDish = lookupejbDishLocal();

    public beanDish() {
    }

    public void create(Dish dish) throws EJBException{
        ejbDish.create(dish);

    }

    public void update(Dish dish) throws EJBException{
        ejbDish.update(dish);

    }

    public Dish findById(Long id) throws EJBException{
        if (ejbDish.findById(id) != null) {
            return ejbDish.findById(id);
        }
        return null;
    }

    public ArrayList<Dish> findAll() throws EJBException{
        ArrayList<Dish> ad = new ArrayList();
        for (Dish d : ejbDish.findAll()) {
            ad.add(d);
        }
        return ad;
    }

    public void delete(Dish dish) throws EJBException{
        ejbDish.delete(dish);
    }


    public int count(){
        return ejbDish.count();
    }
    
    public List<Category> findCategories(){
        return ejbDish.findCategories();
    }
    
    public List<Dish> findAllByRange(int firstResult, int maxResults){
        return ejbDish.findAllByRange(firstResult, maxResults);
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
