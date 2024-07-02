package control;

import model.utente.UtenteBean;
import model.utente.UtenteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "changeUserRoleServlet", urlPatterns = {"/changeUserRoleServlet"})
public class ChangeUserRoleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long userId = Long.parseLong(request.getParameter("userId"));
        boolean makeAdmin = Boolean.parseBoolean(request.getParameter("makeAdmin"));

        try (UtenteDAO utenteDAO = new UtenteDAO()) {
            UtenteBean utente = utenteDAO.doRetrieveByKey(userId);
            if (utente != null) {
                utente.setIsAdmin(makeAdmin);
                utenteDAO.doUpdate(utente);
            }
        } catch (SQLException e) {
            throw new ServletException("Errore durante il cambio del ruolo dell'utente", e);
        }

        response.sendRedirect("adminPage.jsp");
    }
}
