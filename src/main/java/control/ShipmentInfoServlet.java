package control;

import com.tswproject.tswproj.SessionFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.infoConsegna.InfoConsegnaBean;
import model.infoConsegna.InfoConsegnaDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interrogare con una GET questa servlet per ricevere una lista di informazioni di spedizione
 */

@WebServlet(value = "/getShipmentInfos")
public class ShipmentInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionFacade userSession = new SessionFacade(req.getSession());
        if (!userSession.isLoggedIn()) {
            resp.sendRedirect("/login.jsp");
            return;
        }

        List<InfoConsegnaBean> infoConsegnaBeans = new ArrayList<>();
        try(InfoConsegnaDAO dao = new InfoConsegnaDAO()){
            infoConsegnaBeans = dao.doRetrieveAllByUser(userSession.getUserId());
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la collezione delle informazioni di spedizione", e);
        }

        StringBuilder jsonOutput = new StringBuilder();
        jsonOutput.append("[");
        for (int i = 0; i < infoConsegnaBeans.size(); i++) {
            jsonOutput.append(infoConsegnaBeans.get(i).toJson());
            if (i < infoConsegnaBeans.size() - 1)
                jsonOutput.append(",");
        }
        jsonOutput.append("]");

        resp.getWriter().write(jsonOutput.toString());
    }
}
