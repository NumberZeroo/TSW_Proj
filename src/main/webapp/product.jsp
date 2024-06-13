<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="model.recensione.RecensioneBean" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.recensione.RecensioneDAO" %>

<html>
<head>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="navbar.jsp" %>

    <% ProdottoBean prodotto = (ProdottoBean) request.getAttribute("product"); %>
    <title><%=prodotto.getNome()%></title>
</head>

<body>
    <div class="product-details">
        <div class="product-image">
            <img src="<%=prodotto.getImgPath()%>" alt="<%=prodotto.getNome()%>">
        </div>
        <div class="product-info">
            <h1><%=prodotto.getNome()%></h1>
            <p><%=prodotto.getDescrizione()%></p>
            <p>Prezzo: <%=prodotto.getPrezzo()%> €</p>
            <label for="quantity">Quantità:</label>
            <input type="number" id="quantity" name="quantity" min="1" max="99" value="1">
            <button class="addToCartButton" data-product-id="<%=prodotto.getId()%>">Aggiungi al Carrello</button>
        </div>
    </div>

    <%-- Recupera le recensioni e i prodotti consigliati dal tuo database --%>
    <%
        RecensioneDAO recensioneDAO = new RecensioneDAO();
        List<RecensioneBean> recensioni = (List<RecensioneBean>) recensioneDAO.doRetrieveByProduct(prodotto.getId());
        List<ProdottoBean> prodottiConsigliati = new ArrayList<>();
    %>

    <%-- Visualizza le recensioni --%>
    <div class="reviews">
        <h2>Recensioni</h2>
        <% for (RecensioneBean recensione : recensioni) { %>
            <div class="review">
                <h2><%=recensione.getTitolo()%></h2>
                <p><%=recensione.getCommento()%></p>
                <p>Valutazione: <%=recensione.getValutazione()%></p>
                <p>Data: <%=recensione.getData()%></p>
            </div>
        <% } %>
    </div>

    <%-- Visualizza i prodotti consigliati --%>
    <div class="recommended-products">
        <h2>Prodotti Consigliati</h2>
        <% for (ProdottoBean prod : prodottiConsigliati) { %>
            <div class="product">
                <img src="<%=prod.getImgPath()%>" alt="<%=prod.getNome()%>">
                <h2><%=prod.getNome()%></h2>
                <p><%=prod.getDescrizione()%></p>
                <p>Prezzo: <%=prod.getPrezzo()%> €</p>
            </div>
        <% } %>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var button = document.getElementsByClassName("addToCartButton")[0];

            button.addEventListener('click', function() {
                var productId = this.getAttribute('data-product-id');
                var xhr = new XMLHttpRequest();

                xhr.open('POST', 'aggiungiAlCarrello', true);
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

                xhr.onreadystatechange = function() {
                    var feedback = "";
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            try{
                                var response = JSON.parse(xhr.responseText);
                            } catch(e){
                                console.log(e)
                                console.log("mannagg")
                            }
                            if (response.status === 'success') {
                                feedback = 'Prodotto aggiunto al carrello con successo!';
                            } else {
                                feedback = 'Errore nell\'aggiunta al carrello. Riprova.';
                            }
                        } else {
                            feedback = 'Errore nella comunicazione con il server.';
                        }
                    }
                    alert(feedback)
                };
                xhr.send('id=' + encodeURIComponent(productId));
            });
        });
    </script>

    <%@ include file="footer.jsp" %>
</body>
</html>
