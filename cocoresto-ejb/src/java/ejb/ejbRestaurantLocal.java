package ejb;

import entities.CustomerOrder;
import entities.Employee;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ejbRestaurantLocal {

    public HashMap<Integer, CustomerOrder> getOrders();

    public List<Employee> getEmployees();

    public void addCustomerOrder(CustomerOrder order);

    public void removeCustomerOrder(Integer key);

    public CustomerOrder getOrder(Integer key);

    public void addEmployee(Employee employee);

    public void removeEmployee(Employee employee);
    
    public boolean isEmployeeLogged(Employee employee);
}
