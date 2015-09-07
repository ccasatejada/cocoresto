package ejb;

import entities.CustomerOrder;
import entities.Employee;
import java.util.ArrayList;
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
    

    public ejbRestaurant() {
        orders = new HashMap();
        employees = new ArrayList();
    }
    
    @Override
    public HashMap<Integer, CustomerOrder> getOrders() {
        return orders;
    }

    @Override
    public List<Employee> getEmployees() {
        return employees;
    }

    @Override
    public void addCustomerOrder(CustomerOrder order) {
        orders.put(order.getCustomerTable().getNumber(), order);
    }
    
    @Override
    public void removeCustomerOrder(Integer key){
        orders.remove(key);
    }
    
    @Override
    public CustomerOrder getOrder(Integer key) {
        return orders.get(key);
    }
    
    @Override
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
    
    @Override
    public void removeEmployee(Employee employee){
        employees.remove(employee);
    }
    
    @Override
    public boolean isEmployeeLogged(Employee employee){
        return employees.contains(employee);
    }

}
