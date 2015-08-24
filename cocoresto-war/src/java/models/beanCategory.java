package models;

import ejb.ejbCategoryLocal;
import entities.Category;
import java.beans.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanCategory implements Serializable {
    ejbCategoryLocal ejbCategory = lookupejbCategoryLocal();
    

    public boolean create(Category category){
        return ejbCategory.create(category);
    }

    public boolean update(Category category){
        return ejbCategory.update(category);
    }

    public boolean delete(Category category){
        return ejbCategory.delete(category);
    }

    public Category findById(Long id){
        return ejbCategory.findById(id);
    }

    public Collection<Category> findAll(){
        return ejbCategory.findAll();
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
