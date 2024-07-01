package control;

import com.tswproject.tswproj.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.recensione.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

@WebServlet("/addReviewServlet")
public class addReviewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFacade sessionFacade = new SessionFacade(request.getSession());

        long userId = sessionFacade.getUserId();
        long productId = Long.parseLong(request.getParameter("idProdotto"));
        String title = request.getParameter("titolo");
        String comment = request.getParameter("commento");
        double rating = Double.parseDouble(request.getParameter("valutazione"));

        RecensioneBean review = new RecensioneBean();
        review.setIdUtente(userId);
        review.setIdProdotto(productId);
        review.setTitolo(title);
        review.setCommento(comment);
        review.setValutazione(rating);

        LocalDate currentDate = LocalDate.now();
        review.setData(Date.valueOf(currentDate));

        try (RecensioneDAO recensioneDAO = new RecensioneDAO()) {
            recensioneDAO.doSave(review);
        } catch (SQLException | EmptyPoolException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/product?id=" + productId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
