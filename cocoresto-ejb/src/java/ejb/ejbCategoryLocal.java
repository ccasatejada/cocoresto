
package ejb;

import entities.Category;
import java.util.Collection;
import javax.ejb.Local;

@Local
public interface ejbCategoryLocal {
    
    public boolean create(Category category);

    public boolean update(Category category);
    
    public boolean delete(Category category);

    public Category findById(Long id);

    public Collection<Category> findAll();

}
