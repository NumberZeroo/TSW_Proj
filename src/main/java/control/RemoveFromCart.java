package control;

import com.tswproject.tswproj.SessionFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/removeFromCart")
public class RemoveFromCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("id") == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); // TODO: client side error
            return;
        }

        SessionFacade userSession = new SessionFacade(req.getSession());
        String redirectPath = userSession.isLoggedIn() ? "/cart.jsp" : "/nonLoggedCart.jsp";

        long id = Long.parseLong(req.getParameter("id"));
        try {
            userSession.removeCartProduct(id);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + redirectPath + "?error=1");
            return;
        }
        resp.sendRedirect(req.getContextPath() + redirectPath);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
