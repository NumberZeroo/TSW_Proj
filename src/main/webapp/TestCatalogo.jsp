<%@ page import="java.util.List" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="javax.swing.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>Catalogo Prodotti</title>
</head>
<body>
 <%@ include file="navbar.jsp" %>

    <div class="content">
        <button id="open-filter-button" class="display-none">
            <i class="fas fa-filter"></i>
        </button> <!-- Pulsante per mostrare la barra dei filtri a pagina rimpicciolita -->
        <i id="filter-button" class="fas fa-filter"></i> <!-- Pulsante per mostrare/nascondere la barra dei filtri -->

        <div id="filter-bar" class="filter-bar">
            <i id="close-button" class="fas fa-times"></i>
            <form action="${pageContext.request.contextPath}/mostraCatalogoServlet" method="get">
                <label for="price">Prezzo: </label> <span id="price-value"></span>
                <input type="range" id="price" name="price" min="0" max="100">

                <label for="size">Taglia:</label>
                <select id="size" name="size">
                    <option value="PICCOLA">Piccola</option>
                    <option value="MEDIA">Media</option>
                    <option value="GRANDE">Grande</option>
                </select>

                <label for="category">Categoria:</label>
                <select id="category" name="category">
                    <option value="Alimentari">Alimentari</option>
                    <option value="Giocattoli">Giocattoli</option>
                    <option value="Cat3">Cat3</option>
                </select>

                <!-- Filtro per tipo di animale -->
            <%--    <label for="animalRace">Tipo di Animale:</label>--%>
            <%--    <input type="number" id="animalRace" name="animalRace">--%>

                <label for="animalRace">Tipo di animale:</label>
                <select id="animalRace" name="animalRace">
                    <option value="1">Cane</option>
                    <option value="2">Gatto</option>
                    <option value="3">Volatile</option>
                </select>


                <!-- Filtro per età minima -->
                <label for="min-age">Età Minima: </label><span id="min-age-value"></span>
                <input type="range" id="min-age" name="min-age" min="0" max="20">

                <!-- Filtro per età massima -->
                <label for="max-age">Età Massima: </label><span id="max-age-value"></span>
                <input type="range" id="max-age" name="max-age" min="0" max="30">

                <!-- Filtro per sterilizzazione -->
                <label for="sterilized">Sterilizzato:</label>
                <select id="sterilized" name="sterilized">
                    <option value="1">Sì</option>
                    <option value="0">No</option>
                </select>

                <input type="submit" value="Applica filtri">
            </form>
        </div>

    <% List<ProdottoBean> prodotti = (List<ProdottoBean>) request.getAttribute("prodotti"); %>

    <% if(prodotti == null || prodotti.isEmpty()){ %>
        <p>Nessun prodotto disponibile.</p>
    <% } else { %>
        <div class="griglia-prodotti">
            <%for(ProdottoBean prodotto : prodotti) { %>
                <div class = "test-div-class">
                    <a href="product.jsp?id=<%=prodotto.getId()%>">
                        <img src="<%= prodotto.getImgPath() %>" alt="Immagine">
                        <h2><%=prodotto.getNome()%></h2>
                    </a>
                    <p>Prezzo: <%=prodotto.getPrezzo()%> €</p>
                </div>
                <% }
            } %>
        </div>
    </div>

    <%@ include file="footer.jsp" %>

    <!-- Barra dei filtri a pagina ingrandita -->
    <script>
       // Seleziona il pulsante, la barra dei filtri e il pulsante di chiusura
        let filterButton = document.getElementById('filter-button');
        let filterBar = document.getElementById('filter-bar');
        let closeButton = document.getElementById('close-button');
        let temp = document.getElementById('filter-button')
        let productGrid = document.querySelector('.griglia-prodotti');

        // Aggiungi un gestore di eventi al pulsante per mostrare/nascondere la barra dei filtri
        filterButton.addEventListener('click', function() {
            filterBar.classList.toggle('show');
            productGrid.classList.toggle('shift-left');
            filterButton.classList.add('display-none');
            // Cambia l'icona del pulsante dei filtri
            if (!filterBar.classList.contains('show')) {
                filterButton.className = 'fas fa-filter';
            }
        });

        // Aggiungi un gestore di eventi al pulsante di chiusura per nascondere la barra dei filtri
        closeButton.addEventListener('click', function() {
            filterBar.classList.remove('show');
            filterButton.classList.remove('display-none');
            productGrid.classList.remove('shift-left');
            filterButton.className = 'fas fa-filter';
        });
    </script>

    <script>
        // Seleziona i filtri e gli elementi <span>
        let priceFilter = document.getElementById('price');
        let priceValue = document.getElementById('price-value');
        let minAgeFilter = document.getElementById('min-age');
        let minAgeValue = document.getElementById('min-age-value');
        let maxAgeFilter = document.getElementById('max-age');
        let maxAgeValue = document.getElementById('max-age-value');

        // Aggiorna il contenuto degli elementi <span> con il valore corrente del filtro
        priceValue.textContent = priceFilter.value;
        minAgeValue.textContent = minAgeFilter.value;
        maxAgeValue.textContent = maxAgeFilter.value;

        // Aggiungi un gestore di eventi a ciascun filtro per aggiornare il valore ogni volta che cambia
        priceFilter.addEventListener('input', function() {
            priceValue.textContent = this.value;
        });

        minAgeFilter.addEventListener('input', function() {
            minAgeValue.textContent = this.value;
        });

        maxAgeFilter.addEventListener('input', function() {
            maxAgeValue.textContent = this.value;
        });
    </script>

    <!-- Barra dei filtri a pagina rimpicciolita -->
    <script>
        let openFilterButton = document.getElementById('open-filter-button');

        openFilterButton.addEventListener('click', function() {
            filterBar.style.display = 'block';
            openFilterButton.style.display = 'none';
        });
    </script>
</body>
</html>