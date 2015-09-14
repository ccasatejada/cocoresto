package entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class ComboOrderLine implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Combo combo;
    @OneToMany(mappedBy = "comboOrderLine")
    private List<DishOrderLine> dishes;
    @ManyToOne
    private CustomerOrder customerOrder;

    public ComboOrderLine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
    }

    public List<DishOrderLine> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishOrderLine> dishes) {
        this.dishes = dishes;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrders(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
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
        if (!(object instanceof ComboOrderLine)) {
            return false;
        }
        ComboOrderLine other = (ComboOrderLine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ComboOrderLine[ id=" + id + " ]";
    }
    
}
