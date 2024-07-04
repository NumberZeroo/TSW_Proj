package control;

import com.tswproject.tswproj.RuntimeSQLException;
import com.tswproject.tswproj.SessionFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(value = "/aggiungiAlCarrello")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionFacade session = new SessionFacade(req.getSession());
        PrintWriter out = resp.getWriter();

        try {
            long itemId = Long.parseLong(req.getParameter("id"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            session.addCartProduct(itemId, quantity);
            out.println("{\"status\":\"success\"}");
        } catch(NumberFormatException e) {
            //TODO: log
            out.println("{\"status\":\"error\"}");
            System.out.println("errore1");
        } catch (SQLException e) { // TODO: qui potrebbe essere utile comunicare in json con ajax?
            System.out.println("errore2");
            throw new RuntimeSQLException("Errore durante l'aggiunta al carrello", e);
        }
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
