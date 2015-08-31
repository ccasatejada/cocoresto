
package ejb;

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
}
