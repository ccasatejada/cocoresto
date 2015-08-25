
package ejb;

import entities.NutritiveValue;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ejbNutritiveValueLocal {
    public void create(NutritiveValue nv);
    public void update(NutritiveValue nv);
    public void delete(NutritiveValue nv);
    public NutritiveValue findById(Long id);
    public List<NutritiveValue> findAll();
}
