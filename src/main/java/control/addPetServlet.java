package control;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.pet.*;
import com.tswproject.tswproj.SessionFacade;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(value = "/addPetServlet", name = "addPetServlet")
@MultipartConfig
public class addPetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionFacade sessionFacade = new SessionFacade(request.getSession());

        String nome = request.getParameter("nome");
        String animalRace = (request.getParameter("animalRace"));
        String size = request.getParameter("size");
        String birthDateString = request.getParameter("birthDate");
        if (!birthDateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("La data di nascita deve essere nel formato yyyy-mm-dd");
        }
        Date data = Date.valueOf(birthDateString);
        boolean sterilized = request.getParameter("sterilized").equals("1");

        // Crea un nuovo oggetto petBean
        PetBean pet = new PetBean();

        // Imposta i campi del pet
        pet.setNome(nome);
        pet.setTipo(String.valueOf(animalRace)); // Setta il tipo di animale del pet
        pet.setTaglia(size);
        pet.setSterilizzato(sterilized);
        pet.setDataNascita(data);

        Part filePart = request.getPart("imgPath");
        // Percorso dove salvare il file
        String uploadDir = getServletContext().getRealPath("") + File.separator + "img" + File.separator + "pets";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdir();
        }

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString() + System.currentTimeMillis();
        File file = new File(uploadDir + File.separator + fileName);

        try (var fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath());
        }

        String imgPath = "img/pets/" + fileName;

        // Prendi l'id dell'utente loggato
        pet.setIdUtente(sessionFacade.getUserId());
        pet.setImgPath(imgPath); // Setta il path dell'immagine del prodotto

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