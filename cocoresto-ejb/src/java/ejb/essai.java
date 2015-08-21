
package ejb;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class essai implements essaiLocal {
    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

}
