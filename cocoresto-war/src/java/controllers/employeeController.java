
package controllers;

import entities.Employee;
import entities.EmployeeGroup;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanEmployee;

public class employeeController implements IController{

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
            emp.setEmployeeGroup(group);
            bEmp.setEmployee(emp);
            session.setAttribute("bEmp", bEmp);
        } else {
            emp = bEmp.getEmployee();
            group = bEmp.getEmployee().getEmployeeGroup();
        }
        
        ArrayList<EmployeeGroup> employeeGroups = bEmp.findGroups();
        
        if("edit".equals(request.getParameter("task"))) {
            session.setAttribute("employeeGroups", employeeGroups);
            session.removeAttribute("employee");
            return "/WEB-INF/admin/employeeEdit.jsp";
        }
        
        if("modify".equals(request.getParameter("task"))) {
            emp = bEmp.findById(Long.valueOf(request.getParameter("id")));
            session.setAttribute("employeeGroups", employeeGroups);
            session.setAttribute("employee", emp);
            return "/WEB-INF/admin/employeeEdit.jsp";
        }
        
        if("delete".equals(request.getParameter("task"))) {
            emp = bEmp.findById(Long.valueOf(request.getParameter("id")));
            emp.setActive(false);
            bEmp.delete(emp);
        }
        
        if(request.getParameter("createIt") != null) {
            emp.setActive(true);
            emp.setCreationDate(new Date());
            employeeGroups = (ArrayList<EmployeeGroup>)session.getAttribute("employeeGroups");
            for(EmployeeGroup eg : employeeGroups) {
                if(eg.getName().equals(request.getParameter("comboGroupEmployee"))) {
                    System.out.println(eg.getId());
                    group.setId(eg.getId());
                    group.setName(request.getParameter("comboGroupEmployee"));
                    break;
                }
            }
            emp.setEmployeeGroup(group);
            emp.setFirstName(request.getParameter("firstName"));
            emp.setLastName(request.getParameter("lastName"));
            emp.setPassword(request.getParameter("password"));
            bEmp.create(emp);
            session.setAttribute("employee", emp);

        }
        
        if(request.getParameter("modifyIt") != null) {
            emp = (Employee)session.getAttribute("employee");
            for(EmployeeGroup eg : employeeGroups) {
                if(eg.getName().equals(request.getParameter("comboGroupEmployee"))) {
                    group.setId(eg.getId());
                    group.setName(request.getParameter("comboGroupEmployee"));
                    break;
                }
            }
            emp.setEmployeeGroup(group);
            emp.setFirstName(request.getParameter("firstName"));
            emp.setLastName(request.getParameter("lastName"));
            emp.setPassword(request.getParameter("password"));
            bEmp.update(emp);
        }
        
        if("employee".equals(request.getParameter("option"))) {
            ArrayList<Employee> employees = bEmp.findAll();
            session.setAttribute("employees", employees);
            return "/WEB-INF/admin/employeeList.jsp";
        }
        
        return "/WEB-INF/admin/employeeEdit.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
