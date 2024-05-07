package control;

import com.tswproject.tswproj.EmptyPoolException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.utente.*;

import java.io.IOException;
import java.sql.SQLException;

// @WebServlet(value = "/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        // Recupera i parametri della richiesta
//        String username = request.getParameter("username");
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//
//        if(registerUser(username, email, password)) {
//            //Utente registrato con successo
//            response.sendRedirect("login.jsp");
//        } else {
//            //Errore nella registrazione
//            response.sendRedirect("register.jsp");
//        }

        response.sendRedirect("index.jsp");
    }

    private boolean registerUser(String username, String email, String password){
        UtenteBean utente = new UtenteBean();

        //username, email, imgPath, isAdmin, password
        utente.setUsername(username);
        utente.setEmail(email);
        utente.setImgPath("/test");
        utente.setIsAdmin(0);
        utente.setPassword(password);

        try{
            UtenteDAO ut = new UtenteDAO();
            ut.doSave(utente);
            return true;
        }catch (SQLException | EmptyPoolException e) {
            e.printStackTrace();
            return false;
        }
    }
}
