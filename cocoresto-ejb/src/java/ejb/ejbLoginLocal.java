package ejb;

import entities.Employee;
import javax.ejb.Local;

@Local
public interface ejbLoginLocal {

    public Employee login(String password);
    
}
