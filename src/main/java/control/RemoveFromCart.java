package control;

import com.tswproject.tswproj.SessionFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/removeFromCart")
public class RemoveFromCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("id") == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); // TODO: client side error
            return;
        }
        long id = Long.parseLong(req.getParameter("id"));
        (new SessionFacade(req.getSession())).removeCartProduct(id);
        resp.sendRedirect(req.getContextPath() + "/cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
