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
    <i id="filter-button" class="fas fa-filter"></i>

    <div id="filter-bar" class="filter-bar">
        <i id="close-button" class="fas fa-times"></i>
        <form action="TestCatalogo.jsp" method="get">
            <label for="price">Prezzo: </label> <span id="price-value"></span>
            <input type="range" id="price" name="price" min="0" max="100">


            <label for="size">Taglia:</label>
            <select id="size" name="size">
                <option value="small">Piccola</option>
                <option value="medium">Media</option>
                <option value="large">Grande</option>
            </select>

            <label for="category">Categoria:</label>
            <select id="category" name="category">
                <option value="category1">Alimentari</option>
                <option value="category2">Giocattoli</option>
                <option value="category3">Categoria 3</option>
            </select>

            <!-- Filtro per razza di animali -->
            <label for="animal-race">Razza:</label>
            <select id="animal-race" name="animal-race">
                <option value="dog">Cane</option>
                <option value="cat">Gatto</option>
                <option value="bird">Volatile</option>
            </select>

            <!-- Filtro per sterilizzazione -->
            <label for="sterilized">Sterilizzato:</label>
            <select id="sterilized" name="sterilized">
                <option value="yes">Sì</option>
                <option value="no">No</option>
            </select>

            <!-- Filtro per età -->
            <label for="age">Età: </label><span id="age-value"></span>
            <input type="range" id="age" name="age" min="0" max="20">

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
        let ageFilter = document.getElementById('age');
        let ageValue = document.getElementById('age-value');

        // Aggiorna il contenuto degli elementi <span> con il valore corrente del filtro
        priceValue.textContent = priceFilter.value;
        ageValue.textContent = ageFilter.value;

        // Aggiungi un gestore di eventi a ciascun filtro per aggiornare il valore ogni volta che cambia
        priceFilter.addEventListener('input', function() {
            priceValue.textContent = this.value;
        });

        ageFilter.addEventListener('input', function() {
            ageValue.textContent = this.value;
        });
    </script>
</body>
</html>