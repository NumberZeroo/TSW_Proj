package com.tswproject.tswproj;

import jakarta.servlet.http.HttpSession;
import model.cartItem.CartItemDAO;
import model.utente.UtenteBean;
import model.utente.UtenteDAO;

import java.sql.SQLException;
import java.util.*;

/**
 * La SessionFacade prende come parametro del costruttore una request e gestisce la sessione ritornata
 * E' utile per avere un'interfaccia semplice ed unificata per fare operazioni complesse.
 * Esempio: aggiungere un elemento al carrello implica aggiungere l'id ad una lista contenuta in un campo della sessione.
 * Questo se la lista è inizializzata, altrimenti va creata.
 * Tutte queste operazioni possono essere fatte dalla facade che esporrà un metodo semplice per aggiungere elementi alla lista.
 */


public class SessionFacade {
    private final String CART_SESSION_ATTRIBUTE_NAME = "cartProducts";
    private final String USERID_SESSION_ATTRIBUTE_NAME = "userId";
    private final String USERNAME_SESSION_ATTRIBUTE_NAME = "username";
    private final String CARTID_SESSION_ATTRIBUTE_NAME = "cartId";
    private final String ISADMIN_SESSION_ATTRIBUTE_NAME = "isAdmin";


    private HttpSession session;

    public SessionFacade(HttpSession session) {
        this.session = session;
    }

    /**
     * Inizializza il carrello interrogando il database per vedere se l'utente ha aggiunto qualcosa in precedenza
     * In caso contrario ritorna una mappa vuota
     * @return mappa key: idProdotto, value: quantitò
     */
    private Map<Long, Integer> initCart() throws SQLException {
        Map<Long, Integer> products = new HashMap<>();
        if (this.session.getAttribute(CARTID_SESSION_ATTRIBUTE_NAME) == null)
            return products;

        try(CartItemDAO cartItemDAO = new CartItemDAO()) {
            products = cartItemDAO.getCartItems((Long)this.session.getAttribute(CARTID_SESSION_ATTRIBUTE_NAME));
        }
        return products;
    }

    public boolean isLoggedIn() {
        return this.session != null && this.session.getAttribute(USERID_SESSION_ATTRIBUTE_NAME) != null;
    }

    /**
     * Imposta nella sessione i parametri di id utente e carrello, username e oggetto carrello
     * Se prima di fare login erano stati aggiunti dei prodotti al carrello questi vengono mantenuti dopo il login
     * Viene fatta un'interrogazione al server per ottene il carrello stored in una sessione passata
     * @param user  Bean dell'utente da loggare
     * @throws SQLException
     */
    synchronized public void login(UtenteBean user) throws SQLException {
        if (this.isLoggedIn()) return;

        this.session.setAttribute(USERID_SESSION_ATTRIBUTE_NAME, user.getId());
        this.session.setAttribute(USERNAME_SESSION_ATTRIBUTE_NAME, user.getUsername());
        this.session.setAttribute(ISADMIN_SESSION_ATTRIBUTE_NAME, user.getIsAdmin());
        try(UtenteDAO utenteDAO = new UtenteDAO()) {
            long cartId = utenteDAO.getCartId(user.getId());
            this.session.setAttribute(CARTID_SESSION_ATTRIBUTE_NAME, cartId);
        }
        Map<Long, Integer> storedItems = initCart();
        Map<Long, Integer> sessionItems = (Map<Long, Integer>) this.session.getAttribute(CART_SESSION_ATTRIBUTE_NAME);
        if (sessionItems == null){
            this.session.setAttribute(CART_SESSION_ATTRIBUTE_NAME, storedItems);
            return;
        }
        // Aggiungi prodotti della sessione nel db
        sessionItems.forEach((k, v) -> {
            try {
                this.storeItemInDb(k, v);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // Merge di prodotti della sessione con prodotti del db
        Map<Long, Integer> finalSessionItems = sessionItems; // Perché gli stream vogliono "effective final variables"
        storedItems.forEach((k, v) -> finalSessionItems.merge(k, v, Integer::sum));

        this.session.setAttribute(CART_SESSION_ATTRIBUTE_NAME, finalSessionItems);


    }

    // Ogni elemento della lista è un id di prodotto
    public Map<Long, Integer> getCartProducts() {
        Map<Long, Integer> products = (Map<Long, Integer>) this.session.getAttribute(CART_SESSION_ATTRIBUTE_NAME);
        if (products == null) {
            return new HashMap<>();
        }
        return products;
    }

    synchronized public void addCartProduct(long productId, int quantity) throws SQLException {
        Map<Long, Integer> products = (Map<Long, Integer>) this.session.getAttribute(CART_SESSION_ATTRIBUTE_NAME);
        if (products == null) {
            products = new HashMap<>();
        }
        products.merge(productId, quantity, Integer::sum); // Se non c'è aggiungi, altrimenti incrementa quantità
        this.session.setAttribute(CART_SESSION_ATTRIBUTE_NAME, products);

        this.storeItemInDb(productId, quantity);
    }

    private void storeItemInDb(long productId, int quantity) throws SQLException {
        if (this.isLoggedIn()){
            try(CartItemDAO cartItemDAO = new CartItemDAO()){
                cartItemDAO.addProduct(productId, (Long) this.session.getAttribute(CARTID_SESSION_ATTRIBUTE_NAME), quantity);
            }
        }
    }

    synchronized public void removeCartProduct(long productId, int quantity) throws SQLException {
        Map<Long, Integer> products = (Map<Long, Integer>) this.session.getAttribute(CART_SESSION_ATTRIBUTE_NAME);
        if (products == null) return;
        // Se nella mappa products esiste un elemento con chiave productId decrementa il valore di quantity
        if (products.get(productId) != null){
            int oldQuantity = products.get(productId);
            if (quantity >= oldQuantity) { // Togli il prodotto dal carrello
                products.remove(productId);
                if (this.isLoggedIn()){
                    try(CartItemDAO cartItemDAO = new CartItemDAO()){
                        cartItemDAO.removeProduct(productId, (Long)this.session.getAttribute(CARTID_SESSION_ATTRIBUTE_NAME));
                    }
                }
            } else { // Togli una certa quantità
                products.put(productId, products.get(productId) - quantity);
                if (this.isLoggedIn()){
                    try(CartItemDAO cartItemDAO = new CartItemDAO()){
                        cartItemDAO.removeProduct(productId, (Long)this.session.getAttribute(CARTID_SESSION_ATTRIBUTE_NAME), quantity);
                    }
                }
            }
        }
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable((String)this.session.getAttribute(USERNAME_SESSION_ATTRIBUTE_NAME));
    }

    public boolean getIsAdmin() {
        if (this.session.getAttribute(ISADMIN_SESSION_ATTRIBUTE_NAME) == null) return false;
        return (boolean) this.session.getAttribute(ISADMIN_SESSION_ATTRIBUTE_NAME);
    }

    public void invalidate(){
        this.session.invalidate();
    }
}