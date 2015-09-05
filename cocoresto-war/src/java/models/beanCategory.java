package models;

import ejb.ejbCategoryLocal;
import entities.Category;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanCategory implements Serializable {

    ejbCategoryLocal ejbCategory = lookupejbCategoryLocal();
    
    public beanCategory() {
    }

    public void create(Category category) throws EJBException {
        ejbCategory.create(category);

    }

    public void update(Category category) throws EJBException {
        ejbCategory.update(category);

    }

    public void delete(Category category) throws EJBException {
        ejbCategory.delete(category);
    }

    public Category findById(Long id) throws EJBException {
        if (ejbCategory.findById(id) != null) {
            return ejbCategory.findById(id);
        }
        return null;
    }

    public ArrayList<Category> findAll() throws EJBException {
        ArrayList<Category> ac = new ArrayList();
        for (Category c : ejbCategory.findAll()) {
            ac.add(c);
        }
        return ac;

    }
    
    public int count(){
        return ejbCategory.count();
    }
    
    public List<Category> findAllByRange(int firstResult, int maxResults){
        return ejbCategory.findAllByRange(firstResult, maxResults);
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
