package models;

import ejb.ejbCustomerOrderLocal;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanOrderCustomer implements Serializable {

    ejbCustomerOrderLocal ejbCustomerOrder = lookupejbCustomerOrderLocal();

    private ejbCustomerOrderLocal lookupejbCustomerOrderLocal() {
        try {
            Context c = new InitialContext();
            return (ejbCustomerOrderLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbCustomerOrder!ejb.ejbCustomerOrderLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
