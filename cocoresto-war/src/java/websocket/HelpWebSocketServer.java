
package websocket;

import ejb.ejbHelp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/actions")
public class HelpWebSocketServer {

    
    private ejbHelp ejbHelp;
    
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
    
}
