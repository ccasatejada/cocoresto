package ejb;

import entities.Category;
import entities.Drink;
import entities.Format;
import entities.Price;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ejbDrink implements ejbDrinkLocal {
    
    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    @Override
    public ArrayList<Format> findFormats() {
        ArrayList<Format> formats = new ArrayList();
        
        String query = "SELECT fo FROM Format fo";
        Query qr = em.createQuery(query);
        
        List<Format> foList = qr.getResultList();
        for(Format form : foList) {
            formats.add(form);
        }
        return formats;
    }
    
    @Override
    public ArrayList<Price> findPrices() {
        ArrayList<Price> prices = new ArrayList();
        
        String query = "SELECT pr FROM Price pr";
        Query qr = em.createQuery(query);
        
        List<Price> prList = qr.getResultList();
        for(Price pr : prList) {
            prices.add(pr);
        }
        return prices;
    }
    
    @Override
    public ArrayList<Category> findCategories() {
        ArrayList<Category> categories = new ArrayList();
        
        String query = "SELECT ca FROM Category ca";
        Query qr = em.createQuery(query);
        
        List<Category> caList = qr.getResultList();
        for(Category cat : caList) {
            if("Boisson".equals(cat.getType())) {
                categories.add(cat);
            }
        }
        return categories;
    }
    
    @Override
    public void create(Drink drink) {
        em.persist(drink);
    }
    
    @Override
    public void delete(Drink drink) {
        em.merge(drink);
    }
    
    @Override
    public void update(Drink drink) {
        em.merge(drink);
    }
    
    @Override
    public Drink findById(Long id) {
        Drink drink = em.find(Drink.class, id);
        
        if(drink.getDiscount() != null && (new Date()).after(drink.getDiscount().getEndDate())){
            drink.setDiscount(null);
            em.merge(drink);
        }
        
        return drink;
    }
    
    @Override
    public ArrayList<Drink> findAll(){
        ArrayList<Drink> drinks = new ArrayList();
        
        String query = "SELECT d FROM Drink d"
                + " WHERE d.active = :activeDrink";
        Query qr = em.createQuery(query);
        qr.setParameter("activeDrink", true);
        
        List<Drink> dList = qr.getResultList();
        for(Drink dr : dList) {
            drinks.add(dr);
        }
        
        return drinks;
    }
    
    @Override
    public List<Drink> findAllByRange(int firstResult, int maxResults) {
        Query q = em.createQuery("select d from Drink d where d.active = 1 order by d.name asc");
        if (firstResult >= 0) {
            q.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            q.setMaxResults(maxResults);
        }
        return q.getResultList();
    }
    
    @Override
    public int count() {
        return ((Long) em.createQuery("select COUNT(d) from Drink d").getSingleResult()).intValue();
    }
    
    @Override
    public void persist(Object object) {
        em.persist(object);
    }

}
