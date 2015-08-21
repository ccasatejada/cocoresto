
package controllers;

import javax.servlet.http.*;

public interface IController {
    public String execute(HttpServletRequest request, HttpServletResponse response);
    
    public String execute(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response);
}
