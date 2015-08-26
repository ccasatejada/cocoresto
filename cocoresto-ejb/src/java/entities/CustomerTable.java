
package entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class CustomerTable implements Serializable {
    @OneToOne(mappedBy = "customerTable")
    private CustomerOrder customerOrder;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private Integer number;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false)
    private boolean busy;
    private Integer nbTablet;

    public CustomerTable() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    
    
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public Integer getNbTablet() {
        return nbTablet;
    }

    public void setNbTablet(Integer nbTablet) {
        this.nbTablet = nbTablet;
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
        if (!(object instanceof CustomerTable)) {
            return false;
        }
        CustomerTable other = (CustomerTable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CustomerTable[ id=" + id + " ]";
    }
    
}
