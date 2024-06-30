package control;

import com.tswproject.tswproj.RuntimeSQLException;
import com.tswproject.tswproj.SessionFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.cartItem.CartItemBean;
import model.cartItem.CartItemDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/changeQuantity")
public class ChangeQuantityServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int quantity;
        long productId;
        SessionFacade userSession = new SessionFacade(req.getSession());
        try{
            quantity = Integer.parseInt(req.getParameter("quantity"));
            productId = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
            return;
        }

        try(CartItemDAO cartItemDAO = new CartItemDAO()) {
            CartItemBean cartItemBean = cartItemDAO.doRetrieveByKey(productId);
            cartItemBean.setQuantita(quantity);
            cartItemDAO.doUpdate(cartItemBean);
            userSession.getCartProducts().put(cartItemBean.getIdProdotto(), quantity);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
