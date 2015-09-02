package controllers;

import entities.CustomerOrder;
import entities.Employee;
import entities.EmployeeGroup;
import helpers.Alert;
import helpers.Pagination;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanEmployee;

public class employeeController implements IController {

    private beanEmployee bEmp = new beanEmployee();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        Employee emp;
        EmployeeGroup group;

        boolean logged = false;
        Long groupId = 0L;

        bEmp = (beanEmployee) session.getAttribute("bEmp");
        if (bEmp == null) {
            bEmp = new beanEmployee();
            emp = new Employee();
            group = new EmployeeGroup();
            session.setAttribute("bEmp", bEmp);
        } else {
            emp = bEmp.getEmployee();
            group = bEmp.getEmployee().getEmployeeGroup();
        }

        ArrayList<EmployeeGroup> employeeGroups = bEmp.findGroups();

        if (session.getAttribute("logged") != null && session.getAttribute("group") != null) {
            logged = (boolean) session.getAttribute("logged");
            groupId = (Long) session.getAttribute("group");
        }

        if (logged && groupId >= 3) {

            if ("edit".equals(request.getParameter("task"))) {
                session.setAttribute("employeeGroups", employeeGroups);
                session.removeAttribute("employee");
                return "/WEB-INF/admin/employeeEdit.jsp";
            }

            if ("modify".equals(request.getParameter("task"))) {
                emp = bEmp.findById(Long.valueOf(request.getParameter("id")));
                session.setAttribute("employeeGroups", employeeGroups);
                session.setAttribute("employee", emp);
                return "/WEB-INF/admin/employeeEdit.jsp";
            }

            if ("delete".equals(request.getParameter("task"))) {
                emp = bEmp.findById(Long.valueOf(request.getParameter("id")));
                emp.setActive(false);
                bEmp.delete(emp);
            }

            if (request.getParameter("createIt") != null) {
                emp.setActive(true);
                emp.setCreationDate(new Date());
                employeeGroups = (ArrayList) session.getAttribute("employeeGroups");
                for (EmployeeGroup eg : employeeGroups) {
                    if (eg.getName().equals(request.getParameter("comboGroupEmployee"))) {
                        group = eg;
                        break;
                    }
                }
                emp.setEmployeeGroup(group);
                emp.setFirstName(request.getParameter("firstName"));
                emp.setLastName(request.getParameter("lastName"));
                emp.setPassword(request.getParameter("password"));
                bEmp.create(emp);
            }

            if (request.getParameter("modifyIt") != null) {
                emp = (Employee) session.getAttribute("employee");
                for (EmployeeGroup eg : employeeGroups) {
                    if (eg.getName().equals(request.getParameter("comboGroupEmployee"))) {
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

            if ("employee".equals(request.getParameter("option"))) {
                getList(request, "option=employee");
                return "/WEB-INF/admin/employeeList.jsp";
            }
        } else {
            try {
                // not logged or wrong groupId
                response.sendRedirect("FrontController?option=dashboard");
            } catch (IOException ex) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));
            }
        }

        return "/WEB-INF/admin/employeeEdit.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void getList(HttpServletRequest request, String queryString) {

        /* pagination */
        Pagination pagination = new Pagination(queryString, request.getParameter("page"), 10, bEmp.count());
        request.setAttribute("pagination", pagination.getPagination());

        List<Employee> employees = bEmp.findAllByRange(pagination.getMin(), 10);
        request.setAttribute("employees", employees);
    }

}
