/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.Category;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderedProduct;
import entity.OrderedProductPK;
import entity.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.PasswordAuthentication;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author dima
 */
@Stateless
public class DataBaseManager {
    
    @PersistenceContext(unitName = "com.mycompany_Shop02_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public Customer addCustomer(String name, String email, String lieferadresse_str, String lieferadresse_stadt, String rechnungsadresse_str, String rechnungsadresse_stadt, String passwort) {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setLieferadresseStr(lieferadresse_str);
        customer.setLieferadresseStadt(lieferadresse_stadt);
        customer.setRechnungsadresseStr(rechnungsadresse_str);
        customer.setRechnungsadresseStadt(rechnungsadresse_stadt);
        customer.setPasswort(passwort);
        try{
            em.persist(customer);
        } catch (ConstraintViolationException e){
            Set set = e.getConstraintViolations();
        }
        return customer;
    }

    public int placeOrder(Customer user, ShoppingCart cart) {
        CustomerOrder order = addOrder(user, cart);
        addOrderedItems(order, cart);
        return order.getId();
    }
    
    private CustomerOrder addOrder(Customer customer, ShoppingCart cart) {


        // set up customer order
        CustomerOrder order = new CustomerOrder();
        order.setCustomerId(customer);
        cart.calculateTotal();
        double test = cart.getTotal() + 10.0;
        order.setAmount(BigDecimal.valueOf(test));

        // create confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmationNumber(i);
        order.setStatus("ordered");
        try{
            em.persist(order);
        } catch (ConstraintViolationException e){
            Set set = e.getConstraintViolations();
            int test2 = 0;
        }
        return order;
    }

    private void addOrderedItems(CustomerOrder order, ShoppingCart cart) {
        
        try{
            em.flush();
        } catch (ConstraintViolationException e){
            Set set = e.getConstraintViolations();
            int test = 0;
        }
        
        List<ShoppingCartItem> items = cart.getItems();

        // iterate through shopping cart and create OrderedProducts
        for (ShoppingCartItem scItem : items) {

            int productId = scItem.getProduct().getId();

            // set up primary key object
            OrderedProductPK orderedProductPK = new OrderedProductPK();
            orderedProductPK.setCustomerOrderId(order.getId());
            orderedProductPK.setProductId(productId);

            // create ordered item using PK object
            OrderedProduct orderedItem = new OrderedProduct(orderedProductPK);

            // set quantity
            orderedItem.setQuantity(scItem.getQuantity());
            
            em.persist(orderedItem);
        }
    }
    
    public void changeProduct(Product product, String column, String value) {
        Product changeProduct = em.find(Product.class, product.getId());
        
        if(column.equals("name")){
            changeProduct.setName(value);
        } else if(column.equals("price")){
            
            BigDecimal dec = new BigDecimal(Double.parseDouble(value));
            dec.setScale(2, RoundingMode.CEILING);       
            changeProduct.setPrice(dec);
            
        } else if(column.equals("description")){
            changeProduct.setDescription(value);
        }

        em.merge(changeProduct);
        em.flush();
    }
    
    public void deleteProduct(Product product) {
        Product changeProduct = em.find(Product.class, product.getId());
        
        em.remove(changeProduct);
    }
    
    public void newProduct(String name, String price, String description, List<Category> categoryList) {
        BigDecimal dec = new BigDecimal(Double.parseDouble(price));
        dec.setScale(2, RoundingMode.CEILING);
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(dec);
        newProduct.setDescription(description);
        newProduct.setCategoryCollection(categoryList);
        

        try{
            em.persist(newProduct);
        } catch (ConstraintViolationException e){
            Set set = e.getConstraintViolations();
        }
    }
    
    public void changeOrderStatus(CustomerOrder order, Customer customer) {
        CustomerOrder changeOrder = em.find(CustomerOrder.class, order.getId());
        
        if(changeOrder.getStatus().equals("ordered")){
            changeOrder.setStatus("sent");

            em.merge(changeOrder);
            em.flush();
            
            final String username = "danewitz.shop@gmail.com";
            final String password = "pass";

            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
              new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
              });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("danewitz.shop@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(customer.getEmail()));
                message.setSubject("Testing Subject");
                message.setText("Sehr geehrter Kunde,"
                    + "\n\n Ihre Bestellung wurde versandt!");

                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            
        }
        
    }
    
}
