package ejb;

import entities.Combo;
import entities.ComboOrderLine;
import entities.CustomerOrder;
import entities.Dish;
import entities.DishOrderLine;
import entities.Drink;
import entities.DrinkOrderLine;
import entities.OrderStatus;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@Local
public interface ejbCustomerOrderLocal {
    
    public void create(CustomerOrder customerOrder);

    public void update(CustomerOrder customerOrder);
    
    public void delete(CustomerOrder customerOrder);

    public CustomerOrder findById(Long id);

    public List<CustomerOrder> findAll();
    
    public List<CustomerOrder> findAllByRange(int firstResult, int maxResults);
    
    public List<CustomerOrder> findAllByRangeByEmployee(int firstResult, int maxResults, Long id);

    public int count();    

    public List<CustomerOrder> findCurrentOrders();
    
    public List<CustomerOrder> findOrdersByStatus(OrderStatus status1, OrderStatus status2);
    
    public void addSession(Session session, HttpSession httpSession);
    public void removeSession(Session session);
    public void sendOnPrepDish(CustomerOrder order, DishOrderLine dish);
    public void sendOnPrepDrink(CustomerOrder order, DrinkOrderLine drink);
    public void sendOnPrepCombo(CustomerOrder order, ComboOrderLine combo, DishOrderLine dish);
    
    public void sendReadyDish(CustomerOrder order, DishOrderLine dish);
    public void sendReadyDrink(CustomerOrder order, DrinkOrderLine drink);
    public void sendReadyCombo(CustomerOrder order, ComboOrderLine combo, DishOrderLine dish);

}
