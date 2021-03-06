package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;

@Entity
public class Drink implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String name;
    private Integer inventory;
    private String description;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Format format;
    @ManyToOne
    private Price price;
    @ManyToOne
    private Discount discount;
    @ManyToOne
    private Tax tax;
    @OneToMany(mappedBy = "drink")
    private List<DrinkOrderLine> drinkOrderLines;
    private boolean active;

    public Drink() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
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

    public List<DrinkOrderLine> getDrinkOrderLines() {
        return drinkOrderLines;
    }

    public void setDrinkOrderLines(List<DrinkOrderLine> drinkOrderLines) {
        this.drinkOrderLines = drinkOrderLines;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public Double getTotalPrice() {
        Double priceTax = 0d;
        Double totalPrice = 0d;
        if (discount != null) {
            Double priceDiscount = price.getPrice() * (discount.getRate() / 100);
            totalPrice = price.getPrice() - priceDiscount;
            priceTax = totalPrice * (tax.getRate() / 100);
            totalPrice += priceTax;
        } else {
            priceTax = price.getPrice() * (tax.getRate() / 100);
            totalPrice = price.getPrice() + priceTax;
        }

        return round(totalPrice, 1);
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
        if (!(object instanceof Drink)) {
            return false;
        }
        Drink other = (Drink) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Drink[ id=" + id + " ]";
    }

}
