package helpers;

import entities.CustomerTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.beanTableCustomer;

@WebServlet(name = "Ajax", urlPatterns = {"/Ajax"})
public class Ajax extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if ("customerTable".equals(request.getParameter("task"))) {

                if ("available".equals(request.getParameter("get"))) {

                    int nb = Integer.valueOf(request.getParameter("nb"));
                    beanTableCustomer btc = new beanTableCustomer();
                    List<CustomerTable> availableTables = btc.findAvailable(nb);
                    
                    out.println("<option disabled>Sélectionnez une table disponible</option>");
                    for (CustomerTable table : availableTables) {
                        out.println("<option value=\"" + table.getId() + "\">Table n°" + table.getNumber() + "</option>");
                    }

                }

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
