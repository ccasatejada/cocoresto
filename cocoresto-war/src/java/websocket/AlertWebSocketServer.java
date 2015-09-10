
package websocket;

import ejb.ejbCustomerOrderLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/FrontController/alert")
public class AlertWebSocketServer {
    
    @Inject
    ejbCustomerOrderLocal ejbCustomerOrder;

    public AlertWebSocketServer() {
    }

    @OnOpen
    public void open(Session session){
        
    }
    
    @OnClose
    public void close(Session session){
        
    }
    
    @OnError
    public void onError(Throwable error){
        Logger.getLogger(AlertWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }
    
    @OnMessage
    public String onMessage(String message) {
        return null;
    }
    
}
