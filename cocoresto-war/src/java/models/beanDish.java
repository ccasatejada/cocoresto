package models;

import ejb.ejbDishLocal;
import entities.Dish;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

    public Double calculPrice(Dish dish) {
        Double price = null;
        Date d = new Date();

        if (d.before(dish.getDiscount().getEndDate()) && d.after(dish.getDiscount().getBeginDate())) {
            price = dish.getPrice().getPrice() - (dish.getPrice().getPrice() * (dish.getDiscount().getRate() / 100));
        } else {
            price = dish.getPrice().getPrice();
        }
        
        price = price * (1 + (dish.getTax().getRate() / 100));
        return price;
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
