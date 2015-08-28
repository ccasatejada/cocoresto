
package ejb;

import entities.Price;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ejbPriceLocal {
    public void create(Price price);
    public void update(Price price);
    public void delete(Price price);
    public Price findById(Long id);
    public List<Price> findAll();
    public Price findLastInserted();
    public Price findByValue(Double price);
}
