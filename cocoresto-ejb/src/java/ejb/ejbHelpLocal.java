
package ejb;

import entities.CustomerOrder;
import java.util.List;
import javax.ejb.Local;
import javax.websocket.Session;

@Local
public interface ejbHelpLocal {
    
    public void addSession(Session session);
    public void removeSession(Session session);
    public void addHelp(CustomerOrder order);
    public void removeHelp(CustomerOrder order);
    
//    private JsonObject createAddMessage(CustomerOrder order);
//    private void sendToAllConnectedSessions(JsonObject message);
//    private void sendToSession(Session session, JsonObject message);
}
