package ejb;

import entities.Combo;
import entities.CustomerOrder;
import entities.CustomerTable;
import entities.Dish;
import entities.Drink;
import entities.Employee;
import entities.OrderStatus;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@Stateless
public class ejbCustomerOrder implements ejbCustomerOrderLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    private ejbRestaurant ejbRestaurant = new ejbRestaurant();
    private Map sessions = new HashMap<Session, HttpSession>();

    @Override
    public void create(CustomerOrder customerOrder) {
        em.persist(customerOrder);
    }

    @Override
    public void update(CustomerOrder customerOrder) {
        em.merge(customerOrder);
    }

    @Override
    public void delete(CustomerOrder customerOrder) {
        //CustomerOrder co = em.find(CustomerOrder.class, customerOrder.getId());
        //em.remove(co);
        customerOrder.setActive(false);
        em.merge(customerOrder);
    }

    @Override
    public CustomerOrder findById(Long id) {
        CustomerOrder co = em.find(CustomerOrder.class, id);
        return co;
    }

    @Override
    public List<CustomerOrder> findAll() {
        Query q = em.createQuery("select co from CustomerOrder co where co.active = 1 order by co.orderDate desc");
        return q.getResultList();
    }

    @Override
    public List<CustomerOrder> findAllByRange(int firstResult, int maxResults) {
        Query q = em.createQuery("select co from CustomerOrder co where co.active = 1 order by co.orderDate desc");
        if (firstResult >= 0) {
            q.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            q.setMaxResults(maxResults);
        }
        return q.getResultList();
    }

    @Override
    public List<CustomerOrder> findAllByRangeByEmployee(int firstResult, int maxResults, Long id) {
        Query q = em.createQuery("select co from CustomerOrder co where co.active = 1 AND co.employee.id = " + id + " order by co.status desc, co.orderDate desc");
        if (firstResult >= 0) {
            q.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            q.setMaxResults(maxResults);
        }
        return q.getResultList();
    }

    @Override
    public int count() {
        return ((Long) em.createQuery("select COUNT(co) from CustomerOrder co where co.active = 1").getSingleResult()).intValue();
    }

    @Override
    public List<CustomerOrder> findCurrentOrders() {
        Query q = em.createQuery("SELECT co FROM CustomerOrder co WHERE co.active = 1 AND co.status != :cancelled AND co.status != :payed");
        q.setParameter("cancelled", OrderStatus.CANCELLED);
        q.setParameter("payed", OrderStatus.PAYED);
        return q.getResultList();
    }

    @Override
    public List<CustomerOrder> findOrdersByStatus(OrderStatus status1, OrderStatus status2) {
        Query q = em.createQuery("SELECT co FROM CustomerOrder co WHERE co.active = 1 AND co.status = :orderStatus1 OR co.status = :orderStatus2 ORDER BY co.orderDate asc");
        q.setParameter("orderStatus1", status1);
        q.setParameter("orderStatus2", status2);
        return q.getResultList();
    }

    @Override
    public void addSession(Session session, HttpSession httpSession) {
        sessions.put(session, httpSession);
        for (Entry<Integer, CustomerOrder> entry : ejbRestaurant.getOrders().entrySet()) {
            Integer key = entry.getKey();
            CustomerOrder order = entry.getValue();
        }
    }

    @Override
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    @Override
    public void sendOnPrepDish(CustomerOrder order, Dish dish) {
        JsonObject onPrepMessage = createOnPrepMessage(dish, null, null);
        sendToAllConnectedSessions(onPrepMessage, order);
    }

    @Override
    public void sendOnPrepDrink(CustomerOrder order, Drink drink) {
        JsonObject onPrepMessage = createOnPrepMessage(null, null, drink);
        sendToAllConnectedSessions(onPrepMessage, order);
    }

    @Override
    public void sendOnPrepCombo(CustomerOrder order, Combo combo, Dish dish) {
        JsonObject onPrepMessage = createOnPrepMessage(dish, combo, null);
        sendToAllConnectedSessions(onPrepMessage, order);
    }

    @Override
    public void sendReadyDish(CustomerOrder order, Dish dish) {
        JsonObject readyMessage = createReadyMessage(dish, null, null);
        sendToAllConnectedSessions(readyMessage, order);
    }

    @Override
    public void sendReadyDrink(CustomerOrder order, Drink drink) {
        JsonObject readyMessage = createReadyMessage(null, null, drink);
        sendToAllConnectedSessions(readyMessage, order);
    }

    @Override
    public void sendReadyCombo(CustomerOrder order, Combo combo, Dish dish) {
        JsonObject readyMessage = createReadyMessage(dish, combo, null);
        sendToAllConnectedSessions(readyMessage, order);
    }

    private JsonObject createOnPrepMessage(Dish dish, Combo combo, Drink drink) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject onPrepMessage = provider.createObjectBuilder()
                .add("action", "onprep")
                .add("status", "En préparation")
                .add("dish", dish.getName())
                .add("drink", drink.getName())
                .add("combo", combo.getName())
                .build();

        return onPrepMessage;
    }

    private JsonObject createReadyMessage(Dish dish, Combo combo, Drink drink) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject readyMessage = provider.createObjectBuilder()
                .add("action", "ready")
                .add("status", "Terminé")
                .add("dish", dish.getName())
                .add("drink", drink.getName())
                .add("combo", combo.getName())
                .build();

        return readyMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message, CustomerOrder order) {
        for (Iterator entries = sessions.entrySet().iterator(); entries.hasNext();) {
            Entry entry = (Entry) entries.next();
            Session s = (Session) entry.getKey();
            HttpSession hs = (HttpSession) entry.getValue();
            if (hs.getAttribute("loggedEmployee") != null) {
                Employee e = (Employee) hs.getAttribute("loggedEmployee");
                if (order.getEmployee().getId() == e.getId()) {
                    sendToSession(s, message);
                }
            }
            if (hs.getAttribute("table") != null) {
                CustomerTable ct = (CustomerTable) hs.getAttribute("table");
                if (order.getCustomerTable().getId() == ct.getId()) {
                    sendToSession(s, message);
                }
            }
        }

        for (Session session : (HashSet<Session>) sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(ejbCustomerOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
