
package ejb;

import entities.Employee;
import entities.EmployeeGroup;
import java.util.ArrayList;
import javax.ejb.Local;

@Local
public interface ejbEmployeeLocal {

    public void create(Employee emp);

    public void delete(Employee emp);

    public void update(Employee emp);

    public Employee findById(Long id);

    public void persist(Object object);

    public ArrayList<Employee> findAll();

    public ArrayList<EmployeeGroup> findGroups();
    
    public int count();
    
}
