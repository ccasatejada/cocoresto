package helpers;

import ejb.ejbRestaurantLocal;
import entities.CustomerTable;
import entities.Employee;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanTableCustomer;

@WebServlet(name = "Ajax", urlPatterns = {"/Ajax"})
public class Ajax extends HttpServlet {
    @EJB
    private ejbRestaurantLocal ejbRestaurant;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if ("customerTable".equals(request.getParameter("task"))) {
                beanTableCustomer btc = new beanTableCustomer();

                if ("available".equals(request.getParameter("get"))) {
                    int nb = Integer.valueOf(request.getParameter("nb"));
                    List<CustomerTable> availableTables = btc.findAvailable(nb);
                    out.println("<option value=\"0\">Sélectionnez une table disponible</option>");
                    for (CustomerTable table : availableTables) {
                        out.println("<option value=\"" + table.getId() + "\">Table n°" + table.getNumber() + " (" + table.getCapacity() + " places)</option>");
                    }
                }

                if ("nbTablet".equals(request.getParameter("get"))) {
                    Long id = Long.valueOf(request.getParameter("table"));
                    CustomerTable ct = btc.findById(id);
                    out.print(ct.getNbTablet());
                }

            }

            if ("removeEmployee".equals(request.getParameter("task"))) {
                HttpSession session = request.getSession();
                Employee employee = (Employee) session.getAttribute("loggedEmployee");
                ejbRestaurant.removeEmployee(employee);
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
