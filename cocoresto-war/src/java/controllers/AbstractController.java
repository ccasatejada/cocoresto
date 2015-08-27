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

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    
    
    
}
