
package ejb;

import entities.Dish;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public void delete(Dish dish){ 
        em.remove(dish);
    }

    @Override
    public Dish findById(Long id) {
        Dish d = em.find(Dish.class, id);
        
        return d;
    }

    @Override
    public List<Dish> findAll() {
        
        return null;
    }

    public void persist(Object object) {
        em.persist(object);
    }

    
}
