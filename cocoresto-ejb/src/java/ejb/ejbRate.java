
package ejb;

import entities.Discount;
import entities.Employee;
import entities.Tax;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ejbRate implements ejbRateLocal {
    
    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;
    
    @Override
    public void create(Tax tax) {
        em.persist(tax);
    }
    
    @Override
    public void create(Discount discount) {
        em.persist(discount);
    }
    
    @Override
    public void update(Tax tax) {
        em.merge(tax);
    }

    @Override
    public void update(Discount discount) {
        em.merge(discount);
    }
    
    @Override
    public void delete(Tax tax) {
        Tax ta = em.find(Tax.class, tax.getId());
        em.remove(ta);
    }
    
    @Override
    public void delete(Discount discount) {
        Discount di = em.find(Discount.class, discount.getId());
        em.remove(di);
    }

    @Override
    public Tax findTaxById(Long id) {
        Tax tax = new Tax();
        String query = "SELECT t FROM Tax t";
        Query qr = em.createQuery(query);
        List<Tax> tList = qr.getResultList();
        for(Tax ta : tList) {
            if(ta.getId().equals(id)) {
               tax = ta; 
            }
        }
        return tax;
    }

    @Override
    public Discount findDiscountById(Long id) {
        Discount discount = new Discount();
        String query = "SELECT d FROM Discount d";
        Query qr = em.createQuery(query);
        List<Discount> dList = qr.getResultList();
        for(Discount di : dList) {
            if(di.getId().equals(id)) {
               discount = di; 
            }
        }
        return discount;
    }

    @Override
    public ArrayList<Tax> findAllTaxes() {
        ArrayList<Tax> taxes = new ArrayList();
        
        String query = "SELECT t FROM Tax t";
        Query qr = em.createQuery(query);
        
        List<Tax> tList = qr.getResultList();
        for(Tax ta : tList) {
            taxes.add(ta);
        }
        
        return taxes;
    }

    @Override
    public ArrayList<Discount> findAllDiscounts() {
        ArrayList<Discount> discounts = new ArrayList();
        
        String query = "SELECT d FROM Discount d";
        Query qr = em.createQuery(query);
        
        List<Discount> dList = qr.getResultList();
        for(Discount di : dList) {
            discounts.add(di);
        }
        
        return discounts;
    }
        
    @Override
    public void persist(Object object) {
        em.persist(object);
    }

    

    
}
