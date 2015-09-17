package ejb;

import entities.ComboOrderLine;
import entities.CustomerOrder;
import entities.DishOrderLine;
import entities.DrinkOrderLine;
import entities.OrderStatus;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.websocket.Session;

@Stateless
public class ejbCustomerOrder implements ejbCustomerOrderLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    private final Set<Session> sessions = new HashSet();

    public ejbCustomerOrder() {
    }

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
    public void addSession(Session session) {
        sessions.add(session);

    }

    @Override
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    @Override
    public void sendOnPrepDish(CustomerOrder order, DishOrderLine dish) {
        JsonObject onPrepMessage = createOnPrepMessage(order, dish, null, null);
        sendToAllConnectedSessions(onPrepMessage);
    }

    @Override
    public void sendOnPrepDrink(CustomerOrder order, DrinkOrderLine drink) {
        JsonObject onPrepMessage = createOnPrepMessage(order, null, null, drink);
        sendToAllConnectedSessions(onPrepMessage);
    }

    @Override
    public void sendOnPrepCombo(CustomerOrder order, ComboOrderLine combo, DishOrderLine dish) {
        JsonObject onPrepMessage = createOnPrepMessage(order, dish, combo, null);
        sendToAllConnectedSessions(onPrepMessage);
    }

    @Override
    public void sendReadyDish(CustomerOrder order, DishOrderLine dish) {
        JsonObject readyMessage = createReadyMessage(order, dish, null, null);
        sendToAllConnectedSessions(readyMessage);
    }

    @Override
    public void sendReadyDrink(CustomerOrder order, DrinkOrderLine drink) {
        JsonObject readyMessage = createReadyMessage(order, null, null, drink);
        sendToAllConnectedSessions(readyMessage);
    }

    @Override
    public void sendReadyCombo(CustomerOrder order, ComboOrderLine combo, DishOrderLine dish) {
        JsonObject readyMessage = createReadyMessage(order, dish, combo, null);
        sendToAllConnectedSessions(readyMessage);
    }

    private JsonObject createOnPrepMessage(CustomerOrder order, DishOrderLine dish, ComboOrderLine combo, DrinkOrderLine drink) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject onPrepMessage = null;
        if (dish != null && combo == null) {
            onPrepMessage = provider.createObjectBuilder()
                    .add("action", "onprep")
                    .add("typeAlert", "dish")
                    .add("status", "En préparation")
                    .add("idOrderLineAlert", dish.getId())
                    .add("statusOrder", order.getStatus().getName())
                    .add("statusOrderEnum", String.valueOf(order.getStatus()))
                    .add("idCustomerOrder", order.getId())
                    .build();
        }
        if (drink != null) {
            onPrepMessage = provider.createObjectBuilder()
                    .add("action", "onprep")
                    .add("typeAlert", "drink")
                    .add("status", "En préparation")
                    .add("idOrderLineAlert", drink.getId())
                    .add("statusOrder", order.getStatus().getName())
                    .add("statusOrderEnum", String.valueOf(order.getStatus()))
                    .add("idCustomerOrder", order.getId())
                    .build();
        }
        if (combo != null && dish != null) {
            onPrepMessage = provider.createObjectBuilder()
                    .add("action", "onprep")
                    .add("typeAlert", "combo")
                    .add("status", "En préparation")
                    .add("idOrderLineAlert", dish.getId())
                    .add("statusOrder", order.getStatus().getName())
                    .add("statusOrderEnum", String.valueOf(order.getStatus()))
                    .add("idCustomerOrder", order.getId())
                    .build();
        }
        return onPrepMessage;
    }

    private JsonObject createReadyMessage(CustomerOrder order, DishOrderLine dish, ComboOrderLine combo, DrinkOrderLine drink) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject readyMessage = null;
        if (dish != null && combo == null) {
            readyMessage = provider.createObjectBuilder()
                    .add("action", "ready")
                    .add("typeAlert", "dish")
                    .add("status", "Prêt")
                    .add("idOrderLineAlert", dish.getId())
                    .add("statusOrder", order.getStatus().getName())
                    .add("statusOrderEnum", String.valueOf(order.getStatus()))
                    .add("idCustomerOrder", order.getId())
                    .build();
        }
        if (drink != null) {
            readyMessage = provider.createObjectBuilder()
                    .add("action", "ready")
                    .add("typeAlert", "drink")
                    .add("status", "Prêt")
                    .add("idOrderLineAlert", drink.getId())
                    .add("statusOrder", order.getStatus().getName())
                    .add("statusOrderEnum", String.valueOf(order.getStatus()))
                    .add("idCustomerOrder", order.getId())
                    .build();
        }
        if (combo != null && dish != null) {
            readyMessage = provider.createObjectBuilder()
                    .add("action", "ready")
                    .add("typeAlert", "combo")
                    .add("status", "Prêt")
                    .add("idOrderLineAlert", dish.getId())
                    .add("statusOrder", order.getStatus().getName())
                    .add("statusOrderEnum", String.valueOf(order.getStatus()))
                    .add("idCustomerOrder", order.getId())
                    .build();
        }

        return readyMessage;

    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : (HashSet<Session>) sessions) {
            if (session.isOpen()) {
                sendToSession(session, message);
            }
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        for (Iterator entries = sessions.entrySet().iterator(); entries.hasNext();) {
//            Entry entry = (Entry) entries.next();
//            Session s = (Session) entry.getKey();
//            HttpSession hs = (HttpSession) entry.getValue();
//            if (hs.getAttribute("table") != null || hs.getAttribute("employee") != null) {
//                if (s.isOpen()) {
//                    try {
//                        s.getBasicRemote().sendText(message.toString());
//                    } catch (IOException ex) {
//                        Logger.getLogger(ejbCustomerOrder.class.getName()).log(Level.SEVERE, null, ex);
//                        sessions.remove(s);
//                        continue;
//                    }
//                }
//            } else {
//                sessions.remove(s);
//            }
//        }
    }
}
