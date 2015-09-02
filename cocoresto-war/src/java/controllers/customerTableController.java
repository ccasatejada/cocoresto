package controllers;

import entities.CustomerTable;
import helpers.Alert;
import helpers.Pagination;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.beanTableCustomer;

public class customerTableController implements IController {

    private final beanTableCustomer btc = new beanTableCustomer();
    private final String editUrl = "/WEB-INF/admin/customerTableEdit.jsp";
    private final String listUrl = "/WEB-INF/admin/customerTableList.jsp";

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

        if ("simpleList".equals(request.getParameter("task"))) {
            
            getList(request, "option=customerTable&task=" + request.getParameter("task") + "&layout=component");
            return "admin/customerTableSimpleList.jsp";
        }

        if (logged && groupId >= 3) {

            if ("edit".equals(request.getParameter("task")) && request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
                try {
                    CustomerTable ct = btc.findById(Long.valueOf(request.getParameter("id")));
                    request.setAttribute("customerTable", ct);
                    return editUrl;
                } catch (NumberFormatException | EJBException e) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Cette table n'existe pas", "danger"));
                }
            }

            if ("add".equals(request.getParameter("task")) && request.getParameter("id") == null) {
                return editUrl;
            }

            if ("delete".equals(request.getParameter("task")) && request.getParameter("id") != null) {
                try {
                    CustomerTable ct = btc.findById(Long.valueOf(request.getParameter("id")));
                    btc.delete(ct);
                    request.setAttribute("alert", Alert.setAlert("Succès", "La table été supprimée", "success"));
                } catch (NumberFormatException | EJBException e) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Cette table n'existe pas", "danger"));
                }
            }

            // edit form has been send
            if (request.getParameter("confirm") != null) {
                boolean ok = edit(request);
                if (!ok) {
                    return editUrl;
                }
            }

            getList(request, "option=customerTable");

            return listUrl;

        } else {
            try {
                // not logged or wrong groupId
                response.sendRedirect("FrontController?option=dashboard");
            } catch (IOException ex) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "Impossible d'afficher la page", "danger"));
            }
        }

        return "/WEB-INF/login.jsp";

    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    private void getList(HttpServletRequest request, String queryString) {

        /* pagination */
        Pagination pagination = new Pagination(queryString, request.getParameter("page"), 10, btc.count());
        request.setAttribute("pagination", pagination.getPagination());

        List<CustomerTable> customerTables = btc.findAllByRange(pagination.getMin(), 10);
        request.setAttribute("customerTables", customerTables);
    }

    private boolean edit(HttpServletRequest request) {

        // test required input
        if (request.getParameter("number").trim().isEmpty() || request.getParameter("capacity").trim().isEmpty() || request.getParameter("nbTablet").trim().isEmpty()) {
            request.setAttribute("alert", Alert.setAlert("Attention", "Les champs * sont obligatoires", "warning"));
            return false;
        }

        CustomerTable ct = new CustomerTable();
        ct.setNumber(Integer.valueOf(request.getParameter("number")));
        ct.setCapacity(Integer.valueOf(request.getParameter("capacity")));
        ct.setNbTablet(Integer.valueOf(request.getParameter("nbTablet")));

        if (request.getParameter("id").isEmpty()) { // add
            ct.setBusy(false);
            ct.setActive(true);
            try {
                btc.create(ct);
                request.setAttribute("alert", Alert.setAlert("Succès", "La table été ajoutée", "success"));
            } catch (EJBException e) {
                request.setAttribute("alert", Alert.setAlert("Attention", "Cette table existe déjà", "warning"));
                return false;
            }
        } else { // update
            ct.setId(Long.valueOf(request.getParameter("id")));
            try {
                btc.update(ct);
                request.setAttribute("alert", Alert.setAlert("Succès", "La table été mise à jour", "success"));
            } catch (EJBException e) {
                request.setAttribute("alert", Alert.setAlert("Attention", "Cette table existe déjà", "warning"));
                return false;
            }
        }

        return true;

    }

}
