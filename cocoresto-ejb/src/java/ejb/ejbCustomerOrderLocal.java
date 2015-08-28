package ejb;

import entities.CustomerOrder;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ejbCustomerOrderLocal {
    
    public void create(CustomerOrder customerTable);

    public void update(CustomerOrder customerTable);
    
    public void delete(CustomerOrder customerTable);

    public CustomerOrder findById(Long id);

    public List<CustomerOrder> findAll();
    
    public List<CustomerOrder> findAllByRange(int firstResult, int maxResults);

    public int count();    
    
}
