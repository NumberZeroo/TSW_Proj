package control;

import com.tswproject.tswproj.EmptyPoolException;
import com.tswproject.tswproj.Security;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.utente.*;
import model.wishlist.WishlistBean;
import model.wishlist.WishlistDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(value = "/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recupera i parametri della richiesta
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            //Password e conferma password non coincidono
            response.sendRedirect("register.jsp?error=error");
            return;
        }

        if (registerUser(username, email, password)) {
            //Utente registrato con successo
            response.sendRedirect("login.jsp");
        } else {
            //Errore nella registrazione
            response.sendRedirect("register.jsp?error=error");
        }
    }

    private boolean registerUser(String username, String email, String password) {
        UtenteBean utente = new UtenteBean();

        if (!Security.validateEmail(email)) {
            System.out.println("Email non valida");
            return false;
        }

        if (!Security.validateUsername(username)) {
            System.out.println("Username non valida");
            return false;
        }

        Optional<String> hashedPassword = Security.hashPassword(password);
        if (hashedPassword.isEmpty()) {
            System.out.println("Password non valida");
            return false;
        }

        utente.setUsername(username);
        utente.setEmail(email);
        utente.setImgPath("/test");  //todo default imgPath
        utente.setIsAdmin(false); //todo default isAdmin
        utente.setPassword(hashedPassword.get());

        try (UtenteDAO ut = new UtenteDAO()) {
            ut.doSave(utente);

            // Recupera l'ID dell'utente appena registrato
            UtenteBean registeredUser = ut.doRetrieveByUsername(username);
            long userId = registeredUser.getId();

            // Crea una nuova wishlist per l'utente
            WishlistBean wishlist = new WishlistBean();
            wishlist.setUserId(userId);

            // Salva la wishlist
            try (WishlistDAO wishlistDAO = new WishlistDAO()) {
                wishlistDAO.doSave(wishlist);
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: log
            return false;
        }
    }
}
