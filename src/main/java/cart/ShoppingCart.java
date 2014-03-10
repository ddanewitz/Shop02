/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cart;

import entity.Product;
import java.util.*;

/**
 *
 * @author tgiunipero
 */
public class ShoppingCart {

    List<ShoppingCartItem> items;
    int numberOfItems;
    double total;

    public ShoppingCart() {
        items = new ArrayList<ShoppingCartItem>();
        numberOfItems = 0;
        total = 0;
    }

 
    public synchronized void addItem(Product product) {

        boolean newItem = true;

        for (ShoppingCartItem scItem : items) {

            if (scItem.getProduct().getId() == product.getId()) {

                newItem = false;
                scItem.incrementQuantity();
            }
        }

        if (newItem) {
            ShoppingCartItem scItem = new ShoppingCartItem(product);
            items.add(scItem);
        }
    }

    /*Updaten der Menge eines Produkts im Einkaufskorb. 
    Bei Eingabe der Menge null wird das Produkt aus dem Warenkorb gelöscht*/
    
    public synchronized void update(Product product, String quantity) {

        short qty = -1;

        // cast quantity as short
        qty = Short.parseShort(quantity);

        if (qty >= 0) {

            ShoppingCartItem item = null;

            for (ShoppingCartItem scItem : items) {

                if (scItem.getProduct().getId() == product.getId()) {

                    if (qty != 0) {
                        // neuen Wert für Quantity setzen
                        scItem.setQuantity(qty);
                    } else {
                        // bei quantity item speichern und break;
                        item = scItem;
                        break;
                    }
                }
            }

            if (item != null) {
                items.remove(item);
            }
        }
    }


    public synchronized List<ShoppingCartItem> getItems() {

        return items;
    }


    public synchronized int getNumberOfItems() {

        numberOfItems = 0;

        for (ShoppingCartItem scItem : items) {

            numberOfItems += scItem.getQuantity();
        }

        return numberOfItems;
    }


    public synchronized double getSubtotal() {

        double amount = 0;

        for (ShoppingCartItem scItem : items) {

            Product product = (Product) scItem.getProduct();
            amount += (scItem.getQuantity() * product.getPrice().doubleValue());
        }

        return amount;
    }

    /*Berechnet den Gesamtpreis*/
    public synchronized void calculateTotal(/*String surcharge*/) {

        double amount = 0;
        amount = this.getSubtotal();
        total = amount;
    }


    public synchronized double getTotal() {

        return total;
    }

    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
        total = 0;
    }

}