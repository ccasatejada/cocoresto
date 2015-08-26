package ejb;

import entities.Category;
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

    
    
}
