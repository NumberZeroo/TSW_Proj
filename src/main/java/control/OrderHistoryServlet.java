package control;

import model.ordine.OrdineBean;
import model.ordine.OrdineDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet(name = "orderHistoryServlet", urlPatterns = {"/orderHistory"})
public class OrderHistoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long userId = Long.parseLong(request.getParameter("userId"));

        try (OrdineDAO ordineDAO = new OrdineDAO()) {
            Collection<OrdineBean> ordini = ordineDAO.doRetrieveByUser(userId);
            request.setAttribute("ordini", ordini);
            request.getRequestDispatcher("orderHistory.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Errore durante il recupero dello storico ordini", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}