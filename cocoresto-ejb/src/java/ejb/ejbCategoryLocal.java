
package ejb;

import entities.Category;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ejbCategoryLocal {
    
    public void create(Category category);

    public void update(Category category);
    
    public void delete(Category category);

    public Category findById(Long id);

    public List<Category> findAll();
    
    public List<Category> findAvailableCategories(String type);
    
    public int count();
    
    public List<Category> findAllByRange(int firstResult, int maxResults);

}
