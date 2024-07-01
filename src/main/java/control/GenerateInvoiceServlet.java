package control;

import com.tswproject.tswproj.Invoice;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/getInvoice")
public class GenerateInvoiceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long orderId = Long.parseLong(req.getParameter("orderId"));
        Invoice invoice = new Invoice();
        try {
            invoice.generate(orderId, resp.getOutputStream());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
