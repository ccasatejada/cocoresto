package ejb;

import entities.Combo;
import entities.ComboOrderLine;
import entities.CustomerOrder;
import entities.Dish;
import entities.DishOrderLine;
import entities.Drink;
import entities.DrinkOrderLine;
import entities.Employee;
import entities.OrderStatus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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

    @EJB
    private ejbRestaurantLocal ejbRestaurant;

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    private final Map<Session, HttpSession> sessions = new HashMap();

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
    public void addSession(Session session, HttpSession httpSession) {
        sessions.put(session, httpSession);

    }

    @Override
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    @Override
    public void sendOnPrepDish(CustomerOrder order, DishOrderLine dish) {
        JsonObject onPrepMessage = createOnPrepMessage(dish, null, null);
        sendToAllConnectedSessions(onPrepMessage, order);
    }

    @Override
    public void sendOnPrepDrink(CustomerOrder order, DrinkOrderLine drink) {
        JsonObject onPrepMessage = createOnPrepMessage(null, null, drink);
        sendToAllConnectedSessions(onPrepMessage, order);
    }

    @Override
    public void sendOnPrepCombo(CustomerOrder order, ComboOrderLine combo, DishOrderLine dish) {
        JsonObject onPrepMessage = createOnPrepMessage(dish, combo, null);
        sendToAllConnectedSessions(onPrepMessage, order);
    }

    @Override
    public void sendReadyDish(CustomerOrder order, DishOrderLine dish) {
        JsonObject readyMessage = createReadyMessage(dish, null, null);
        sendToAllConnectedSessions(readyMessage, order);
    }

    @Override
    public void sendReadyDrink(CustomerOrder order, DrinkOrderLine drink) {
        JsonObject readyMessage = createReadyMessage(null, null, drink);
        sendToAllConnectedSessions(readyMessage, order);
    }

    @Override
    public void sendReadyCombo(CustomerOrder order, ComboOrderLine combo, DishOrderLine dish) {
        JsonObject readyMessage = createReadyMessage(dish, combo, null);
        sendToAllConnectedSessions(readyMessage, order);
    }

    private JsonObject createOnPrepMessage(DishOrderLine dish, ComboOrderLine combo, DrinkOrderLine drink) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject onPrepMessage = null;
        if (dish != null && combo == null) {
            System.out.println("dish: " + dish.getDish().getName());
            onPrepMessage = provider.createObjectBuilder()
                    .add("action", "onprep")
                    .add("typeAlert", "dish")
                    .add("status", "En préparation")
                    .add("idDish", dish.getId())
                    .build();
        }
        if (drink != null) {
            System.out.println("drink : " + drink.getDrink().getName());
            onPrepMessage = provider.createObjectBuilder()
                    .add("action", "onprep")
                    .add("typeAlert", "drink")
                    .add("status", "En préparation")
                    .add("idDrink", drink.getId())
                    .build();
        }
        if (combo != null && dish != null) {
            System.out.println("combo : " + combo.getId());
            System.out.println("combo : " + combo.getCombo().getName());
            System.out.println("dish : " + dish.getId());
            System.out.println("dish : " + dish.getDish().getName());
            onPrepMessage = provider.createObjectBuilder()
                    .add("action", "onprep")
                    .add("typeAlert", "combo")
                    .add("status", "En préparation")
                    .add("idDishCombo", dish.getId())
                    .build();
        }
        return onPrepMessage;
    }

    private JsonObject createReadyMessage(DishOrderLine dish, ComboOrderLine combo, DrinkOrderLine drink) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject readyMessage = null;
        if (dish != null && combo == null) {
            System.out.println(dish.getDish().getName());
            readyMessage = provider.createObjectBuilder()
                    .add("action", "ready")
                    .add("typeAlert", "dish")
                    .add("status", "Prêt")
                    .add("idDish", dish.getId())
                    .build();
        }
        if (drink != null) {
            System.out.println(drink.getDrink().getName());
            readyMessage = provider.createObjectBuilder()
                    .add("action", "ready")
                    .add("typeAlert", "drink")
                    .add("status", "Prêt")
                    .add("idDrink", drink.getId())
                    .build();
        }
        if (combo != null && dish != null) {
            System.out.println("combo:" + combo.getCombo().getName());
            System.out.println("dish:" + dish.getDish().getId());
            readyMessage = provider.createObjectBuilder()
                    .add("action", "ready")
                    .add("typeAlert", "combo")
                    .add("status", "Prêt")
                    .add("idDishCombo", dish.getId())
                    .build();
        }

        return readyMessage;

    }

    private void sendToAllConnectedSessions(JsonObject message, CustomerOrder order) {
        sessions.forEach((session, httpSession) -> sendToSession((Session) session, message, order));
//        sendToSession(null, message, order);
//        for (Iterator entries = sessions.entrySet().iterator(); entries.hasNext();) {
//            System.out.println("debut hashmap");
//            Entry entry = (Entry) entries.next();
//            Session s = (Session) entry.getKey();
//            HttpSession hs = (HttpSession) entry.getValue();
//            if (hs.getAttribute("loggedEmployee") != null) {
//                Employee e = (Employee) hs.getAttribute("loggedEmployee");
//                if (order.getEmployee().getId() == e.getId()) {
//                    sendToSession(s, message, order);
//                    System.out.println("sessionEmployee = " + s.getId());
//                    System.out.println("httpSession employee = " + e.getLastName() + " " + e.getFirstName());
//                }
//            }
//            if (hs.getAttribute("table") != null) {
//
//                Integer ct = (Integer) hs.getAttribute("table");
//                if (order.getCustomerTable().getNumber() == ct) {
//                    sendToSession(s, message, order);
//                    System.out.println("sessionTable = " + s.getId());
//                    System.out.println("httpSessionTable = " + order.getCustomerTable().getNumber());
//                    System.out.println("num table = " + ct);
//                }
//            }
//        }
    }

    private void sendToSession(Session session, JsonObject message, CustomerOrder order) {
        try {
            for (Iterator entries = sessions.entrySet().iterator(); entries.hasNext();) {
                Entry entry = (Entry) entries.next();
                Session s = (Session) entry.getKey();

                HttpSession hs = (HttpSession) entry.getValue();
                Employee e = (Employee) hs.getAttribute("loggedEmployee");
                Integer ct = (Integer) hs.getAttribute("table");
                if (hs.getAttribute("loggedEmployee") != null && order.getEmployee().getId().equals(e.getId())) {
                    try {
                        if (s.isOpen()) {
                            s.getBasicRemote().sendText(message.toString());
                        }
                    } catch (NullPointerException npe) {
                    }
                }
                if (hs.getAttribute("table") != null && order.getCustomerTable().getNumber().equals(ct)) {
                    try {
                        if (s.isOpen()) {
                            s.getBasicRemote().sendText(message.toString());
                        }
                    } catch (NullPointerException npe) {
                    }
                }
            }
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(ejbCustomerOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
