package controller;

import cart.ShoppingCart;
import encryption.UtilEncryption;
import entity.Admin;
import entity.Category;
import entity.Customer;
import entity.CustomerOrder;
import entity.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.AdminFacade;
import session.CategoryFacade;
import session.CustomerFacade;
import session.CustomerOrderFacade;
import session.DataBaseManager;
import session.ProductFacade;

/**
 *
 * @author dima
 */
@WebServlet(name = "ControllerServlet",
        loadOnStartup = 1,
        urlPatterns = { "/category",
                        "/searchProduct",
                        "/productInfo",
                        "/addToCart",
                        "/updateCart",
                        "/login",
                        "/register",
                        "/order",
                        "/summarty",
                        "/confirmOrder",
                        "/shoppingCart",
                        "/adminlogin",
                        "/adminindex",
                        "/adminproductedit",
                        "/adminorderedit",
                        "/changeProduct",
                        "/deleteProduct",
                        "/newProduct",
                        "/changeStatus"
        })
public class ControllerServlet extends HttpServlet {
    
    @EJB
    private CategoryFacade categoryFacade;
    
    @EJB
    private ProductFacade productFacade;
    
    @EJB
    private CustomerFacade customerFacade;
    
    @EJB
    private CustomerOrderFacade customerOrderFacade;
    
    @EJB
    private DataBaseManager dataBaseManager;
    
    @EJB
    private AdminFacade adminFacade;

    @Override
    public void init() throws ServletException {

        getServletContext().setAttribute("categories", categoryFacade.findAll());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String userPath = request.getServletPath();

        if (userPath.equals("/category")) {
            Category selectedCategory;
            Collection<Product> categoryProducts;
            
            // categoryId holen
            int categoryId = Integer.parseInt(request.getQueryString());
            selectedCategory = categoryFacade.find((short)categoryId);
            categoryProducts = selectedCategory.getProductCollection();
            request.setAttribute("shownProducts", categoryProducts);
            
            userPath = "/index";

        } else if (userPath.equals("/searchProduct")) {
            Collection<Product> searchProducts;
            String searchString = request.getParameter("searchString");
            searchProducts = productFacade.search(searchString);
            request.setAttribute("shownProducts", searchProducts);
            
            userPath = "/index";
            
        } else if (userPath.equals("/shoppingCart")) {
            
            userPath = "/WEB-INF/shoppingCart";  
            
        } else if (userPath.equals("/login")) {
            
            userPath = "/WEB-INF/login";  
            
        } else if (userPath.equals("/register")) {
            
            userPath = "/WEB-INF/register"; 
            

            
        } else if (userPath.equals("/productInfo")) {
            Product selectedProduct;
            selectedProduct = productFacade.find(Integer.parseInt(request.getQueryString()));
            request.setAttribute("selectedProduct", selectedProduct);
            userPath = "/WEB-INF/productInfo";
            
        }

        String url = userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        Customer user = (Customer) session.getAttribute("user");
        Admin admin = (Admin) session.getAttribute("admin");

        if (userPath.equals("/addToCart")) {
            if(cart == null){
                cart = new ShoppingCart();
                session.setAttribute("cart", cart);
            }
            
            String productId = request.getParameter("productId");

            if (!productId.isEmpty()) {

                Product product = productFacade.find(Integer.parseInt(productId));
                cart.addItem(product);
                request.setAttribute("selectedProduct", product);
            }

            userPath = "/WEB-INF/productInfo";
        } 
        
        else if (userPath.equals("/updateCart")) {
            String productId = request.getParameter("productId");
            String quantity = request.getParameter("quantity");

            Product product = productFacade.find(Integer.parseInt(productId));
            cart.update(product, quantity);
            
            userPath = "/WEB-INF/shoppingCart";
        }
        
        //Loginseite. Es wird bei erfolgreicher Suche der Email-Adresse ein Customer-Objekt erstellt.
        else if (userPath.equals("/login")) {
            String userEmail = request.getParameter("email");
            String passwort = request.getParameter("password");
            try{
                user = customerFacade.findUser(userEmail);
            } catch (EJBException e){
                String message = "Emailadresse nicht bekannt!";
                request.setAttribute("message", message);
                userPath = "/WEB-INF/login";
            }
            
            //Passwortüberpfürung
            if(user != null){
                String userPasswort = user.getPasswort();
                if(UtilEncryption.encryptPassword(passwort).equals(userPasswort)){
                    session.setAttribute("user", user);
                    userPath = "/index";
                } else {
                    String message = "Passwort falsch!";
                    request.setAttribute("message", message);
                    userPath = "/WEB-INF/login";
                }
            
            }
            
        }
        
        //Registrierung. sameAddress dient bei gleicher Liefer- und Rechnungsadressen zur schnellen Eingabe
        else if (userPath.equals("/register")) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String lieferadresse_str = request.getParameter("lieferadresse_str");
            String lieferadresse_stadt = request.getParameter("lieferadresse_stadt");
            
            String sameAddress = request.getParameter("sameAddress");
            
            String rechnungsadresse_str;
            String rechnungsadresse_stadt;
            
            String passwort1 = request.getParameter("password1");
            String passwort2 = request.getParameter("password2");
            
            if(sameAddress != null){
                rechnungsadresse_str = request.getParameter("lieferadresse_str");
                rechnungsadresse_stadt = request.getParameter("lieferadresse_stadt");
            } else {
                rechnungsadresse_str = request.getParameter("rechnungsadresse_str");
                rechnungsadresse_stadt = request.getParameter("rechnungsadresse_stadt");
            }
            
            Customer testUser = null;
            try{
                testUser = customerFacade.findUser(email);
            } catch (EJBException e){

            }

            if (testUser != null){
                
                String message = "Email bereits vergeben!";
                request.setAttribute("message", message);
                userPath = "/WEB-INF/register";
            } else if (!passwort1.equals(passwort2)){
                String message = "Passwort stimmt nicht ueberein!";
                request.setAttribute("message", message);
                userPath = "/WEB-INF/register";
            } else if (name.equals("") || email.equals("") || lieferadresse_str.equals("") || lieferadresse_stadt.equals("") || rechnungsadresse_str.equals("") || rechnungsadresse_stadt.equals("")){
                String message = "Pflichtfelder nicht ausgefüllt!";
                request.setAttribute("message", message);
                userPath = "/WEB-INF/register";
            } else {
                dataBaseManager.addCustomer(name, email, lieferadresse_str, lieferadresse_stadt, rechnungsadresse_str, rechnungsadresse_stadt, UtilEncryption.encryptPassword(passwort1));
                user = customerFacade.findUser(email);
                session.setAttribute("user", user);
                userPath = "/index";
            }

        }
        
