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
        <form>
            <label for="price">Prezzo:</label>
            <input type="range" id="price" name="price" min="0" max="100">
        </form>
        <form>
            <label for="size">Taglia:</label>
            <select id="size" name="size">
                <option value="small">Piccola</option>
                <option value="medium">Media</option>
                <option value="large">Grande</option>
            </select>
        </form>
        <form>
            <label for="category">Categoria:</label>
            <select id="category" name="category">
                <option value="category1">Categoria 1</option>
                <option value="category2">Categoria 2</option>
                <option value="category3">Categoria 3</option>
            </select>
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
</body>
</html>