package control;

import com.tswproject.tswproj.EmptyPoolException;
import com.tswproject.tswproj.Security;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.NotSupportedException;
import model.utente.UtenteBean;
import model.utente.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null) {
            System.out.println("Username o password non validi"); // TODO: client side error
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        UtenteBean user;
        try(UtenteDAO utenteDAO = new UtenteDAO()){
            user = utenteDAO.doRetrieveByUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO: log
        }

        if (user == null || !checkLogin(user, password)) {
            System.out.println("Login fallito, come te"); // TODO: client side error
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", user.getUsername());
        session.setAttribute("loggedIn", true);
        resp.sendRedirect(req.getContextPath() + "/profile.jsp");

    }

    private boolean checkLogin(UtenteBean user, String hashedPassword) {
        return Security.verifyPassword(hashedPassword, user.getPassword());
    }
}
