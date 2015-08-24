package ejb;

import entities.Category;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ejbCategory implements ejbCategoryLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    @Override
    public boolean create(Category category) {
        em.persist(category);
        return false;
    }

    @Override
    public boolean update(Category category) {
        em.merge(category);
        return false;
    }

    @Override
    public boolean delete(Category category) {
        em.remove(category);
        return false;
    }

    @Override
    public Category findById(Long id) {
        Category c = em.find(Category.class, id);

        return c;
    }

    @Override
    public Collection<Category> findAll() {
        return null;
    }

}