        //Bei drücken des Bestellbuttons überprüfen, ob der User eingeloggt ist
        else if (userPath.equals("/order")) {
            
            if(session.getAttribute("user") != null){
                cart.calculateTotal();
                userPath = "/WEB-INF/summarySite";
            } else {
                userPath = "/WEB-INF/login";
            }

        }
        
        else if (userPath.equals("/confirmOrder")) {
            
            if(session.getAttribute("user") != null){
                int orderId = dataBaseManager.placeOrder(user, cart);
                cart.clear();
                session.removeAttribute("cart");
                userPath = "/WEB-INF/thankyou";
            } else {
                userPath = "/WEB-INF/login";
            }

        }
        
        else if (userPath.equals("/adminlogin")) {
            String username = request.getParameter("username");
            String passwort = request.getParameter("password");
            try{
                admin = adminFacade.findUser(username);
            } catch (EJBException e){
                String message = "Benutzername nicht bekannt!";
                request.setAttribute("message", message);
            }
            
            if(admin != null){
                String adminPasswort = admin.getPassword();
                String test = UtilEncryption.encryptPassword(passwort);
                if(UtilEncryption.encryptPassword(passwort).equals(adminPasswort)){
                    session.setAttribute("admin", admin);
                    
                    
                } else {
                    String message = "Passwort falsch!";
                    request.setAttribute("message", message);
                }
            
            }
            
            userPath = "/adminlogin";
        }
        
