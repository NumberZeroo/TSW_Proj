package control;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.recensione.RecensioneDAO;

@WebServlet(name = "deleteReviewServlet", value = "/deleteReviewServlet")
public class DeleteReviewServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long reviewId = Integer.parseInt(request.getParameter("reviewId"));
        long productId = Integer.parseInt(request.getParameter("productId"));

        try (RecensioneDAO recensioneDAO = new RecensioneDAO()) {
            recensioneDAO.doDelete(reviewId);
        } catch (Exception e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/product?id=" + productId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}