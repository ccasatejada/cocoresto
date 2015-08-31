package ejb;

import entities.Combo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class ejbCombo implements ejbComboLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    @Override
    public void create(Combo combo) {
        em.persist(combo);
    }

    @Override
    public void update(Combo combo) {
        em.persist(combo);
    }

    @Override
    public void delete(Combo combo) {
        Combo c = em.find(Combo.class, combo.getId());
        em.remove(c);
    }

    @Override
    public Combo findById(Long id) {
        Combo c = em.find(Combo.class, id);
        return c;
    }

    @Override
    public List<Combo> findAll() {
        String sq = "select c from Combo c";
        Query q = em.createQuery(sq);
        return q.getResultList();
    }

    @Override
    public int count() {
        return ((Long) em.createQuery("select COUNT(c) from Combo c").getSingleResult()).intValue();
    }

}
