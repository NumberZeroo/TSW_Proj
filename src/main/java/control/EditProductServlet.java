package control;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/editProductServlet")
public class EditProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        String category = request.getParameter("category");
        String taglia = request.getParameter("taglia");

        try (ProdottoDAO prodottoDAO = new ProdottoDAO()) {
            ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(id);

            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setPrezzo(prezzo);
            prodotto.setCategoria(category);
            prodotto.setTaglia(taglia);

            prodottoDAO.doUpdate(prodotto);
            request.setAttribute("product", prodotto);

        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("product?id=" + id);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
