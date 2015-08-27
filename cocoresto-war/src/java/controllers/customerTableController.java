package controllers;

import entities.CustomerTable;
import helpers.Alert;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.beanTableCustomer;

public class customerTableController extends AbstractController implements IController {

    beanTableCustomer btc = new beanTableCustomer();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        // set login values
        setLogged(request.getSession());

        String editUrl = "/WEB-INF/admin/customerTableEdit.jsp";
        String listUrl = "/WEB-INF/admin/customerTableList.jsp";

        if (logged && groupId >= 3) {

            if ("edit".equals(request.getParameter("task")) && request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
                try {
                    CustomerTable ct = btc.findById(Long.valueOf(request.getParameter("id")));
                    request.setAttribute("customerTable", ct);
                } catch (NumberFormatException | EJBException e) {
                    request.setAttribute("alert", Alert.setAlert("Erreur", "Cette table n'existe pas", "danger"));
                }
                return editUrl;
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

            // form has been send
            if (request.getParameter("confirm") != null) {
                edit(request);
            }

            getList(request);

            return listUrl;
            
        } else {
            try {
                // not logged or wrong groupId
                response.sendRedirect(request.getRequestURI());
            } catch (IOException ex) {
                request.setAttribute("alert", Alert.setAlert("Erreur", "impossible d'afficher la page", "danger"));
            }
        }

        return "/WEB-INF/index.jsp";

    }

    @Override
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    private void getList(HttpServletRequest request) {
        List<CustomerTable> customerTables = btc.findAll();
        request.setAttribute("customerTables", customerTables);
    }

    private void edit(HttpServletRequest request) {
        CustomerTable ct = new CustomerTable();
        ct.setNumber(Integer.valueOf(request.getParameter("number")));
        ct.setCapacity(Integer.valueOf(request.getParameter("capacity")));
        ct.setNbTablet(Integer.valueOf(request.getParameter("nbTablet")));

        if (request.getParameter("id").isEmpty()) { // add
            ct.setBusy(false);
            ct.setActive(true);
            btc.create(ct);
            request.setAttribute("alert", Alert.setAlert("Succès", "La table été ajoutée", "success"));
        } else { // update
            ct.setId(Long.valueOf(request.getParameter("id")));
            btc.update(ct);
            request.setAttribute("alert", Alert.setAlert("Succès", "La table été mise à jour", "success"));
        }
    }

}
