
package ejb;

import entities.Employee;
import entities.EmployeeGroup;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ejbEmployeeLocal {

    public void create(Employee emp);

    public void delete(Employee emp);

    public void update(Employee emp);

    public Employee findById(Integer id);

    public void persist(Object object);

    public ArrayList<Employee> findAll();

    public List<EmployeeGroup> findGroups();
    
}
