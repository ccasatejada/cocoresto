package ejb;

import entities.Category;
import entities.Dish;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ejbDish implements ejbDishLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    @Override
    public void create(Dish dish) {
        em.persist(dish);

    }

    @Override
    public void update(Dish dish) {
        em.merge(dish);

    }

    @Override
    public void delete(Dish dish) {
        Dish d = em.find(Dish.class, dish.getId());
        em.remove(d);
    }

    @Override
    public Dish findById(Long id) {
        Dish d = em.find(Dish.class, id);

        if (d.getDiscount() != null && (new Date()).after(d.getDiscount().getEndDate())) {
            d.setDiscount(null);
            em.merge(d);
        }
        return d;
    }

    @Override
    public List<Dish> findAll() {
        String sq = "select d from Dish d";
        Query q = em.createQuery(sq);
        return q.getResultList();
    }

    @Override
    public List<Dish> findAllByCategory(Long id) {
        Query q = em.createQuery("SELECT d FROM Dish d WHERE d.active = 1 AND d.category.id = " + id +" AND d.inventory > 0");
        return q.getResultList();
    }

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public int count() {
        return ((Long) em.createQuery("select COUNT(d) from Dish d").getSingleResult()).intValue();
    }

    @Override
    public List<Category> findCategories() {
        List<Category> categories = new ArrayList();
        String sq = "SELECT c FROM Category c where c.active = 1";
        Query q = em.createQuery(sq);
        for (Category cat : (List<Category>) q.getResultList()) {
            if ("Plat".equals(cat.getType())) {
                categories.add(cat);
            }
        }
        return categories;
    }

    @Override
    public List<Dish> findAllByRange(int firstResult, int maxResults) {
        Query q = em.createQuery("select d from Dish d order by d.category");
        if (firstResult >= 0) {
            q.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            q.setMaxResults(maxResults);
        }
        return q.getResultList();
    }

}
