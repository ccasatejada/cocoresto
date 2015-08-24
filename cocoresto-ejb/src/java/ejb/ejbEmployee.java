
package ejb;

import entities.Employee;
import entities.EmployeeGroup;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ejbEmployee implements ejbEmployeeLocal {
    
    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    @Override
    public List<EmployeeGroup> findGroups() {
        
        String query = "SELECT eg FROM EmployeeGroup eg";
        Query qr = em.createQuery(query);
        
        List<EmployeeGroup> egList = qr.getResultList();
//        EmployeeGroup eg = new EmployeeGroup();
//        for(EmployeeGroup eGroup : egList) {
//            eg = eGroup;
//        }

        return egList;
    }
    
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
        ArrayList<Employee> employees = new ArrayList();
        
        
        return employees;
    }
    
    @Override
    public void persist(Object object) {
        em.persist(object);
    }

}
