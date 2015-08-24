package ejb;

import entities.Employee;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ejbLogin implements ejbLoginLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    public Employee login(String password) {

        String q = "select e from Employee e where e.password = :password and e.active = true";
        Query query = em.createQuery(q);
        query.setParameter("password", password);
        
        Employee employee = null;
        
        try{
            employee = (Employee) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }

        return employee;
    }

}
