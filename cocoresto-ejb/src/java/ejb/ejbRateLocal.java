
package ejb;

import entities.Discount;
import entities.Employee;
import entities.EmployeeGroup;
import entities.Tax;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.Local;

@Local
public interface ejbRateLocal {
    
    public void create(Tax tax);
    
    public void create(Discount discount);

    public void delete(Tax tax);
    
    public void delete(Discount discount);

    public void update(Tax tax);
    
    public void update(Discount discount);

    public Tax findTaxById(Long id);
    
    public Discount findDiscountById(Long id);

    public void persist(Object object);

    public ArrayList<Tax> findAllTaxes();
    
    public ArrayList<Discount> findAllDiscounts();

    public Discount findByDates(Double rate, Date beginDate, Date endDate);
}
