
package controllers;

import entities.Employee;
import entities.EmployeeGroup;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanEmployee;

public class EmployeeController implements IController{

    
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        
        Employee emp;
        EmployeeGroup group;
        beanEmployee bEmp = (beanEmployee) session.getAttribute("bEmp");
        if(bEmp == null) {
            bEmp = new beanEmployee();
            emp = new Employee();
            group = new EmployeeGroup();
        } else {
            emp = bEmp.getEmployee();
            group = bEmp.getEmployee().getEmployeeGroup();
        }
        
        if("edit".equals(request.getParameter("task"))) {
            
            emp.setActive(true);
            emp.setCreationDate(new Date());
            //emp.setEmployeeGroup(bEmp.findGroups(request.getParameter("comboGroupEmployee")));
            emp.setFirstName(request.getParameter("firstName"));
            emp.setLastName(request.getParameter("lastName"));
            emp.setPassword(request.getParameter("password"));
            bEmp.setEmployee(emp);
            bEmp.create(emp);
            
            return "/WEB-INF/admin/employeeEdit.jsp";
        }
        
        if("employee".equals(request.getParameter("option"))) {
            return "/WEB-INF/admin/employeeList.jsp";
        }
        
        
        return "/WEB-INF/admin/employeeEdit.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
