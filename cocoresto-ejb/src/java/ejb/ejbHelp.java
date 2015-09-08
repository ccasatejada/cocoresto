package ejb;

import entities.CustomerOrder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

@Stateless
public class ejbHelp implements ejbHelpLocal {

    private int helpCount = 0;
    private ejbCustomerOrder ejbCustomerOrder;
    private ejbRestaurant ejbRestaurant;
    private final Set sessions = new HashSet<>();
    private final Set helps = new HashSet<CustomerOrder>();

    public ejbHelp() {
    }

    @Override
    public void addSession(Session session) {
        sessions.add(session);
        for (Entry<Integer, CustomerOrder> entry : ejbRestaurant.getOrders().entrySet()) {
            Integer key = entry.getKey();
            CustomerOrder order = entry.getValue();
            JsonObject addMessage = createAddMessage(order);
            sendToSession(session, addMessage);
        }

    }

    @Override
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    @Override
    public List getHelps() {
        return new ArrayList(helps);
    }

    @Override
    public void addHelp(CustomerOrder order) {
        helps.add(order);
        helpCount++;
        JsonObject addMessage = createAddMessage(order);
        sendToAllConnectedSessions(addMessage);
    }

    @Override
    public void removeHelp(Integer id) {
        CustomerOrder order = getOrderById(id);
        if(order != null) {
            helps.remove(order);
            helpCount--;
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }
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

    private JsonObject createAddMessage(CustomerOrder order) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("", "")
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
