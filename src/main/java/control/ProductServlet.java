package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/product")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); // TODO errore user friendly
            return;
        }

        long productID = Long.parseLong(req.getParameter("id"));
        try(ProdottoDAO prodottoDAO = new ProdottoDAO()) {
            ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(productID);
            req.setAttribute("product", prodotto);
            req.getRequestDispatcher("/product.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
