package websocket;

import ejb.ejbCustomerOrderLocal;
import ejb.ejbEmployeeLocal;
import ejb.ejbRestaurantLocal;
import entities.CustomerOrder;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
    ejbEmployeeLocal ejbEmployee;

    @Inject
    ejbCustomerOrderLocal ejbCustomerOrder;

    @Inject
    ejbRestaurantLocal ejbRestaurant;

    private Session wsSession;
    private HttpSession httpSession;

    public AlertWebSocketServer() {
        ejbCustomerOrder = lookupejbCustomerOrderLocal();
        ejbRestaurant = lookupejbRestaurantLocal();
    }

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        this.wsSession = session;
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        System.out.println("getId: " + wsSession.getId());
        System.out.println("getContainer : " + wsSession.getContainer());
        for (Session s : wsSession.getOpenSessions()) {
            System.out.println("getIdsession : " + s.getId());
        }
        System.out.println("group : " + httpSession.getAttribute("group"));
        System.out.println("loggedEmployee : " + httpSession.getAttribute("loggedEmployee"));
        System.out.println("userName : " + httpSession.getAttribute("userName"));
        System.out.println("table : " + httpSession.getAttribute("table"));
        System.out.println("========================================");
        System.out.println("========================================");

        ejbCustomerOrder.addSession(session, httpSession);
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
            if ("onprep".equals(jsonMessage.getString("action"))) {
                CustomerOrder order = ejbRestaurant.getOrder(Integer.valueOf(jsonMessage.getString("order")));
                
                if (jsonMessage.getString("dish") != null) {
                    Integer id = Integer.valueOf(jsonMessage.getString("dish"));
                }
                if(jsonMessage.getString("combo") != null){
                    Integer idCombo = Integer.valueOf(jsonMessage.getString("combo"));
                    Integer id = Integer.valueOf(jsonMessage.getString("dishcombo"));
                }
                if(jsonMessage.getString("drink") != null){
                    Integer id = Integer.valueOf(jsonMessage.getString("drink"));
                }
            }
            if ("ready".equals(jsonMessage.getString("action"))) {

            }
        }
    }

    private ejbCustomerOrderLocal lookupejbCustomerOrderLocal() {
        try {
            Context c = new InitialContext();
            return (ejbCustomerOrderLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbCustomerOrder!ejb.ejbCustomerOrderLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ejbRestaurantLocal lookupejbRestaurantLocal() {
        try {
            Context c = new InitialContext();
            return (ejbRestaurantLocal) c.lookup("java:global/cocoresto/cocoresto-ejb/ejbRestaurant!ejb.ejbRestaurantLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
