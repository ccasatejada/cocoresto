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
        List<Price> pList = qr.getResultList();
        Price lastPrice = pList.get(pList.size()-1);

        return lastPrice;
    }

    @Override
    public List<Price> findAll() {
        String sq = "select p from Price p";
        Query q = em.createQuery(sq);
        return q.getResultList();
    }

    @Override
    public Price findByValue(Double price) {
        String sq = "select p from Price p where p.price = :price";
        Query q = em.createQuery(sq);
        q.setParameter("price", price);
        Price p = (Price)q.getSingleResult();
        
        return p;
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
