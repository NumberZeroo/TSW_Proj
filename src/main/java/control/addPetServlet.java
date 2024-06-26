package control;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.pet.*;
import com.tswproject.tswproj.SessionFacade;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(value = "/addPetServlet", name = "addPetServlet")
public class addPetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFacade sessionFacade = new SessionFacade(request.getSession());

        String nome = request.getParameter("nome");
        int animalRace = Integer.parseInt(request.getParameter("animalRace"));
        String size = request.getParameter("size");
        Date data = Date.valueOf(request.getParameter("birthDate"));
        String imgPath = request.getParameter("imgPath");
        boolean sterilized = request.getParameter("sterilized").equals("1");

        // Crea un nuovo oggetto petBean
        PetBean pet = new PetBean();

        // Imposta i campi del pet
        pet.setNome(nome);
        pet.setTipo(String.valueOf(animalRace)); // Setta il tipo di animale del pet
        pet.setTaglia(size);
        pet.setSterilizzato(sterilized);
        pet.setDataNascita(data);

        imgPath = "img/" + imgPath;
        pet.setImgPath(imgPath); // Setta il path dell'immagine del pet

        // Prendi l'id dell'utente loggato
        pet.setIdUtente(sessionFacade.getUserId());

        // Aggiungi il pet al database
        try (PetDAO dao = new PetDAO();) {
            dao.doSave(pet);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        // Reindirizza l'utente alla pagina di conferma
        response.sendRedirect("profile.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}