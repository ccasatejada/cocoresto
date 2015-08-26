package models;

import ejb.ejbCustomerTableLocal;
import entities.CustomerTable;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanTableCustomer implements Serializable {

    ejbCustomerTableLocal ejbCustomerTable = lookupejbCustomerTableLocal();

    public beanTableCustomer() {
    }

    public List<CustomerTable> findAll() {
        return ejbCustomerTable.findAll();
    }

    public void create(CustomerTable customerTable) {
        ejbCustomerTable.create(customerTable);

    }

    public void update(CustomerTable customerTable) {
        ejbCustomerTable.update(customerTable);

    }

    public void delete(CustomerTable customerTable) throws EJBException {
        ejbCustomerTable.delete(customerTable);
    }

    public CustomerTable findById(Long id) {
        return ejbCustomerTable.findById(id);
    }

    private ejbCustomerTableLocal lookupejbCustomerTableLocal() {
        try {
            Context c = new InitialContext();
            return (ejbCustomerTableLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbCustomerTable!ejb.ejbCustomerTableLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
