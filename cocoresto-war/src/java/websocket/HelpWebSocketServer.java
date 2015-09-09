package websocket;

import ejb.ejbHelpLocal;
import ejb.ejbRestaurant;
import ejb.ejbRestaurantLocal;
import entities.CustomerOrder;
import java.io.Serializable;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/FrontController/actions")
public class HelpWebSocketServer implements Serializable {

    @Inject
    ejbRestaurantLocal ejbRestaurant;

    @Inject
    ejbHelpLocal ejbHelp;
    

    public HelpWebSocketServer() {
//        ejbHelp = lookupejbHelpLocal();
//        ejbRestaurant = lookupejbRestaurantLocal();
    }

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        ejbHelp.addSession(session);
        

    }

    @OnClose
    public void close(Session session) {
        ejbHelp.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(HelpWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            if ("add".equals(jsonMessage.getString("action"))) {
                CustomerOrder order = ejbRestaurant.getOrder(Integer.valueOf(jsonMessage.getString("id")));
                order.setNeedHelp(true);
                ejbHelp.addHelp(order);                
            }

            if ("remove".equals(jsonMessage.getString("action"))) {
                CustomerOrder order = ejbRestaurant.getOrder(jsonMessage.getInt("id"));
                order.setNeedHelp(false);
                ejbHelp.removeHelp(order);
            }
        }
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
