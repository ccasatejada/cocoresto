package models;

import ejb.ejbCustomerOrderLocal;
import ejb.ejbRestaurantLocal;
import entities.CustomerOrder;
import entities.OrderStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanOrderCustomer implements Serializable {

    ejbRestaurantLocal ejbRestaurant = lookupejbRestaurantLocal();
    ejbCustomerOrderLocal ejbCustomerOrder = lookupejbCustomerOrderLocal();

    public beanOrderCustomer() {
    }

    public List<CustomerOrder> findAll() {
        return ejbCustomerOrder.findAll();
    }

    public List<CustomerOrder> findAllByRange(int firstResult, int maxResults) {
        return ejbCustomerOrder.findAllByRange(firstResult, maxResults);
    }

    public List<CustomerOrder> findAllByRangeByEmployee(int firstResult, int maxResults, Long id) {
        return ejbCustomerOrder.findAllByRangeByEmployee(firstResult, maxResults, id);
    }

    public void create(CustomerOrder customerOrder) throws EJBException {
        ejbCustomerOrder.create(customerOrder);
        ejbRestaurant.addCustomerOrder(customerOrder);
    }

    public void update(CustomerOrder customerOrder) throws EJBException {
        ejbCustomerOrder.update(customerOrder);

    }

    public void delete(CustomerOrder customerOrder) throws EJBException {
        ejbCustomerOrder.delete(customerOrder);
        ejbRestaurant.removeCustomerOrder(customerOrder.getNbTablet());
    }

    public CustomerOrder findById(Long id) {
        return ejbCustomerOrder.findById(id);
    }

    public int count() {
        if (ejbCustomerOrder.count() != 0) {
            return ejbCustomerOrder.count();
        }
        return 0;
    }

    public OrderStatus[] findAllOrderStatus() {
        return OrderStatus.values();
    }

    public void cancelCustomerOrder(CustomerOrder customerOrder) {

        // update order status and persist
        customerOrder.setStatus(OrderStatus.CANCELLED);
        this.update(customerOrder);

        // remove order in active orders collection
        ejbRestaurant.removeCustomerOrder(customerOrder.getNbTablet());

    }

    public void restoreCurrentOrders() {
        List<CustomerOrder> orders = ejbCustomerOrder.findCurrentOrders();
        for (CustomerOrder co : orders) {
            ejbRestaurant.addCustomerOrder(co);
        }
    }

    public List<CustomerOrder> getNeedHelpOrders() {
        ArrayList<CustomerOrder> aco = new ArrayList();
        for (Entry<Integer, CustomerOrder> entry : ejbRestaurant.getOrders().entrySet()) {
            CustomerOrder order = entry.getValue();
            System.out.println(order.getCustomerTable().getNumber());
            System.out.println(order.isNeedHelp());
            if (order.isNeedHelp() == true) {
                aco.add(order);
            }
        }
        return aco;
    }

    public int getNbHelp() {
        int i = 0;
        for (Entry<Integer, CustomerOrder> entry : ejbRestaurant.getOrders().entrySet()) {
            CustomerOrder order = entry.getValue();
            System.out.println(order.getCustomerTable().getNumber());
            System.out.println(order.isNeedHelp());
            if (order.isNeedHelp() == true) {
                i++;
            }
        }
        
        return i;
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

    private ejbRestaurantLocal lookupejbRestaurantLocal() {
        try {
            Context c = new InitialContext();
            return (ejbRestaurantLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbRestaurant!ejb.ejbRestaurantLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
