package ejb;

import entities.CustomerOrder;
import entities.Employee;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ejbRestaurant implements ejbRestaurantLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;
    
    private HashMap<Integer, CustomerOrder> orders;
    private List<Employee> employees;

    public HashMap<Integer, CustomerOrder> getOrders() {
        return orders;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addCustomerOrder(CustomerOrder order) {
        orders.put(order.getCustomerTable().getNumber(), order);
    }
    
    

}