package ejb;

import entities.CustomerTable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ejbCustomerTable implements ejbCustomerTableLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    @Override
    public void create(CustomerTable customerTable) {
        em.persist(customerTable);
    }

    @Override
    public void update(CustomerTable customerTable) {
        em.merge(customerTable);
    }

    @Override
    public void delete(CustomerTable customerTable) {
        // CustomerTable ct = em.find(CustomerTable.class, customerTable.getId());
        //em.remove(ct);
        customerTable.setActive(false);
        em.merge(customerTable);
    }

    @Override
    public CustomerTable findById(Long id) {
        CustomerTable ct = em.find(CustomerTable.class, id);
        return ct;
    }

    @Override
    public List<CustomerTable> findAll() {
        Query q = em.createQuery("select ct from CustomerTable ct where ct.active = 1 order by ct.number");
        return q.getResultList();
    }

    @Override
    public List<CustomerTable> findAvailable(int nb) {
        Query q = em.createQuery("SELECT ct FROM CustomerTable ct WHERE ct.active = 1 AND ct.busy = 0 AND ct.capacity >= :nb ORDER BY ct.number");
        q.setParameter("nb", nb);
        return q.getResultList();
    }

    @Override
    public List<CustomerTable> findAllByRange(int firstResult, int maxResults) {
        Query q = em.createQuery("select ct from CustomerTable ct where ct.active = 1 order by ct.number");
        if (firstResult >= 0) {
            q.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            q.setMaxResults(maxResults);
        }
        return q.getResultList();
    }

    @Override
    public int count() {
        return ((Long) em.createQuery("select COUNT(ct) from CustomerTable ct where ct.active = 1").getSingleResult()).intValue();
    }

    @Override
    public int countMaxCapacity() {
        return ((Integer) em.createQuery("SELECT MAX(ct.capacity) FROM CustomerTable ct WHERE ct.active = 1").getSingleResult()).intValue();
    }

}
