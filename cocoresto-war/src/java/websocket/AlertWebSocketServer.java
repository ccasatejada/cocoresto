package websocket;

import ejb.ejbCustomerOrderLocal;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/FrontController/alert", configurator = GetHttpSessionConfigurator.class)
public class AlertWebSocketServer {

    @Inject
    ejbCustomerOrderLocal ejbCustomerOrder;
    private Session wsSession;
    private HttpSession httpSession;

    public AlertWebSocketServer() {
    }

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        this.wsSession = session;
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        System.out.println("getId: " +wsSession.getId());
        System.out.println("getContainer : " +wsSession.getContainer());
        for(Session s : wsSession.getOpenSessions()){
            System.out.println("getIdsession : " + s.getId());
        }
        System.out.println("group : " + httpSession.getAttribute("group"));
        System.out.println("loggedEmployee : " + httpSession.getAttribute("loggedEmployee"));
        System.out.println("userName : " + httpSession.getAttribute("userName"));
        System.out.println("table : " + httpSession.getAttribute("table"));
        System.out.println("========================================");
        System.out.println("========================================");
        
        ejbCustomerOrder.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        ejbCustomerOrder.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(AlertWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            if ("sendToWaiter".equals(jsonMessage.getString("action"))) {

            }
            if ("sendToCustomer".equals(jsonMessage.getString("action"))) {

            }
        }
    }

}
