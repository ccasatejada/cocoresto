package ejb;

import entities.CustomerOrder;
import entities.Employee;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ejbCustomerOrderLocal {
    
    public void create(CustomerOrder customerOrder);

    public void update(CustomerOrder customerOrder);
    
    public void delete(CustomerOrder customerOrder);

    public CustomerOrder findById(Long id);

    public List<CustomerOrder> findAll();
    
    public List<CustomerOrder> findAllByRange(int firstResult, int maxResults);
    
    public List<CustomerOrder> findAllByRangeByEmployee(int firstResult, int maxResults, Long id);

    public int count();    
    
}
