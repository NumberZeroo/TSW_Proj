package control;

import com.tswproject.tswproj.EmptyPoolException;
import com.tswproject.tswproj.Security;
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
import java.util.Optional;
import java.util.logging.Logger;

@WebServlet(value = "/loginServlet")
public class LoginServlet extends HttpServlet {

    private static Logger logger;

    @Override
    public void init() throws ServletException {
        logger = Logger.getLogger(LoginServlet.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new UnsupportedOperationException("GET not implemented"); // TODO: sostituisci con client side error
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null) {
            logger.info("Username or password are missing");
            return; // TODO: aggiungi errore
        }

        String passwordHash = Security.getPasswordHash(password);

        try {
            UtenteDAO utenteDAO = new UtenteDAO();
            Optional<UtenteBean> user = utenteDAO.doRetrieveByLogin(username, passwordHash);
            if (user.isPresent()) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
            }

            resp.sendRedirect(req.getContextPath() + "/checkLogin.jsp");

        } catch (EmptyPoolException e) {
            logger.severe("Empty pool"); // TODO: aggiungi errore
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }

    }
}
