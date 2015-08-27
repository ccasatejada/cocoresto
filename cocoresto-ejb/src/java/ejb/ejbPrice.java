package ejb;

import entities.Price;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class ejbPrice implements ejbPriceLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    @Override
    public void create(Price price) {
        em.persist(price);
    }

    @Override
    public void update(Price price) {
        em.merge(price);
    }

    @Override
    public void delete(Price price) {
        Price p = em.find(Price.class, price.getId());
        em.remove(p);
    }

    @Override
    public Price findById(Long id) {
        Price p = em.find(Price.class, id);
        return p;
    }
    
    @Override
    public Price findLastInserted() {
        String query = "SELECT p FROM Price p";
        Query qr = em.createQuery(query);
        Price lastPrice = (Price)qr.getParameterValue(qr.getResultList().size());
        
        return lastPrice;
    }

    @Override
    public List<Price> findAll() {
        String sq = "select p from Price p";
        Query q = em.createQuery(sq);
        return q.getResultList();
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
