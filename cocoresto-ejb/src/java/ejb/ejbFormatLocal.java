
package ejb;

import entities.Format;
import java.util.ArrayList;
import javax.ejb.Local;

@Local
public interface ejbFormatLocal {
    
    public void create(Format format);

    public void delete(Format format);

    public void update(Format format);

    public Format findById(Long id);

    public void persist(Object object);

    public ArrayList<Format> findAll();
    
    
}
