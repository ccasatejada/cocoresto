package ejb;

import entities.Dish;
import java.util.Collection;
import javax.ejb.Local;

@Local
public interface ejbDishLocal {

    public boolean create(Dish dish);

    public boolean update(Dish dish);
    
    public boolean delete(Dish dish);

    public Dish findById(Long id);

    public Collection<Dish> findAll();

}
