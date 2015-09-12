package helpers;

import ejb.ejbRestaurantLocal;
import entities.Category;
import entities.Combo;
import entities.CustomerTable;
import entities.Dish;
import entities.Drink;
import entities.Employee;
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

        HttpSession session = request.getSession();

        try (PrintWriter out = response.getWriter()) {

            if ("cart".equals(request.getParameter("task"))) {

                if (session.getAttribute("cartDishes") == null) {
                    session.setAttribute("cartDishes", new ArrayList());
                }
                if (session.getAttribute("cartDrinks") == null) {
                    session.setAttribute("cartDrinks", new ArrayList());
                }
                if (session.getAttribute("cartCombos") == null) {
                    session.setAttribute("cartCombos", new ArrayList());
                }

                beanDish bdish = new beanDish();
                beanDrink bdrink = new beanDrink();
                beanCombo bcombo = new beanCombo();

                Long id = Long.valueOf(request.getParameter("id"));
                String type = request.getParameter("type");

                switch (type) {
                    case "Boisson":
                        Drink dr = bdrink.findById(id);
                        List<Drink> cartDrinks = (List<Drink>) session.getAttribute("cartDrinks");
                        if (null != request.getParameter("action")) {
                            switch (request.getParameter("action")) {
                                case "remove":
                                    cartDrinks.remove(dr);
                                    break;
                                case "add":
                                    cartDrinks.add(dr);
                                    break;
                            }
                        }
                        session.setAttribute("cartDrinks", cartDrinks);
                        break;
                    case "Menu":
                        Combo c = bcombo.findById(id);
                        List<Combo> cartCombos = (List<Combo>) session.getAttribute("cartCombos");
                        if (null != request.getParameter("action")) {
                            switch (request.getParameter("action")) {
                                case "remove":
                                    cartCombos.remove(c);
                                    break;
                                case "add":
                                    cartCombos.add(c);
                                    break;
                            }
                        }
                        session.setAttribute("cartCombos", cartCombos);
                        break;
                    default:
                        Dish d = bdish.findById(id);
                        List<Dish> cartDishes = (List<Dish>) session.getAttribute("cartDishes");
                        if (null != request.getParameter("action")) {
                            switch (request.getParameter("action")) {
                                case "remove":
                                    cartDishes.remove(d);
                                    break;
                                case "add":
                                    cartDishes.add(d);
                                    break;
                            }
                        }
                        session.setAttribute("cartDishes", cartDishes);
                }

                List<Dish> cartDishes = (List<Dish>) session.getAttribute("cartDishes");
                List<Drink> cartDrinks = (List<Drink>) session.getAttribute("cartDrinks");
                List<Combo> cartCombos = (List<Combo>) session.getAttribute("cartCombos");
                Double total = 0.00;

                if (cartDishes.size() > 0) {
                    out.println("<tr><th class=\"bg-info\" colspan=\"3\">Plats</th></tr>");
                    for (Dish d : cartDishes) {
                        total += d.getTotalPrice();
                        out.println("<tr>\n"
                                + "<td>" + d.getName() + "</td>\n"
                                + "<td>" + d.getTotalPrice() + "</td>\n"
                                + "<td>\n"
                                + "<a data-task=\"remove\" data-id=\"" + d.getId() + "\" data-price=\"" + d.getTotalPrice() + "\" data-type=\"" + type + "\" href=\"#\" class=\"btn btn-lightred btn-rounded btn-ef\" name=\"deleteIt\"><i class=\"fa fa-minus-circle\"></i></a>\n"
                                + "</td>\n"
                                + "</tr>");
                    }
                }

                if (cartDrinks.size() > 0) {
                    out.println("<tr><th class=\"bg-info\" colspan=\"3\">Boissons</th></tr>");
                    for (Drink dr : cartDrinks) {
                        total += dr.getTotalPrice();
                        out.println("<tr>\n"
                                + "<td>" + dr.getName() + "</td>\n"
                                + "<td>" + dr.getTotalPrice() + "</td>\n"
                                + "<td>\n"
                                + "<a data-task=\"remove\" data-id=\"" + dr.getId() + "\" data-price=\"" + dr.getTotalPrice() + "\" data-type=\"" + type + "\" href=\"#\" class=\"btn btn-lightred btn-rounded btn-ef\" name=\"deleteIt\"><i class=\"fa fa-minus-circle\"></i></a>\n"
                                + "</td>\n"
                                + "</tr>");
                    }
                }

                if (cartCombos.size() > 0) {
                    out.println("<tr><th class=\"bg-info\" colspan=\"3\">Menus</th></tr>");
                    for (Combo c : cartCombos) {
                        total += c.getTotalPrice();
                        out.println("<tr>\n"
                                + "<td>" + c.getName() + "</td>\n"
                                + "<td>" + c.getTotalPrice() + "</td>\n"
                                + "<td>\n"
                                + "<a data-task=\"remove\" data-id=\"" + c.getId() + "\" data-price=\"" + c.getTotalPrice() + "\" data-type=\"" + type + "\" href=\"#\" class=\"btn btn-lightred btn-rounded btn-ef\" name=\"deleteIt\"><i class=\"fa fa-minus-circle\"></i></a>\n"
                                + "</td>\n"
                                + "</tr>");
                    }
                }

            }

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
                                Double price = drink.getTotalPrice();
                                out.println("<div class=\"col-sm-6 col-md-4\"><div class=\"thumbnail\">");
                                out.println("<img src=\"images/products/" + drink.getImage() + "\" alt=\"" + drink.getName() + "\">");
                                out.println("<div class=\"caption\">"
                                        + "<h3>" + drink.getName() + " <small>" + drink.getFormat() + "</small></h3>");
                                out.println("<h4>" + price + " €</h4>");
                                out.println("<p><a data-task=\"add\" data-id=\"" + drink.getId() + "\" data-price=\"" + drink.getTotalPrice() + "\" data-type=\"" + type + "\" href=\"#\" class=\"btn btn-primary\" role=\"button\">Ajouter</a> "
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
                                        + "<p><a data-task=\"add\" data-id=\"" + combo.getId() + "\" data-price=\"" + combo.getTotalPrice() + "\" data-type=\"" + type + "\" href=\"#\" class=\"btn btn-primary\" role=\"button\">Ajouter</a> "
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
                                        + "<p><a data-task=\"add\" data-id=\"" + dish.getId() + "\" data-price=\"" + dish.getTotalPrice() + "\" data-type=\"" + type + "\" href=\"#\" class=\"btn btn-primary\" role=\"button\">Ajouter</a> "
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
