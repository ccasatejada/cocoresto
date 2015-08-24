package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ejbLogin implements ejbLoginLocal {
    
    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;
    
    public Boolean login(String password) {
        
        
        return false;
    }

}
