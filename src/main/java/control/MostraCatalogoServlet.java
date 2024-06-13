package control;

import java.io.IOException;
import java.util.List;

import com.tswproject.tswproj.EmptyPoolException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import model.prodotto.*;

import javax.swing.*;

@WebServlet(name = "catalogo", value = "/mostraCatalogoServlet")
public class MostraCatalogoServlet extends jakarta.servlet.http.HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (ProdottoDAO prodottoDAO = new ProdottoDAO()) {
            // Recupera i parametri dei filtri dalla richiesta
            String price = request.getParameter("price");
            String size = request.getParameter("size");
            String category = request.getParameter("category");
            String animalRace = request.getParameter("animalRace");
            String sterilized = request.getParameter("sterilized");
            String minAge = request.getParameter("min-age");
            String maxAge = request.getParameter("max-age");

            List<ProdottoBean> prodotti;
            try {
                if (price != null || size != null || category != null || animalRace != null || sterilized != null || minAge != null || maxAge != null){
                    prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveFiltered(price, size, category, animalRace, sterilized, minAge, maxAge);
                } else {
                    prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveAll("ASC");
                }
                request.setAttribute("prodotti", prodotti);
                request.getRequestDispatcher("/TestCatalogo.jsp").forward(request, response);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}