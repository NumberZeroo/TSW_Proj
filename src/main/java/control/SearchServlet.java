package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        List<ProdottoBean> prodottoBeanList = new ArrayList<>();
        try(ProdottoDAO prodottoDAO = new ProdottoDAO()) {
            prodottoBeanList = prodottoDAO.doRetrieveByName(q);
        } catch (SQLException e) {
            resp.getWriter().println("{\"status\":\"error\"}");
            return;
        }

        StringBuilder responseBuilder = new StringBuilder();
        responseBuilder.append("{\"status\":\"success\"");
        responseBuilder.append(",\"products\":{");
        for(int i = 0; i < prodottoBeanList.size(); i++) {
            responseBuilder.append("\""+prodottoBeanList.get(i).getNome()+"\" : " + prodottoBeanList.get(i).getId());
            if (i != prodottoBeanList.size() - 1) {
                responseBuilder.append(",");
            }
        }
        responseBuilder.append("}}");
        resp.getWriter().println(responseBuilder.toString());
    }
}
