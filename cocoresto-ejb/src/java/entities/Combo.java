
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Combo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    @ManyToMany
    private Collection<Dish> dishes;
    private boolean active;
    
    @ManyToOne
    private Category category;
    
    @ManyToOne
    private Price price;
    
    @ManyToOne
    private Discount discount;
    
    @ManyToOne
    private Tax tax;
    @ManyToMany(mappedBy = "combos")
    private List<CustomerOrder> customerOrders;

    public Combo() {
    }

    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }
    
    

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Collection<Dish> dishes) {
        this.dishes = dishes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    public Double getTotalPrice() {
        Double p = 0d;
        Date d = new Date();
        

        if (discount != null && d.before(discount.getEndDate()) && d.after(discount.getBeginDate())) {
            p = price.getPrice() - (price.getPrice() * (discount.getRate() / 100));
        } else {
            p = price.getPrice();
        }

        p = p * (1 + (tax.getRate() / 100));
        
        return round(p,2);
    }
    
    public Double getPriceWithDiscount(){
        Double p = 0d;
        Date d = new Date();
        

        if (discount != null && d.before(discount.getEndDate()) && d.after(discount.getBeginDate())) {
            p = price.getPrice() - (price.getPrice() * (discount.getRate() / 100));
        } else {
            return null;
        }
        
        return round(p,2);
    }
    
        public Double getPriceWithTax(){
        Double p = 0d;
        p = price.getPrice() * (1 + (tax.getRate() / 100));
        
        return round(p,2);
    }
    
    
    
    public Double round(Double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Combo)) {
            return false;
        }
        Combo other = (Combo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Combo[ id=" + id + " ]";
    }
    
}
