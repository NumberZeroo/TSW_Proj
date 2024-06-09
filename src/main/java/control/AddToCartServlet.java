package control;

import com.tswproject.tswproj.SessionFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/aggiungiAlCarrello")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionFacade session = new SessionFacade(req.getSession());
        PrintWriter out = resp.getWriter();

        try {
            session.addCartProduct(Long.parseLong(req.getParameter("id")));
            // req.getRequestDispatcher("/cart.jsp").forward(req, resp); // TODO: aggiungi notifica "prodotto aggiunto al carrello"
            // resp.sendRedirect(req.getContextPath() + "/cart.jsp");
            out.println("{\"status\":\"success\"}");
        } catch(NumberFormatException e) {
            //TODO: log
            out.println("{\"status\":\"error\"}");
        }
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
