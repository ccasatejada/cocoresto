package ejb;

import entities.Category;
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

    }

    @Override
    public void update(CustomerTable customerTable) {

    }

    @Override
    public void delete(CustomerTable customerTable) {

    }

    @Override
    public CustomerTable findById(Long id) {
        return null;
    }

    @Override
    public List<CustomerTable> findAll() {
        Query q = em.createQuery("select ct from CustomerTable ct");       
        return q.getResultList();
    }

}
