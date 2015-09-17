package websocket;

import ejb.ejbCustomerOrderLocal;
import ejb.ejbRestaurantLocal;
import entities.ComboOrderLine;
import entities.CustomerOrder;
import entities.DishOrderLine;
import entities.DrinkOrderLine;
import java.io.Serializable;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
@ServerEndpoint(value = "/FrontController/alert")
public class AlertWebSocketServer implements Serializable {

    @EJB
    ejbCustomerOrderLocal ejbCustomerOrder;

    @EJB
    ejbRestaurantLocal ejbRestaurant;

    public AlertWebSocketServer() {
        ejbCustomerOrder = lookupejbCustomerOrderLocal();
        ejbRestaurant = lookupejbRestaurantLocal();
    }

    @OnOpen
    public void open(Session session) {
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
            CustomerOrder order = ejbCustomerOrder.findById(Long.valueOf(jsonMessage.getString("order")));
            if ("onprep".equals(jsonMessage.getString("action"))) {
                if ("dish".equals(jsonMessage.getString("element"))) {
                    Long id = Long.valueOf(jsonMessage.getString("dish"));
                    for (DishOrderLine dol : order.getDishes()) {
                        if (dol.getId().equals(id)) {
                            DishOrderLine d = dol;
                            ejbCustomerOrder.sendOnPrepDish(order, d);
                            break;
                        }
                    }
                }
                if ("combo".equals(jsonMessage.getString("element"))) {
                    Long id = Long.valueOf(jsonMessage.getString("dishcombo"));
                    for (ComboOrderLine col : order.getCombos()) {
                        for (DishOrderLine dol : col.getDishes()) {
                            if (dol.getId().equals(id)) {
                                ComboOrderLine c = dol.getComboOrderLine();
                                DishOrderLine d = dol;
                                ejbCustomerOrder.sendOnPrepCombo(order, c, d);
                                break;
                            }
                        }
                    }
                }

                if ("drink".equals(jsonMessage.getString("element"))) {
                    Long id = Long.valueOf(jsonMessage.getString("drink"));
                    for (DrinkOrderLine dol : order.getDrinks()) {
                        if (dol.getId().equals(id)) {
                            DrinkOrderLine d = dol;
                            ejbCustomerOrder.sendOnPrepDrink(order, d);
                            break;
                        }
                    }
                }
            }
            if ("ready".equals(jsonMessage.getString("action"))) {
                if ("dish".equals(jsonMessage.getString("element"))) {
                    Long id = Long.valueOf(jsonMessage.getString("dish"));
                    for (DishOrderLine dol : order.getDishes()) {
                        if (dol.getId().equals(id)) {
                            DishOrderLine d = dol;
                            ejbCustomerOrder.sendReadyDish(order, d);
                            break;
                        }
                    }
                }
                if ("combo".equals(jsonMessage.getString("element"))) {
                    Long id = Long.valueOf(jsonMessage.getString("dishcombo"));
                    for (ComboOrderLine col : order.getCombos()) {
                        for (DishOrderLine dol : col.getDishes()) {
                            if (dol.getId().equals(id)) {
                                ComboOrderLine c = dol.getComboOrderLine();
                                DishOrderLine d = dol;
                                ejbCustomerOrder.sendReadyCombo(order, c, d);
                                break;
                            }
                        }
                    }
                }
                if ("drink".equals(jsonMessage.getString("element"))) {
                    Long id = Long.valueOf(jsonMessage.getString("drink"));
                    for (DrinkOrderLine dol : order.getDrinks()) {
                        if (dol.getId().equals(id)) {
                            DrinkOrderLine d = dol;
                            ejbCustomerOrder.sendReadyDrink(order, d);
                            break;
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
