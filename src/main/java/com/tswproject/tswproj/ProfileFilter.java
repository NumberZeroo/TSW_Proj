package com.tswproject.tswproj;

import jakarta.servlet.Filter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter(filterName = "ProfileFilter", urlPatterns = {"/profile.jsp"})
public class ProfileFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
        // Inizializzazione del filtro se necessario
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        SessionFacade sessionFacade = new SessionFacade(session);

        // Controlla se l'utente è loggato
        if(sessionFacade.isLoggedIn()) {
            // Controlla se l'utente è un amministratore
            if(sessionFacade.getIsAdmin()) {
                // Reindirizza alla pagina dell'amministratore
                response.sendRedirect(request.getContextPath() + "/adminPage.jsp");
            } else {
                // Reindirizza alla pagina del profilo dell'utente
                chain.doFilter(req, res);
            }
        } else {
            // Reindirizza alla pagina di login se l'utente non è loggato
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    public void destroy() {
        // Pulizia del filtro se necessario
    }
}
