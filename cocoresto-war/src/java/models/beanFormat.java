
package models;

import ejb.ejbFormatLocal;
import entities.Format;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanFormat implements Serializable {
    ejbFormatLocal ejbFormat = lookupejbFormatLocal();

    public Format findById(Long id) {
        return ejbFormat.findById(id);
    }
    
    
    private ejbFormatLocal lookupejbFormatLocal() {
        try {
            Context c = new InitialContext();
            return (ejbFormatLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbFormat!ejb.ejbFormatLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
    
}
