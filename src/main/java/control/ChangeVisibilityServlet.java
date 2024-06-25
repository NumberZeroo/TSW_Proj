package control;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.prodotto.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/changeVisibilityServlet")
public class ChangeVisibilityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        boolean isVisible = Boolean.parseBoolean(request.getParameter("isVisible"));

        try (ProdottoDAO prodottoDAO = new ProdottoDAO()) {
            prodottoDAO.changeVisibility(productId, isVisible);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
