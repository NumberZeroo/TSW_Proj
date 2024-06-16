package com.tswproject.tswproj;

import jakarta.servlet.http.HttpSession;
import model.utente.UtenteBean;

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
    private final String ISADMIN_SESSION_ATTRIBUTE_NAME = "isAdmin";

    private HttpSession session;

    public SessionFacade(HttpSession session) {
        this.session = session;
    }

    private Map<Long, Long> loadCartProducts() {
        // TODO: prendi la sessione da DB se c'è e aggiungili alla sessione
        return (Map<Long, Long>) this.session.getAttribute(CART_SESSION_ATTRIBUTE_NAME); // Roba a caso
    }

    public boolean isLoggedIn() {
        return this.session != null && this.session.getAttribute(USERID_SESSION_ATTRIBUTE_NAME) != null;
    }

    public void login(UtenteBean user) {
        this.session.setAttribute(USERID_SESSION_ATTRIBUTE_NAME, user.getId());
        this.session.setAttribute(USERNAME_SESSION_ATTRIBUTE_NAME, user.getUsername());
        this.session.setAttribute(ISADMIN_SESSION_ATTRIBUTE_NAME, user.getIsAdmin());
    }

    // Ogni elemento della lista è un id di prodotto
    public Map<Long, Long> getCartProducts() {
        Map<Long, Long> products = (Map<Long, Long>) this.session.getAttribute(CART_SESSION_ATTRIBUTE_NAME);
        if (products == null) {
            return new HashMap<>();
        }
        return products;
    }

    public void addCartProduct(long productId) {
        Map<Long, Long> products = (Map<Long, Long>) this.session.getAttribute(CART_SESSION_ATTRIBUTE_NAME);
        if (products == null) {
            products = new HashMap<>();
        }
        products.merge(productId, 1L, Long::sum); // Se non c'è aggiungi, altrimenti incrementa quantità
        this.session.setAttribute(CART_SESSION_ATTRIBUTE_NAME, products);
    }

    public void removeCartProduct(long productId) {
        Map<Long, Long> products = (Map<Long, Long>) this.session.getAttribute(CART_SESSION_ATTRIBUTE_NAME);
        if (products != null) {
            products.remove(productId);
        }
        this.session.setAttribute(CART_SESSION_ATTRIBUTE_NAME, products);
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable((String)this.session.getAttribute(USERNAME_SESSION_ATTRIBUTE_NAME));
    }

    public boolean getIsAdmin() {
        return (boolean) this.session.getAttribute(ISADMIN_SESSION_ATTRIBUTE_NAME);
    }

    public void invalidate(){
        // TODO: commit verso il DB
        this.session.invalidate();
    }
}