        else if (userPath.equals("/adminindex")) {
            if(session.getAttribute("admin") != null){
                
                userPath = "/WEB-INF/admin/adminindex";
            }
            
            else userPath = "/index";
        }
        
        else if (userPath.equals("/adminproductedit")) {
            if(session.getAttribute("admin") != null){
                Collection<Product> allProducts;
                allProducts = productFacade.all();
                request.setAttribute("allProducts", allProducts);
                
                userPath = "/WEB-INF/admin/adminproductedit";
            }
            
            else userPath = "/index";
        }
        
        //Bestellungen bearbeiten
        else if (userPath.equals("/adminorderedit")) {
            if(session.getAttribute("admin") != null){
                Collection<CustomerOrder> allOrders;
                allOrders = customerOrderFacade.all();
                request.setAttribute("allOrders", allOrders);
                
                userPath = "/WEB-INF/admin/adminorderedit";
            }
            
            else userPath = "/index";
        }
        
        //Bestellstatus ändern
        else if (userPath.equals("/changeStatus")) {
            if(session.getAttribute("admin") != null){
                
                
                CustomerOrder order = customerOrderFacade.findOrderById(Integer.parseInt(request.getParameter("orderId")));
                Customer customer = customerFacade.find(order.getCustomerId());
                
                dataBaseManager.changeOrderStatus(order, customer);
                
                Collection<CustomerOrder> allOrders;
                allOrders = customerOrderFacade.all();
                request.setAttribute("allOrders", allOrders);
                
                userPath = "/WEB-INF/admin/adminorderedit";
            }
            
            else userPath = "/index";
        }
        
        else if (userPath.equals("/changeProduct")) {
            if(session.getAttribute("admin") != null){
                Product product = productFacade.findProductById(Integer.parseInt(request.getParameter("productId")));
                String column = request.getParameter("column");
                String value = request.getParameter("value");
                dataBaseManager.changeProduct(product, column, value);
                
                Collection<Product> allProducts;
                allProducts = productFacade.all();
                request.setAttribute("allProducts", allProducts);
                
                userPath = "/WEB-INF/admin/adminproductedit";
            }
            
            else userPath = "/index";
        }
        
        else if (userPath.equals("/deleteProduct")) {
            if(session.getAttribute("admin") != null){
                Product product = productFacade.findProductById(Integer.parseInt(request.getParameter("productId")));
                dataBaseManager.deleteProduct(product);
                
                Collection<Product> allProducts;
                allProducts = productFacade.all();
                request.setAttribute("allProducts", allProducts);
                
                userPath = "/WEB-INF/admin/adminproductedit";
            }
            
            else userPath = "/index";
        }
        
        else if (userPath.equals("/newProduct")) {
            if(session.getAttribute("admin") != null){
                String name = request.getParameter("name");
                String price = request.getParameter("price");
                String description = request.getParameter("description");
                
                /*Product product = productFacade.findProductById(69);
                dataBaseManager.newCategory(product);*/
                List<Category> categoryList = new ArrayList<Category>();
                for(Category c : categoryFacade.findAll()){
                    String checkBoxName = "categorySelected" + c.getId();
                    String test = request.getParameter("categorySelected1");
                    String checkedCategory = request.getParameter(checkBoxName);
                    if(checkedCategory != null){
                        categoryList.add(c);
                    }
                }
                
                if(isNumeric(price)){
                    dataBaseManager.newProduct(name, price, description, categoryList);
                    userPath = "/WEB-INF/admin/adminproductedit";
                }
                else{
                    String message = "Preisangabe fehlerhaft!";
                    request.setAttribute("message", message);
                    userPath = "/WEB-INF/admin/adminproductedit";
                }
                
                Collection<Product> allProducts;
                allProducts = productFacade.all();
                request.setAttribute("allProducts", allProducts);
                
            }
            
            else userPath = "/index";
        }
        
        //Zusammensetzen der URL
        String url = userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //Methode zur Prüfung, ob Eingabe eine Zahl ist
    public static boolean isNumeric(String str)  
    {  
        try  
        {  
            double d = Double.parseDouble(str);  
        }  
        catch(NumberFormatException nfe)  
        {  
            return false;  
        }  
        return true;  
    }

}