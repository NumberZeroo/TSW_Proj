package control;

import com.tswproject.tswproj.EmptyPoolException;
import com.tswproject.tswproj.Security;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.utente.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(value = "/registerServlet")
public class RegisterServlet extends HttpServlet {

    private Logger logger;

    @Override
    public void init() throws ServletException {
        logger = Logger.getLogger(getClass().getName());
    }

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

        if (!Security.checkEmailFormat(email)) {
            System.out.println("Email non valida"); //todo client side error message
            return false;
        }

        //username, email, imgPath, isAdmin, password
        utente.setUsername(username); //todo exclude unsecure chars
        utente.setEmail(email);
        utente.setImgPath("/test");  //todo default image path
        utente.setIsAdmin(0); //todo default isAdmin
        utente.setPassword(Security.getPasswordHash(password));

        try{
            UtenteDAO ut = new UtenteDAO();
            ut.doSave(utente);
            return true;
        }catch (SQLException | EmptyPoolException e) {
            logger.severe(e.getMessage());
            return false;
        }
    }
}
