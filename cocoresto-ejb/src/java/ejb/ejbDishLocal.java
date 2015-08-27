package ejb;

import entities.Dish;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ejbDishLocal {

    public void create(Dish dish);

    public void update(Dish dish);
    
    public void delete(Dish dish);

    public Dish findById(Long id);

    public List<Dish> findAll();
    
    

    public int count();

}
