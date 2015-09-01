package ejb;

import entities.Category;
import entities.Combo;
import entities.Dish;
import entities.Drink;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ejbCategory implements ejbCategoryLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;
    
    @Override
    public void create(Category category) {
        em.persist(category);
    }

    @Override
    public void update(Category category) {
        em.merge(category);
    }

    @Override
    public void delete(Category category) {
        Category c = em.find(Category.class, category.getId());
        
        if("Plat".equals(c.getType())){
           String sq = "select di FROM Dish di WHERE di.category = :category";
           Query q = em.createQuery(sq);
           q.setParameter("category", category);
           
           for(Dish d : (List<Dish>)q.getResultList()){
              d.setCategory(null);
              em.merge(d);
           }
        }
        
        if("Boisson".equals(c.getType())){
            String sq = "select dr FROM Drink dr WHERE dr.category = :category";
            Query q = em.createQuery(sq);
            q.setParameter("category", category);
            for(Drink d : (List<Drink>)q.getResultList()){
                d.setCategory(null);
                em.merge(d);
            }
        }
        
        if("Dejeuner".equals(c.getType()) || "Diner".equals(c.getType())){
            String sq = "select co FROM Combo co WHERE co.category = :category";
            Query q = em.createQuery(sq);
            q.setParameter("category", category);
            for(Combo co : (List<Combo>)q.getResultList()){
                co.setCategory(null);
                em.merge(co);
            }
        }
        
        em.remove(c);
    }

    @Override
    public Category findById(Long id) {
        Category c = em.find(Category.class, id);
        return c;
    }

    @Override
    public List<Category> findAll() {
        String sq = "select c from Category c";
        Query q = em.createQuery(sq);       
        return q.getResultList();
    }
    
    @Override
    public int count(){
        return ((Long) em.createQuery("select COUNT(c) from Category c").getSingleResult()).intValue();
    }
    
    public List<Category> findAllByRange(int firstResult, int maxResults) {
        Query q = em.createQuery("select ca from Category ca ORDER BY ca.name");
        if(firstResult >= 0){
            q.setFirstResult(firstResult);
        }
        if(maxResults > 0){
            q.setMaxResults(maxResults);
        }
        
        return q.getResultList();
    }

}
