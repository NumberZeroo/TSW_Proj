package control;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.tswproject.tswproj.EmptyPoolException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import model.prodotto.*;

@WebServlet(name = "catalogo", value = "/mostraCatalogoServlet")
public class MostraCatalogoServlet extends jakarta.servlet.http.HttpServlet {
    private ProdottoDAO prodottoDAO;
    private Logger logger;

    @Override
    public void init() throws ServletException {
        super.init();
        logger = Logger.getLogger(getClass().getName());
        try {
            prodottoDAO = new ProdottoDAO();
        } catch (EmptyPoolException e) {
            logger.warning("Conneection pool vuota...trova un modo migliore di gestire questo errore...");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
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