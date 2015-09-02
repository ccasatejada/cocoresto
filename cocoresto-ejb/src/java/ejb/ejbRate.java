package ejb;

import entities.Discount;
import entities.Tax;
import java.util.ArrayList;
import java.util.Date;
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
        Tax tax = em.find(Tax.class, id);
        return tax;
    }

    @Override
    public Discount findDiscountById(Long id) {
        Discount discount = em.find(Discount.class, id);
        return discount;
    }

    @Override
    public ArrayList<Tax> findAllTaxes() {
        ArrayList<Tax> taxes = new ArrayList();

        String query = "SELECT t FROM Tax t";
        Query qr = em.createQuery(query);

        List<Tax> tList = qr.getResultList();
        for (Tax ta : tList) {
            taxes.add(ta);
        }

        return taxes;
    }

    @Override
    public ArrayList<Discount> findAllDiscounts() {
        ArrayList<Discount> discounts = new ArrayList();

        String query = "SELECT d FROM Discount d WHERE d.endDate >= :actualDate";
        Query qr = em.createQuery(query);
        qr.setParameter("actualDate", new Date());
        List<Discount> dList = qr.getResultList();
        for (Discount di : dList) {
            discounts.add(di);
        }

        return discounts;
    }
    
    @Override
    public List<Tax> findAllTaxesByRange(int firstResult, int maxResults) {
        Query q = em.createQuery("select t from Tax t order by t.rate asc");
        if (firstResult >= 0) {
            q.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            q.setMaxResults(maxResults);
        }
        return q.getResultList();
    }
    
    @Override
    public List<Discount> findAllDiscountsByRange(int firstResult, int maxResults) {
        Query q = em.createQuery("select d from Discount d where d.endDate >= :actualDate order by d.endDate desc");
        q.setParameter("actualDate", new Date());
        if (firstResult >= 0) {
            q.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            q.setMaxResults(maxResults);
        }
        return q.getResultList();
    }

    @Override
    public Discount findByDates(Double rate, Date beginDate, Date endDate) {
        String sq = "SELECT d FROM Discount d WHERE d.rate = :rate AND d.beginDate = :beginDate AND d.endDate = :endDate";
        Query q = em.createQuery(sq);
        q.setParameter("rate", rate);
        q.setParameter("beginDate", beginDate);
        q.setParameter("endDate", endDate);
        
        Discount d = (Discount)q.getSingleResult();
        return d;
    }
    
    @Override
    public int taxCount() {
        return ((Long) em.createQuery("select COUNT(t) from Tax t").getSingleResult()).intValue();
    }
    
    @Override
    public int discountCount() {
        return ((Long) em.createQuery("select COUNT(d) from Discount d").getSingleResult()).intValue();
    }

    @Override
    public void persist(Object object) {
        em.persist(object);
    }

}
