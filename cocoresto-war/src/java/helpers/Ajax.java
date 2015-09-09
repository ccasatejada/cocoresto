package helpers;

import ejb.ejbRestaurantLocal;
import entities.Category;
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
import models.beanCategory;
import models.beanDish;
import models.beanTableCustomer;

@WebServlet(name = "Ajax", urlPatterns = {"/Ajax"})
public class Ajax extends HttpServlet {

    @EJB
    private ejbRestaurantLocal ejbRestaurant;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if ("menu".equals(request.getParameter("task"))) {
                
                beanCategory bc = new beanCategory();
                String type = "Plat";

                if ("dishes".equals(request.getParameter("list"))) {
                    type = "Plat";
                }

                if ("drinks".equals(request.getParameter("list"))) {
                    type = "Boisson";
                }

                if ("combos".equals(request.getParameter("list"))) {
                    type = "Menu";
                }

                List<Category> categories = bc.findAvailableCategories(type);

                out.println("<div id=\"listMenu\" class=\"panel-group\" role=\"tablist\" aria-multiselectable=\"true\">");
                for (Category cat : categories) {
                    out.println("<div class=\"panel panel-default\">\n"
                            + "<div class=\"panel-heading\" role=\"tab\" id=\"heading" + cat.getId() + "\">\n"
                            + "<h4 class=\"panel-title\">\n"
                            + "<a role=\"button\" data-toggle=\"collapse\" data-parent=\"#listMenu\" href=\"#collapse" + cat.getId() + "\" aria-expanded=\"false\" aria-controls=\"collapse" + cat.getId() + "\">" + cat.getName() + "</a>\n"
                            + "</h4>\n"
                            + "</div>\n"
                            + "<div id=\"collapse" + cat.getId() + "\" class=\"panel-collapse collapse\" role=\"tabpanel\" aria-labelledby=\"heading" + cat.getId() + "\">\n"
                            + "<div class=\"panel-body\">");

                    out.println("</div>\n</div>\n</div>");
                }
                out.println("</div>");

            }

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
