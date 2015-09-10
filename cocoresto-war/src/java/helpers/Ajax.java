package helpers;

import ejb.ejbRestaurantLocal;
import entities.Category;
import entities.Combo;
import entities.CustomerTable;
import entities.Dish;
import entities.Drink;
import entities.Employee;
import entities.Price;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanCategory;
import models.beanCombo;
import models.beanDish;
import models.beanDrink;
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
                beanDish bdish = new beanDish();
                beanDrink bdrink = new beanDrink();
                beanCombo bcombo = new beanCombo();

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
                            + "<h1 class=\"panel-title\">\n"
                            + "<a role=\"button\" data-toggle=\"collapse\" data-parent=\"#listMenu\" href=\"#collapse" + cat.getId() + "\" aria-expanded=\"false\" aria-controls=\"collapse" + cat.getId() + "\">" + cat.getName() + "</a>\n"
                            + "</h1>\n"
                            + "</div>\n"
                            + "<div id=\"collapse" + cat.getId() + "\" class=\"panel-collapse collapse\" role=\"tabpanel\" aria-labelledby=\"heading" + cat.getId() + "\">\n"
                            + "<div class=\"panel-body\">\n"
                            + "<div class=\"row\">\n");

                    switch (type) {
                        case "Boisson":
                            List<Drink> drinks = bdrink.findAllByCategory(cat.getId());
                            for (Drink drink : drinks) {
                                ArrayList<Double> prices = drink.getTotalPrices();
                                out.println("<div class=\"col-sm-6 col-md-4\"><div class=\"thumbnail\">");
                                out.println("<img src=\"images/products/" + drink.getImage() + "\" alt=\"" + drink.getName() + "\">");
                                out.println("<div class=\"caption\">"
                                        + "<h3>" + drink.getName() + "</h3>");
                                for (Double price : prices) {
                                    out.println("<h4>" + price + " €</h4>");
                                }
                                out.println("<p><a data-task=\"add\" href=\"#\" class=\"btn btn-primary\" role=\"button\">Ajouter</a> "
                                        + "<a data-task=\"show\" data-toggle=\"modal\" data-target=\"#detailModal\" data-link=\"FrontController?option=menu&task=detail&id=" + drink.getId() + "&type=" + type + "&layout=component\" class=\"btn btn-default\" role=\"button\">Détail</a></p>"
                                        + "</div>");
                                out.println("</div></div>");
                            }
                            break;
                        case "Menu":
                            List<Combo> combos = bcombo.findAllByCategory(cat.getId());
                            for (Combo combo : combos) {
                                out.println("<div class=\"col-sm-6 col-md-4\"><div class=\"thumbnail\">");
                                out.println("<div class=\"caption\">"
                                        + "<h3>" + combo.getName() + "</h3>"
                                        + "<h4>" + combo.getTotalPrice() + " €</h4>"
                                        + "<p><a data-task=\"add\" href=\"#\" class=\"btn btn-primary\" role=\"button\">Ajouter</a> "
                                        + "<a data-task=\"show\" data-toggle=\"modal\" data-target=\"#detailModal\" data-link=\"FrontController?option=menu&task=detail&id=" + combo.getId() + "&type=" + type + "&layout=component\" class=\"btn btn-default\" role=\"button\">Détail</a></p>"
                                        + "</div>");
                                out.println("</div></div>");
                            }
                            break;
                        default:
                            List<Dish> dishes = bdish.findAllByCategory(cat.getId());
                            for (Dish dish : dishes) {
                                out.println("<div class=\"col-sm-6 col-md-4\"><div class=\"thumbnail\">");
                                out.println("<img src=\"images/products/" + dish.getImage() + "\" alt=\"" + dish.getName() + "\">");
                                out.println("<div class=\"caption\">"
                                        + "<h3>" + dish.getName() + "</h3>"
                                        + "<h4>" + dish.getTotalPrice() + " €</h4>"
                                        + "<p><a data-task=\"add\" href=\"#\" class=\"btn btn-primary\" role=\"button\">Ajouter</a> "
                                        + "<a data-task=\"show\" data-toggle=\"modal\" data-target=\"#detailModal\" data-link=\"FrontController?option=menu&task=detail&id=" + dish.getId() + "&type=" + type + "&layout=component\" class=\"btn btn-default\" role=\"button\">Détail</a></p>"
                                        + "</div>");
                                out.println("</div></div>");
                            }
                    }

                    out.println("</div>\n</div>\n</div>\n</div>");
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
