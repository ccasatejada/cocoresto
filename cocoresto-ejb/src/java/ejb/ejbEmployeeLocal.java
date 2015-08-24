
package ejb;

import entities.Employee;
import java.util.ArrayList;
import javax.ejb.Local;

@Local
public interface ejbEmployeeLocal {

    public void create(Employee emp);

    public void delete(Employee emp);

    public void update(Employee emp);

    public Employee findById(Integer id);

    public void persist(Object object);

    public ArrayList<Employee> findAll();
    
}
