package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.prodotto.ProdottoDAO;
import model.prodotto.ProdottoBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/refillProductServlet")
public class RefillProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera l'ID del prodotto e la quantità dal form
        long productId = Long.parseLong(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try (ProdottoDAO prodottoDAO = new ProdottoDAO()) {
            // Recupera il prodotto dal database
            ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(productId);

            // Aggiorna la disponibilità del prodotto
            prodotto.setDisponibilita(prodotto.getDisponibilita() + quantity);

            // Salva il prodotto aggiornato nel database
            prodottoDAO.doUpdate(prodotto);

            // Reindirizza l'utente alla pagina di amministrazione
            response.sendRedirect("adminPage.jsp");
        } catch (SQLException e) {
            throw new ServletException("Errore durante il refill del prodotto", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}