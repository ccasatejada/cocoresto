
package ejb;

import entities.Category;
import entities.Combo;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ejbComboLocal {
    public void create(Combo combo);
    public void update(Combo combo);
    public void delete(Combo combo);
    
    public Combo findById(Long id);
    public List<Combo> findAll();
    public int count();
    public List<Category> findCategories(); 
    
    public List<Combo> findAllByRange(int firstResult, int maxResults);

    public List<Combo> findAllByCategory(String type);
    
}
