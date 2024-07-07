package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.wishlist.WishlistBean;
import model.wishlist.WishlistDAO;
import model.wishlistItem.WishlistItemBean;
import model.wishlistItem.WishlistItemDAO;
import com.tswproject.tswproj.SessionFacade;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addToWishlistServlet")
public class AddToWishlistServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFacade userSession = new SessionFacade(request.getSession());
        long userId = userSession.getUserId();
        long productId = Long.parseLong(request.getParameter("productId"));

        if(userId == -1) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try (WishlistDAO wishlistDAO = new WishlistDAO();
             WishlistItemDAO wishlistItemDAO = new WishlistItemDAO()) {

            WishlistBean wishlist = wishlistDAO.doRetrieveByUser(userId);
            if (wishlist == null) {
                wishlist = new WishlistBean();
                wishlist.setUserId(userId);
                wishlistDAO.doSave(wishlist);
            }

            WishlistItemBean wishlistItem = new WishlistItemBean();

            //controllo se il prodotto è già presente nella wishlist
            for(WishlistItemBean item : wishlistItemDAO.doRetrieveByWishlistId(wishlist.getId())) {
                if(item.getProductId() == productId) {
                    response.sendRedirect(request.getContextPath() + "/wishList.jsp");
                    return;
                }
            }

            wishlistItem.setWishlistId(wishlist.getId());
            wishlistItem.setProductId(productId);
            wishlistItemDAO.doSave(wishlistItem);

            response.sendRedirect(request.getContextPath() + "/wishList.jsp");

        } catch (SQLException e) {
            throw new ServletException("Errore aggiunta alla wishlist", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
