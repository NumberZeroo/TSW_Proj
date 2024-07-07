package control;

import com.tswproject.tswproj.SessionFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.wishlist.WishlistDAO;
import model.wishlistItem.WishlistItemDAO;

import java.io.IOException;

@WebServlet("/removeFromWishlistServlet")
public class RemoveFromWishlistServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFacade sessionFacade = new SessionFacade(request.getSession());
        int productId = Integer.parseInt(request.getParameter("productId"));
        long userId = sessionFacade.getUserId();

        try (WishlistDAO wishlistDAO = new WishlistDAO();
             WishlistItemDAO wishlistItemDAO = new WishlistItemDAO()) {

            long wishlistId = wishlistDAO.doRetrieveByUser(userId).getId();
            wishlistItemDAO.doDelete(wishlistId, productId);

        } catch (Exception e) {
            throw new ServletException("Errore nel rimuovere il prodotto dalla wishlist", e);
        }

        response.sendRedirect(request.getContextPath() + "/wishList.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
