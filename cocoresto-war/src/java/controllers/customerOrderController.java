package controllers;

import entities.CustomerOrder;
import entities.CustomerTable;
import entities.OrderStatus;
import helpers.Alert;
import helpers.Pagination;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanOrderCustomer;
import models.beanTableCustomer;

public class customerOrderController implements IController {

    private final beanOrderCustomer boc = new beanOrderCustomer();
    private final String editUrl = "/WEB-INF/admin/customerOrderEdit.jsp";
    private final String listUrl = "/WEB-INF/admin/customerOrderList.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        // set session values
        HttpSession session = request.getSession();
        boolean logged = false;
        Long groupId = 0L;

        if (session.getAttribute("logged") != null && session.getAttribute("group") != null) {
            logged = (boolean) session.getAttribute("logged");
            groupId = (Long) session.getAttribute("group");
        }

        if (logged && groupId >= 3) {

            if ("edit".equals(request.getParameter("task")) && request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
                try {
                    CustomerOrder co = boc.findById(Long.valueOf(request.getParameter("id")));
                    request.setAttribute("customerOrder", co);
                    request.setAttribute("statusList", boc.findAllOrderStatus());
                    return editUrl;
                } catch (NumberFormatException | EJBException e) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Cette commande n'existe pas", "danger"));
                }
            }

            if ("add".equals(request.getParameter("task")) && request.getParameter("id") == null) {
                return editUrl;
            }

            if ("delete".equals(request.getParameter("task")) && request.getParameter("id") != null) {
                try {
                    CustomerOrder co = boc.findById(Long.valueOf(request.getParameter("id")));
                    boc.delete(co);
                    request.setAttribute("alert", Alert.setAlert("Succès", "La commande été supprimée", "success"));
                } catch (NumberFormatException | EJBException e) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Cette commande n'existe pas", "danger"));
                }
            }

            // form has been send
            if (request.getParameter("confirm") != null) {
                boolean ok = edit(request);
                if (!ok) {
                    return editUrl;
                }
            }

            getList(request);

            return listUrl;
        } else {
            try {
                // not logged or wrong groupId
                response.sendRedirect("FrontController?option=dashboard");
            } catch (IOException ex) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));
            }
        }

        return "/WEB-INF/index.jsp";
    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    private void getList(HttpServletRequest request) {

        /* pagination */
        int max = 10;
        int currentPage = 1;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.valueOf(request.getParameter("page"));
            } catch (NumberFormatException e) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "La page n'est pas un nombre", "danger"));
            }
        }
        Pagination pagination = new Pagination("customerOrder", currentPage, max, boc.count());
        request.setAttribute("pagination", pagination.getPagination());

        List<CustomerOrder> customerOrders = boc.findAllByRange(pagination.getMin(), max);
        request.setAttribute("customerOrders", customerOrders);
    }
    
    private boolean edit(HttpServletRequest request) {

        
        return true;

    }
}
