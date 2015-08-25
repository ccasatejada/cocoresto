package models;

import ejb.ejbCategoryLocal;
import entities.Category;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanCategory implements Serializable {

    ejbCategoryLocal ejbCategory = lookupejbCategoryLocal();

    public beanCategory() {
    }

    public boolean create(Category category) {
        try {
            ejbCategory.create(category);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean update(Category category) {
        try {
            ejbCategory.update(category);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public void delete(Category category) {
        ejbCategory.delete(category);
    }

    public Category findById(Long id) {
        if (ejbCategory.findById(id) != null) {
            return ejbCategory.findById(id);
        }
        return null;
    }

    public ArrayList<Category> findAll() {
        ArrayList<Category> ac = new ArrayList();
        for (Category c : ejbCategory.findAll()) {
            ac.add(c);
        }
        return ac;

    }

    private ejbCategoryLocal lookupejbCategoryLocal() {
        try {
            Context c = new InitialContext();
            return (ejbCategoryLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbCategory!ejb.ejbCategoryLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
