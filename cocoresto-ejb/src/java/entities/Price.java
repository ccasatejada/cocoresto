
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

@Entity
public class Price implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double price;
    @OneToMany(mappedBy = "price")
    private Collection<Drink> drinks;
    @OneToMany(mappedBy = "price")
    private Collection<Combo> combos;
    @OneToMany(mappedBy = "price")
    private Collection<Dish> dishes;
    


    public Price() {
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Collection<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(Collection<Drink> drinks) {
        this.drinks = drinks;
    }

    public Collection<Combo> getCombos() {
        return combos;
    }

    public void setCombos(Collection<Combo> combos) {
        this.combos = combos;
    }

    public Collection<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Collection<Dish> dishes) {
        this.dishes = dishes;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Price)) {
            return false;
        }
        Price other = (Price) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return price + "";
    }

    

    
}
