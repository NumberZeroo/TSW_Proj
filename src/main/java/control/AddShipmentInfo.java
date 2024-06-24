package control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tswproject.tswproj.SessionFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.infoConsegna.InfoConsegnaBean;
import model.infoConsegna.InfoConsegnaBeanDeserializer;
import model.infoConsegna.InfoConsegnaDAO;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Si interroga questa classe con un json che rappresenta le informazioni di spedizione
 * Se la richiesta va a buon fine viene ritornato un json che rappresenta successo o insuccesso.
 * Formato di input: citta, cap, via, altro, destinatario
 * Formato di output: {status: [success/error]}
 */

@WebServlet(value = "/addShipmentInfo")
public class AddShipmentInfo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionFacade userSession = new SessionFacade(req.getSession());
        if (!userSession.isLoggedIn()) {
            resp.sendRedirect("/login.jsp");
            return;
        }

        StringBuffer jb = new StringBuffer();
        String line = null;
        try (InfoConsegnaDAO dao = new InfoConsegnaDAO()){
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(InfoConsegnaBean.class, new InfoConsegnaBeanDeserializer());
            Gson gson = gsonBuilder.create();
            InfoConsegnaBean infoConsegnaBean = gson.fromJson(jb.toString(), InfoConsegnaBean.class);

            infoConsegnaBean.setIdUtente(userSession.getUserId());
            long generatedId = dao.doSave(infoConsegnaBean);
            resp.getWriter().write("{\"id\": \""+generatedId+"\", \"status\":\"success\"}");
        } catch (Exception e) {
            resp.getWriter().write("{\"status\":\"error\"}");
        }
    }
}
