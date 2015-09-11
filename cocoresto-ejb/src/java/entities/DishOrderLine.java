package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class DishOrderLine implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Dish dish;
    @Transient
    private Integer status;
    @ManyToOne
    private CustomerOrder customerOrder;
    @ManyToOne
    private ComboOrderLine comboOrderLine;

    public DishOrderLine() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CustomerOrder getCustomerOrders() {
        return customerOrder;
    }

    public void setCustomerOrders(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public ComboOrderLine getCombo() {
        return comboOrderLine;
    }

    public void setCombo(ComboOrderLine combo) {
        this.comboOrderLine = combo;
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
        if (!(object instanceof DishOrderLine)) {
            return false;
        }
        DishOrderLine other = (DishOrderLine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DishOrderLine[ id=" + id + " ]";
    }
    
}
