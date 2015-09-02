package controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FrontController extends HttpServlet {

    HashMap<String, IController> map;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        map = new HashMap();
        for (Enumeration<String> e = config.getInitParameterNames(); e.hasMoreElements();) {
            String name = e.nextElement();
            String value = config.getInitParameter(name);
            try {
                IController c = (IController) Class.forName(value).newInstance();
                map.put(name, c);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "/WEB-INF/template.jsp";
        String content = "/WEB-INF/login.jsp";
        request.setAttribute("date", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        
        if (map.containsKey(request.getParameter("option"))) {
            IController c = map.get(request.getParameter("option"));
            content = c.execute(request, response);
        }
        
        request.setAttribute("content", content);
        request.getRequestDispatcher(url).include(request, response);
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
