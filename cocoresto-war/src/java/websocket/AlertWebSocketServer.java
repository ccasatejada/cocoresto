
package websocket;

import ejb.ejbCustomerOrderLocal;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
        ejbCustomerOrder.addSession(session);
    }
    
    @OnClose
    public void close(Session session){
        ejbCustomerOrder.removeSession(session);
    }
    
    @OnError
    public void onError(Throwable error){
        Logger.getLogger(AlertWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }
    
    @OnMessage
    public void handleMessage(String message, Session session) {
        try(JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            if("sendToWaiter".equals(jsonMessage.getString("action"))){
                
            }
            if("sendToCustomer".equals(jsonMessage.getString("action"))){
                
            }
        }
    }
    
}
