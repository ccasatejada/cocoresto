package ejb;

import javax.ejb.Local;

@Local
public interface ejbLoginLocal {

    public Boolean login(String password);
    
}
