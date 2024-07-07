package control;

import com.tswproject.tswproj.Security;
import com.tswproject.tswproj.SessionFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.utente.UtenteBean;
import model.utente.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/editUserServlet")
public class EditUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String newUsername = request.getParameter("newUsername");
        String newEmail = request.getParameter("newEmail");

        SessionFacade sessionFacade = new SessionFacade(request.getSession());
        long userId = sessionFacade.getUserId();

        try (UtenteDAO userDAO = new UtenteDAO()) {
            UtenteBean utente = userDAO.doRetrieveByKey(userId);

            if ("editEmail".equals(action)) {
                if (!Security.validateEmail(newEmail)) {
                    throw new RuntimeException("Email non valida");
                } else {
                    utente.setEmail(newEmail);
                    userDAO.doUpdate(utente);
                }
            } else if ("editUsername".equals(action)) {
                if (!Security.validateUsername(newUsername)) {
                    throw new RuntimeException("Username non valido");
                } else {
                    utente.setUsername(newUsername);
                    userDAO.doUpdate(utente);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Errore durante l'aggiornamento delle informazioni dell'utente", e);
        }

        // Reindirizza l'utente alla pagina del profilo
        response.sendRedirect("profile.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Non dovrebbe essere possibile accedere a questa servlet tramite GET, quindi reindirizza l'utente alla pagina di login
        response.sendRedirect("login.jsp");
    }
}