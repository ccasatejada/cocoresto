package models;

import ejb.ejbCustomerOrderLocal;
import entities.CustomerOrder;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanOrderCustomer implements Serializable {

    ejbCustomerOrderLocal ejbCustomerOrder = lookupejbCustomerOrderLocal();

    public beanOrderCustomer() {
    }
    
        public List<CustomerOrder> findAll() {
        return ejbCustomerOrder.findAll();
    }
    
    
    public List<CustomerOrder> findAllByRange(int firstResult, int maxResults) {
        return ejbCustomerOrder.findAllByRange(firstResult, maxResults);
    }
    
    public void create(CustomerOrder customerTable) throws EJBException {
        ejbCustomerOrder.create(customerTable);

    }

    public void update(CustomerOrder customerTable) throws EJBException {
        ejbCustomerOrder.update(customerTable);

    }

    public void delete(CustomerOrder customerTable) throws EJBException {
        ejbCustomerOrder.delete(customerTable);
    }

    public CustomerOrder findById(Long id) {
        return ejbCustomerOrder.findById(id);
    }

    public int count() {
        return ejbCustomerOrder.count();
    }

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
