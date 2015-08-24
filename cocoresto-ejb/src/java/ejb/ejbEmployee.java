
package ejb;

import entities.Employee;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ejbEmployee implements ejbEmployeeLocal {
    
    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    @Override
    public void create(Employee emp) {

    }
    
    @Override
    public void delete(Employee emp) {
        
    }
    
    @Override
    public void update(Employee emp) {
        
    }
    
    @Override
    public Employee findById(Integer id) {
        Employee employee = new Employee();
        
        return employee;
    }
    
    @Override
    public ArrayList<Employee> findAll(){
        Employee employee = new Employee();
        ArrayList<Employee> employees = new ArrayList();
        
        
        return employees;
    }
    
    @Override
    public void persist(Object object) {
        em.persist(object);
    }

}
