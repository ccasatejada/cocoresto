package ejb;

import entities.Category;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ejbCategory implements ejbCategoryLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;
    
    @Override
    public void create(Category category) {
        em.persist(category);
    }

    @Override
    public void update(Category category) {
        em.merge(category);
    }

    @Override
    public void delete(Category category) {
        Category c = em.find(Category.class, category.getId());
        em.remove(c);
    }

    @Override
    public Category findById(Long id) {
        Category c = em.find(Category.class, id);
        return c;
    }

    @Override
    public List<Category> findAll() {
        String sq = "select c from Category c";
        Query q = em.createQuery(sq);       
        return q.getResultList();
    }

}
