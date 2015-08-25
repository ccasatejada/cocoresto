
package models;

import ejb.ejbNutritiveValueLocal;
import entities.NutritiveValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanNutritiveValue implements Serializable {
    ejbNutritiveValueLocal ejbNutritiveValue = lookupejbNutritiveValueLocal();

    public beanNutritiveValue() {
    }

    public void create(NutritiveValue nv){
        ejbNutritiveValue.create(nv);
    }
    
    public void update(NutritiveValue nv){
        ejbNutritiveValue.update(nv);
    }
    
    public void delete(NutritiveValue nv){
        ejbNutritiveValue.delete(nv);
    }
    
    public NutritiveValue findById(Long id){
        if(ejbNutritiveValue.findById(id) != null){
            return ejbNutritiveValue.findById(id);
        }
        return null;
    }
    
    public ArrayList<NutritiveValue> findAll(){
        ArrayList<NutritiveValue> anv = new ArrayList();
        for(NutritiveValue nv : ejbNutritiveValue.findAll()){
            anv.add(nv);
        }
        return anv;
    }
    
    
    private ejbNutritiveValueLocal lookupejbNutritiveValueLocal() {
        try {
            Context c = new InitialContext();
            return (ejbNutritiveValueLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbNutritiveValue!ejb.ejbNutritiveValueLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
}
