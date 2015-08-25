package models;

import ejb.ejbDishLocal;
import entities.Dish;
import java.io.Serializable;
import java.util.ArrayList;
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

    public void create(Dish dish) {
        ejbDish.create(dish);

    }

    public void update(Dish dish) {
        ejbDish.update(dish);

    }

    public Dish findById(Long id) {
        if (ejbDish.findById(id) != null) {
            return ejbDish.findById(id);
        }
        return null;
    }

    public ArrayList<Dish> findAll() {
        ArrayList<Dish> ad = new ArrayList();
        for (Dish d : ejbDish.findAll()) {
            ad.add(d);
        }
        return ad;
    }

    public void delete(Dish dish) {
        ejbDish.delete(dish);
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
