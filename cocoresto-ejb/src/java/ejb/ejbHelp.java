package ejb;

import entities.CustomerOrder;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

@Stateless
public class ejbHelp implements ejbHelpLocal {

    private int helpCount = 0;
    private ejbRestaurant ejbRestaurant = new ejbRestaurant();
    private final Set sessions = new HashSet<>();

    public ejbHelp() {
    }

    @Override
    public void addSession(Session session) {
        sessions.add(session);
        for (Entry<Integer, CustomerOrder> entry : ejbRestaurant.getOrders().entrySet()) {
            Integer key = entry.getKey();
            CustomerOrder order = entry.getValue();
            JsonObject addMessage = createAddMessage(order, helpCount);
            sendToSession(session, addMessage);
        }

    }

    @Override
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    @Override
    public void addHelp(CustomerOrder order) {
        int i = 0;
        for (Entry<Integer, CustomerOrder> entry : ejbRestaurant.getOrders().entrySet()) {
            CustomerOrder o = entry.getValue();
            if (order.isNeedHelp() == true) {
                i++;
            }
        }
        helpCount += 1;
        JsonObject addMessage = createAddMessage(order, helpCount);
        sendToAllConnectedSessions(addMessage);
    }

    @Override
    public void removeHelp(CustomerOrder order) {
        helpCount -= 1;
        JsonProvider provider = JsonProvider.provider();
        JsonObject removeMessage = provider.createObjectBuilder()
                .add("action", "remove")
                .add("id", order.getCustomerTable().getNumber())
                .build();
        sendToAllConnectedSessions(removeMessage);

    }

    private CustomerOrder getOrderById(Integer id) {
        for (Entry<Integer, CustomerOrder> entry : ejbRestaurant.getOrders().entrySet()) {
            Integer key = entry.getKey();
            CustomerOrder order = entry.getValue();
            if (order.getCustomerTable().getNumber() == id) {
                return order;
            }
        }
        return null;
    }

    private JsonObject createAddMessage(CustomerOrder order, int helpCount) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("count", helpCount)
                .add("number", order.getCustomerTable().getNumber())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : (HashSet<Session>) sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(ejbHelp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
