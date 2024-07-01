package control;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

@WebServlet(value = "/addProductServlet")
@MultipartConfig
public class addProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        int animalRace = Integer.parseInt(request.getParameter("animalRace"));
        String category = request.getParameter("category");
        String size = request.getParameter("size");
        int minEta = Integer.parseInt(request.getParameter("minEta"));
        int maxEta = Integer.parseInt(request.getParameter("maxEta"));
        int disponibilita = Integer.parseInt(request.getParameter("disponibilita"));
        boolean sterilized = request.getParameter("sterilized").equals("1");

        Part filePart = request.getPart("imgPath");
        // Percorso dove salvare il file
        String uploadDir = getServletContext().getRealPath("") + File.separator + "img";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdir();
        }

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        File file = new File(uploadDir + File.separator + fileName);

        try (var fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath());
        }

        String imgPath = "img/" + fileName;

        // Crea un nuovo oggetto ProdottoBean
        ProdottoBean prodotto = new ProdottoBean();

        // Imposta i campi del prodotto
        prodotto.setNome(nome);
        prodotto.setDescrizione(descrizione);
        prodotto.setPrezzo(prezzo);
        prodotto.setTipoAnimale(animalRace); // Setta il tipo di animale del prodotto
        prodotto.setCategoria(category);
        prodotto.setTaglia(size);
        prodotto.setMinEta(minEta);
        prodotto.setMaxEta(maxEta);
        prodotto.setDisponibilita(disponibilita);
        prodotto.setSterilizzati(sterilized);
        prodotto.setVisibile(true);
        prodotto.setImgPath(imgPath); // Setta il path dell'immagine del prodotto

        if (category.equals("Alimentari")) { // Se la categoria è Alimentari, l'iva è al 22%
            prodotto.setIva("22");
        } else {
            prodotto.setIva("10"); // Altrimenti l'iva è al 10%
        }

        try (ProdottoDAO dao = new ProdottoDAO();) {
            dao.doSave(prodotto);
        } catch (SQLException e) {
            response.getWriter().print("{\"status\": \"error\"}");
            return;
        }
        // Reindirizza l'utente alla pagina di conferma
        response.getWriter().print("{\"status\": \"success\"}");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
