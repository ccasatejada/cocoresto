
package ejb;

import entities.Dish;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ejbDish implements ejbDishLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;
    
    @Override
    public boolean create(Dish dish) {
        em.persist(dish);
        
        return false;
    }

    @Override
    public boolean update(Dish dish) {
        em.merge(dish);
        
        return false;
    }
    
    @Override
    public boolean delete(Dish dish){ 
        em.remove(dish);
        return false;
    }

    @Override
    public Dish findById(Long id) {
        Dish d = em.find(Dish.class, id);
        
        return d;
    }

    @Override
    public Collection<Dish> findAll() {
        
        return null;
    }

    public void persist(Object object) {
        em.persist(object);
    }

    
}
