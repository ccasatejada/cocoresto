
package models;

import ejb.ejbEmployeeLocal;
import entities.Employee;
import entities.EmployeeGroup;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class beanEmployee implements Serializable {
    
    Employee employee;
    ejbEmployeeLocal ejbEmployee = lookupejbEmployeeLocal();

    public beanEmployee() {
        employee = new Employee();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ArrayList<EmployeeGroup> findGroups() {
        return ejbEmployee.findGroups();
    }
    
    public int count() {
        if(ejbEmployee.count() != 0) {
        return ejbEmployee.count();
        }
        return 0;
    }
    
    public void create(Employee emp) throws EJBException {
        ejbEmployee.create(emp);
    }
    
    public void delete(Employee emp) throws EJBException {
        ejbEmployee.delete(emp);
    }
    
    public void update(Employee emp) throws EJBException {
        ejbEmployee.update(emp);
    }
    
    public Employee findById(Long id) throws EJBException {
        return ejbEmployee.findById(id);
    }
    
    public ArrayList<Employee> findAll() throws EJBException {    
        return ejbEmployee.findAll();
    }
    
    public List<Employee> findAllByRange(int firstResult, int maxResults) {
        return ejbEmployee.findAllByRange(firstResult, maxResults);
    }
    
    
    
    private ejbEmployeeLocal lookupejbEmployeeLocal() {
        try {
            Context c = new InitialContext();
            return (ejbEmployeeLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbEmployee!ejb.ejbEmployeeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
    
}
