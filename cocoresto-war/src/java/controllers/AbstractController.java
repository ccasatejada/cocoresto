package controllers;

import javax.servlet.http.HttpSession;

public class AbstractController {

    protected boolean logged = false;
    protected Long groupId = 0L;

    protected void setLogged(HttpSession session) {
        if (session.getAttribute("logged") != null && session.getAttribute("group") != null) {
            this.logged = (boolean) session.getAttribute("logged");
            this.groupId = (Long) session.getAttribute("group");
        }
    }
    
}
