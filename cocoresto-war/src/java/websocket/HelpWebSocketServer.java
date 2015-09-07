
package websocket;

import ejb.ejbHelp;
import ejb.ejbHelpLocal;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("actions")
public class HelpWebSocketServer implements Serializable {
    
    @EJB
    ejbHelpLocal ejbHelp = lookupejbHelpLocal();
    
    @OnOpen
    public void open(Session session){
        ejbHelp.addSession(session);
    }
    
    @OnClose
    public void close(Session session){
        ejbHelp.removeSession(session);
    }
    
    @OnError
    public void onError(Throwable error){
        Logger.getLogger(HelpWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }
    
    @OnMessage
    public String handleMessage(String message, Session session) {
        return null;
    }

    private ejbHelpLocal lookupejbHelpLocal() {
        try {
            Context c = new InitialContext();
            return (ejbHelpLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbHelp!ejb.ejbHelpLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
    
}
