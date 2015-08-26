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
        CustomerTable ct = em.find(CustomerTable.class, customerTable.getId());
        em.remove(ct);
    }

    @Override
    public CustomerTable findById(Long id) {
        CustomerTable ct = em.find(CustomerTable.class, id);
        return ct;
    }

    @Override
    public List<CustomerTable> findAll() {
        Query q = em.createQuery("select ct from CustomerTable ct");       
        return q.getResultList();
    }

}
