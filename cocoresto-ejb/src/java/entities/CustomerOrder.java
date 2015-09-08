package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;

@Entity
public class CustomerOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String number;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date orderDate;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(nullable = false)
    private Integer people;
    @Column(nullable = false)
    private Integer nbTablet;
    @Transient
    private Integer currentTablets;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Employee employee;
    @OneToOne
    @JoinColumn(nullable = false)
    private CustomerTable customerTable;

    @ManyToMany
    private Collection<Drink> drinks;

    @ManyToMany
    private Collection<Dish> dishes;

    @ManyToMany
    private Collection<Combo> combos;

    @Transient
    private boolean needHelp;

    public CustomerOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public Integer getNbTablet() {
        return nbTablet;
    }

    public void setNbTablet(Integer nbTablet) {
        this.nbTablet = nbTablet;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public CustomerTable getCustomerTable() {
        return customerTable;
    }

    public void setCustomerTable(CustomerTable customerTable) {
        this.customerTable = customerTable;
    }

    public Collection<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Collection<Dish> dishes) {
        this.dishes = dishes;
    }

    public Collection<Combo> getCombos() {
        return combos;
    }

    public void setCombos(Collection<Combo> combos) {
        this.combos = combos;
    }

    public Collection<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(Collection<Drink> drinks) {
        this.drinks = drinks;
    }

    public boolean isNeedHelp() {
        return needHelp;
    }

    public void setNeedHelp(boolean needHelp) {
            this.needHelp = needHelp;        
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CustomerOrder)) {
            return false;
        }
        CustomerOrder other = (CustomerOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CustomerOrder[ id=" + id + " ]";
    }

}
