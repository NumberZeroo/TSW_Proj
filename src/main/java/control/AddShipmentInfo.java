package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Si interroga questa classe con un json che rappresenta le informazioni di spedizione
 * Se la richiesta va a buon fine viene ritornato un json che rappresenta successo o insuccesso.
 * Formato di input: id, idUtente, citta, cap, via, altro, destinatario, isDefault [true, false]
 * Formato di output: {status: [success/error]}
 */
public class AddShipmentInfo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
