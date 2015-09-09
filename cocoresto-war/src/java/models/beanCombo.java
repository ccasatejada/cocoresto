package models;

import ejb.ejbComboLocal;
import entities.Category;
import entities.Combo;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanCombo implements Serializable {
    ejbComboLocal ejbCombo = lookupejbComboLocal();
    
    
    
    public beanCombo(){
        
    }
    
    public void create(Combo combo) throws EJBException {
        ejbCombo.create(combo);
    }
    
    public void update(Combo combo) throws EJBException {
        ejbCombo.update(combo);
    }
    
    public void delete(Combo combo) throws EJBException {
        ejbCombo.delete(combo);
    }
    
    public Combo findById(Long id) throws EJBException {
        if(ejbCombo.findById(id) != null){
            return ejbCombo.findById(id);
        }
        return null;
    }
    
    public int count(){
        return ejbCombo.count();
    }
    
    public List<Category> findCategories(){
        return ejbCombo.findCategories();
    }
    
    public List<Combo> findAllByRange(int firstResult, int maxResults) {
        return ejbCombo.findAllByRange(firstResult, maxResults);
    }
    
    public List<Combo> findAllByCategory(String type) {
        return ejbCombo.findAllByCategory(type);
    }
    
    private ejbComboLocal lookupejbComboLocal() {
        try {
            Context c = new InitialContext();
            return (ejbComboLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbCombo!ejb.ejbComboLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }


}
