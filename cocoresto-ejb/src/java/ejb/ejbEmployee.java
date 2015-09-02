
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
    public ArrayList<EmployeeGroup> findGroups() {
        ArrayList<EmployeeGroup> groups = new ArrayList();
        
        String query = "SELECT eg FROM EmployeeGroup eg";
        Query qr = em.createQuery(query);
        
        List<EmployeeGroup> egList = qr.getResultList();
        for(EmployeeGroup eGroup : egList) {
            groups.add(eGroup);
        }

        return groups;
    }
    
    @Override
    public void create(Employee emp) {
        em.persist(emp);
    }
    
    @Override
    public void delete(Employee emp) {
        em.merge(emp);
    }
    
    @Override
    public void update(Employee emp) {
        em.merge(emp);
    }
    
    @Override
    public Employee findById(Long id) {
        Employee employee = em.find(Employee.class, id);
        
        return employee;
    }
    
    @Override
    public ArrayList<Employee> findAll(){
        ArrayList<Employee> employees = new ArrayList();
        
        String query = "SELECT e FROM Employee e"
                + " WHERE e.active = :activeEmployee";
        Query qr = em.createQuery(query);
        qr.setParameter("activeEmployee", true);
        
        List<Employee> eList = qr.getResultList();
        for(Employee emp : eList) {
            employees.add(emp);
        }
        
        return employees;
    }
    
    @Override
    public int count() {
        return ((Long) em.createQuery("select COUNT(e) from Employee e").getSingleResult()).intValue();
    }
    
    @Override
    public void persist(Object object) {
        em.persist(object);
    }

}
