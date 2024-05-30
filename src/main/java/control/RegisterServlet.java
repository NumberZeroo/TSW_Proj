package control;

import com.tswproject.tswproj.EmptyPoolException;
import com.tswproject.tswproj.Security;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.utente.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.*;

@WebServlet(value = "/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupera i parametri della richiesta
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(registerUser(username, email, password)) {
            //Utente registrato con successo
            response.sendRedirect("login.jsp");
        } else {
            //Errore nella registrazione
            response.sendRedirect("register.jsp");
        }
    }

    private boolean registerUser(String username, String email, String password){
        UtenteBean utente = new UtenteBean();
        
        if (!Security.validateEmail(email)){
            System.out.println("Email non valida");
            return false;
        }

        if (!Security.validateUsername(username)){
            System.out.println("Username non valida");
            return false;
        }

        Optional<String> hashedPassword = Security.hashPassword(password);
        if (hashedPassword.isEmpty()) {
            System.out.println("Password non valida"); // TODO: client side error message
            return false;
        }

        //username, email, imgPath, isAdmin, password
        utente.setUsername(username);
        utente.setEmail(email);
        utente.setImgPath("/test");  //todo default imgPath
        utente.setIsAdmin(0); //todo default isAdmin
        utente.setPassword(hashedPassword.get());

        try(UtenteDAO ut = new UtenteDAO()){
            ut.doSave(utente);
            return true;
        } catch (SQLException | EmptyPoolException e) {
            e.printStackTrace(); // TODO: log
            return false;
        }
    }
}
