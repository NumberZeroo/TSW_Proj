<%@ page import="model.pet.PetBean" %>
<%@ page import="model.pet.PetDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.tswproject.tswproj.EmptyPoolException" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/style/filterBar.css">

<div id="filter-bar" class="filter-bar">
    <i id="close-button" class="fas fa-times"></i>
    <form action="${pageContext.request.contextPath}/mostraCatalogoServlet" method="get">
        <label for="price">Prezzo: </label> <span id="price-value"></span>
        <input type="range" id="price" name="price" min="0" max="100" value="50">

        <label for="size">Taglia:</label>
        <select id="size" name="size">
            <option value="all">Tutte le taglie</option>
            <option value="PICCOLA">Piccola</option>
            <option value="MEDIA">Media</option>
            <option value="GRANDE">Grande</option>
        </select>

        <label for="category">Categoria:</label>
        <select id="category" name="category">
            <option value="all">Tutte le categorie</option>
            <option value="Alimentari">Alimentari</option>
            <option value="Giocattoli">Giocattoli</option>
        </select>

        <label for="animalRace">Tipo di animale:</label>
        <select id="animalRace" name="animalRace">
            <option value="all">Tutti gli animali</option>
            <option value="1">Cane</option>
            <option value="2">Gatto</option>
            <option value="3">Volatile</option>
        </select>

        <!-- Filtro per sterilizzazione -->
        <label for="sterilized">Sterilizzato:</label>
        <select id="sterilized" name="sterilized">
            <option value="all">Tutti</option>
            <option value="1">Sì</option>
            <option value="0">No</option>
        </select>

        <%
            List<PetBean> pets = new ArrayList<>();
            if (sessionFacade.isLoggedIn()) {
                try (PetDAO petDAO = new PetDAO()) {
                    pets = petDAO.doRetrieveByUser(sessionFacade.getUserId());
                } catch (SQLException | EmptyPoolException e) {
                    e.printStackTrace();
                    // TODO: handle exception properly
                }
            }
        %>

        <% if (sessionFacade.isLoggedIn()) { %>
        <label for="myPet">I miei animali:</label>
        <select id="myPet" name="myPet">
            <option value="all">Nessun pet</option>
            <% for (PetBean pet : pets) { %>
            <option value="<%=pet.getId()%>"><%=pet.getNome()%>
            </option>
            <% } %>
        </select>
        <% } %>

        <input type="submit" value="Applica filtri">
    </form>
</div>

<style>
    #sterilized, label[for="sterilized"] {
        display: none;
    }

    #myPet {
        width: auto; /* Larghezza automatica in base al contenuto */
        height: auto; /* Altezza automatica in base al contenuto */
        border: 2px solid #000;
        border-radius: 3px;
        background-color: #fff;
    }
</style>

<script>
    window.onload = function () {
        let categorySelect = document.getElementById('category');
        let sterilizedLabel = document.querySelector('label[for="sterilized"]');
        let sterilizedSelect = document.getElementById('sterilized');

        categorySelect.addEventListener('change', function () {
            if (this.value === 'Alimentari') {
                sterilizedLabel.style.display = 'block';
                sterilizedSelect.style.display = 'block';
            } else {
                sterilizedLabel.style.display = 'none';
                sterilizedSelect.style.display = 'none';
            }
        });

        // Simula un evento 'change' per aggiornare lo stato iniziale
        categorySelect.dispatchEvent(new Event('change'));
    };
</script>