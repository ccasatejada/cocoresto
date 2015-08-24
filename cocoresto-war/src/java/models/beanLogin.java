package models;

import ejb.ejbLoginLocal;
import entities.Employee;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanLogin implements Serializable {

    ejbLoginLocal ejbLogin = lookupejbLoginLocal();

    public beanLogin() {
    }

    public Employee login(String password) {
        return ejbLogin.login(password);
    }

    private ejbLoginLocal lookupejbLoginLocal() {
        try {
            Context c = new InitialContext();
            return (ejbLoginLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbLogin!ejb.ejbLoginLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
