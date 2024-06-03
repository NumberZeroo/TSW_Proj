package com.tswproject.tswproj;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * La SessionFacade prende come parametro del costruttore una request e gestisce la sessione ritornata
 * E' utile per avere un'interfaccia semplice ed unificata per fare operazioni complesse.
 * Esempio: aggiungere un elemento al carrello implica aggiungere l'id ad una lista contenuta in un campo della sessione.
 * Questo se la lista è inizializzata, altrimenti va creata.
 * Tutte queste operazioni possono essere fatte dalla facade che esporrà un metodo semplice per aggiungere elementi alla lista.
 */


public class SessionFacade {
    private HttpSession session;

    public SessionFacade(HttpServletRequest req) {
        this.session = req.getSession();
    }

    public boolean isLoggedIn() {
        return this.session != null && this.session.getAttribute("user_id") != null;
    }

    // Ogni elemento della lista è un id di prodotto
    public List<Integer> getKartProducts() {
        List<Integer> kartProducts = (List<Integer>) this.session.getAttribute("kart_products");
        if (kartProducts == null || kartProducts.isEmpty())
            return new ArrayList<>();
        return  kartProducts;
    }

    public void addKartProduct(int productId) {
        if (session.getAttribute("kart_products") == null) {
            session.setAttribute("kart_products", new ArrayList<Integer>());
        }

        ((List<Integer>)session.getAttribute("kart_products")).add(productId);
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable((String)this.session.getAttribute("username"));
    }

    public void invalidate(){
        this.session.invalidate();
    }

}