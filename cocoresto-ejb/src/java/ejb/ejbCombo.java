package ejb;

import entities.Category;
import entities.Combo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class ejbCombo implements ejbComboLocal {

    @PersistenceContext(unitName = "cocoresto-ejbPU")
    private EntityManager em;

    @Override
    public void create(Combo combo) {
        em.persist(combo);
    }

    @Override
    public void update(Combo combo) {
        em.persist(combo);
    }

    @Override
    public void delete(Combo combo) {
        Combo c = em.find(Combo.class, combo.getId());
        em.remove(c);
    }

    @Override
    public Combo findById(Long id) {
        Combo c = em.find(Combo.class, id);
        return c;
    }

    @Override
    public List<Combo> findAll() {
        String sq = "select c from Combo c";
        Query q = em.createQuery(sq);
        return q.getResultList();
    }

    @Override
    public int count() {
        return ((Long) em.createQuery("select COUNT(c) from Combo c").getSingleResult()).intValue();
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
    @Override
    public List<Category> findCategories(){
        List<Category> categories = new ArrayList();
        String sq = "SELECT c FROM Category c";
        Query q = em.createQuery(sq);
        for(Category cat : (List<Category>)q.getResultList()){
            if("Dejeuner".equals(cat.getType())){
                categories.add(cat);
            }
            if("Diner".equals(cat.getType())){
                categories.add(cat);
            }
            if("Brunch".equals(cat.getType())){
                categories.add(cat);
            }
        }
        return categories;
    }
    
    @Override
    public List<Combo> findAllByRange(int firstResult, int maxResults){
        Query q = em.createQuery("select co from Combo co order by co.name");
        if(firstResult >= 0){
            q.setFirstResult(firstResult);
        }
        if(maxResults > 0){
            q.setMaxResults(maxResults);
        }
        
        return q.getResultList();
    }

}
