package control;

import com.tswproject.tswproj.EmptyPoolException;
import com.tswproject.tswproj.RuntimeSQLException;
import com.tswproject.tswproj.Security;
import com.tswproject.tswproj.SessionFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
            return;
        }

        SessionFacade session = new SessionFacade(req.getSession());
        try {
            session.login(user);
        } catch (SQLException e) {
            e.printStackTrace(); // TODO: log
            throw new RuntimeSQLException("Errore durante il login", e);
        }
        resp.sendRedirect(req.getContextPath() + "/profile.jsp");

    }

    private boolean checkLogin(UtenteBean user, String hashedPassword) {
        return Security.verifyPassword(hashedPassword, user.getPassword());
    }
}
