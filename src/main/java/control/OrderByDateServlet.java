package control;

import jakarta.servlet.annotation.MultipartConfig;
import model.ordine.OrdineBean;
import model.ordine.OrdineDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet(name = "orderByDateServlet", urlPatterns = {"/orderByDate"})
@MultipartConfig
public class OrderByDateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

//        Date data1 = Date.valueOf(startDate);
//        Date data2 = Date.valueOf(endDate);

//        try (OrdineDAO ordineDAO = new OrdineDAO()) {
//            Collection<OrdineBean> ordini = ordineDAO.doRetrieveByDate(startDate, endDate);
//            request.setAttribute("ordini", ordini);
//            request.getRequestDispatcher("adminPage.jsp").forward(request, response);
//        } catch (SQLException e) {
//            throw new ServletException("Errore durante il recupero degli ordini", e);
//        }

        request.getRequestDispatcher("adminPage.jsp?startDate=" + startDate
                + "&endDate=" + endDate).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}