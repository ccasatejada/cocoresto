package websocket;

import ejb.ejbCustomerOrderLocal;
import ejb.ejbEmployeeLocal;
import ejb.ejbRestaurantLocal;
import entities.Combo;
import entities.ComboOrderLine;
import entities.CustomerOrder;
import entities.Dish;
import entities.DishOrderLine;
import entities.Drink;
import entities.DrinkOrderLine;
import entities.Employee;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
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

@ApplicationScoped
@ServerEndpoint(value = "/FrontController/alert", configurator = GetHttpSessionConfigurator.class)
public class AlertWebSocketServer {

    @Inject
    ejbCustomerOrderLocal ejbCustomerOrder;

    @Inject
    ejbRestaurantLocal ejbRestaurant;

    
    public AlertWebSocketServer() {
        ejbCustomerOrder = lookupejbCustomerOrderLocal();
        ejbRestaurant = lookupejbRestaurantLocal();
    }

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        System.out.println(session.getId());
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
            CustomerOrder order = ejbRestaurant.getOrder(Integer.valueOf(jsonMessage.getString("order")));
            if ("onprep".equals(jsonMessage.getString("action"))) {
                if ("dish".equals(jsonMessage.getString("element"))) {
                    Long id = Long.valueOf(jsonMessage.getString("dish"));
                    for (DishOrderLine dol : order.getDishes()) {
                        if (dol.getId() == id) {
                            Dish d = dol.getDish();
                            ejbCustomerOrder.sendOnPrepDish(order, d);
                            System.out.println(d.getName());
                        }
                    }
                }
                if ("combo".equals(jsonMessage.getString("element"))) {
                    Long idCombo = Long.valueOf(jsonMessage.getString("combo"));
                    System.out.println(idCombo);
                    Long id = Long.valueOf(jsonMessage.getString("dishcombo"));
                    System.out.println(id);
//                    for (ComboOrderLine col : order.getCombos()) {
//                        if (col.getId() == idCombo) {                            
//                            Combo c = col.getCombo(); 
//                            System.out.println(c.getName());
//                            for (DishOrderLine dol : col.getDishes()) {
//                                
//                                if (dol.getId() == id) {
//                                    Dish d = dol.getDish();
//                                    ejbCustomerOrder.sendOnPrepCombo(order, c, d);
//                                }
//                            }
//                        }
//                    }
                    for(DishOrderLine dol : order.getDishes()){
                        if(dol.getComboOrderLine().getId() == idCombo){
                            Combo c = dol.getComboOrderLine().getCombo();
                            System.out.println(c.getName());
                            for(DishOrderLine dd : dol.getComboOrderLine().getDishes()){
                                if(dd.getId() == id){
                                    Dish d = dd.getDish();
                                    System.out.println(d.getName());
                                    ejbCustomerOrder.sendOnPrepCombo(order, c, d);
                                }
                            }
                        }
                    }
                }
                if ("drink".equals(jsonMessage.getString("element"))) {
                    Long id = Long.valueOf(jsonMessage.getString("drink"));
                    for (DrinkOrderLine dol : order.getDrinks()) {
                        if (dol.getId() == id) {
                            Drink d = dol.getDrink();
                            ejbCustomerOrder.sendOnPrepDrink(order, d);
                        }
                    }
                }
            }
            if ("ready".equals(jsonMessage.getString("action"))) {
                if ("dish".equals(jsonMessage.getString("element"))) {
                    Long id = Long.valueOf(jsonMessage.getString("dish"));
                    for (DishOrderLine dol : order.getDishes()) {
                        Dish d = dol.getDish();
                        ejbCustomerOrder.sendReadyDish(order, d);
                    }
                }
                if ("combo".equals(jsonMessage.getString("element"))) {
                    Long idCombo = Long.valueOf(jsonMessage.getString("combo"));
                    Long id = Long.valueOf(jsonMessage.getString("dishcombo"));
                    for (ComboOrderLine col : order.getCombos()) {
                        if (col.getId() == idCombo) {
                            Combo c = col.getCombo();
                            for (DishOrderLine dol : col.getDishes()) {
                                if (dol.getId() == id) {
                                    Dish d = dol.getDish();
                                    ejbCustomerOrder.sendReadyCombo(order, c, d);
                                }
                            }
                        }
                    }
                }
                if ("drink".equals(jsonMessage.getString("element"))) {
                    Long id = Long.valueOf(jsonMessage.getString("drink"));
                    for (DrinkOrderLine dol : order.getDrinks()) {
                        if (dol.getId() == id) {
                            Drink d = dol.getDrink();
                            ejbCustomerOrder.sendReadyDrink(order, d);
                        }
                    }
                }
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
