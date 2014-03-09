/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.CustomerOrder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author dima
 */
@Stateless
public class CustomerOrderFacade extends AbstractFacade<CustomerOrder> {
    @PersistenceContext(unitName = "com.mycompany_Shop02_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerOrderFacade() {
        super(CustomerOrder.class);
    }
    
    public List<CustomerOrder> all() { 
        TypedQuery<CustomerOrder> query = em.createNamedQuery("CustomerOrder.findAll", CustomerOrder.class); 
        return query.getResultList(); 
    }
    
    public CustomerOrder findOrderById(int id) { 
        TypedQuery<CustomerOrder> query = em.createNamedQuery("CustomerOrder.findById", CustomerOrder.class); 
        query.setParameter("id", id); 
        return query.getSingleResult();
    }
}
