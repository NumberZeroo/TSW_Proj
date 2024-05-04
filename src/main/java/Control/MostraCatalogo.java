package Control;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.tswproject.tswproj.EmptyPoolException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import model.prodotto.*;

import javax.swing.*;

public class MostraCatalogo extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProdottoDAO prodottoDAO;

    public void init() throws ServletException {
        super.init();
        try {
            prodottoDAO = new ProdottoDAO();
        } catch (EmptyPoolException e) {
            System.out.println("Conneection pool vuota...trova un modo migliore di gestire questo errore...");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<ProdottoBean> prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveAll("ASC");
            request.setAttribute("prodotti", prodotti);
            request.getRequestDispatcher("/TestCatalogo.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public void destroy() {
        super.destroy();
        prodottoDAO.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}