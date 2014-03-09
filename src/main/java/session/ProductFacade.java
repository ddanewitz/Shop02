/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.Customer;
import entity.Product;
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
public class ProductFacade extends AbstractFacade<Product> {
    @PersistenceContext(unitName = "com.mycompany_Shop02_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }
    
    public List<Product> search(String userName) { 
        TypedQuery<Product> query = em.createNamedQuery("Product.findBySubstringName", Product.class); 
        query.setParameter("name", "%" + userName + "%"); 
        return query.getResultList(); 
    }
    
    public List<Product> all() { 
        TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class); 
        return query.getResultList(); 
    }
    
    public Product findProductById(int id) { 
        TypedQuery<Product> query = em.createNamedQuery("Product.findById", Product.class); 
        query.setParameter("id", id); 
        return query.getSingleResult();
    }

    
}
