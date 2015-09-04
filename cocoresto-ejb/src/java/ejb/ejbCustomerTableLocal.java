package ejb;

import entities.CustomerTable;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ejbCustomerTableLocal {
    
    public void create(CustomerTable customerTable);

    public void update(CustomerTable customerTable);
    
    public void delete(CustomerTable customerTable);

    public CustomerTable findById(Long id);

    public List<CustomerTable> findAll();
    
    public List<CustomerTable> findAvailable(int nb);
    
    public List<CustomerTable> findAllByRange(int firstResult, int maxResults);

    public int count();
    
    public int countMaxCapacity();    

    public CustomerTable findByNumber(Integer number);

}
