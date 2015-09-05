package ejb;

import entities.Dish;
import entities.NutritiveValue;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ejbNutritiveValue implements ejbNutritiveValueLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    @Override
    public void create(NutritiveValue nv) {
        em.persist(nv);
    }

    @Override
    public void update(NutritiveValue nv) {
        em.merge(nv);
    }

    @Override
    public void delete(NutritiveValue nv) {
        NutritiveValue n = em.find(NutritiveValue.class, nv.getId());
        em.remove(n);
    }

    @Override
    public NutritiveValue findById(Long id) {
        NutritiveValue nv = em.find(NutritiveValue.class, id);
        return nv;
    }

    @Override
    public List<NutritiveValue> findAll() {
        String sq = "select nv from NutritiveValue nv";
        Query q = em.createQuery(sq);
        return q.getResultList();
    }

    @Override
    public List<NutritiveValue> findByDish(Dish dish) {
        Dish d = em.find(Dish.class, dish.getId());

        String sq = "select nv from NutritiveValue nv where nv.dish = :dish";
        Query q = em.createQuery(sq);
        q.setParameter("dish", d);

        return q.getResultList();
    }

}
