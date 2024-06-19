<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="model.recensione.RecensioneBean" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.recensione.RecensioneDAO" %>
<%@ page import="model.prodotto.ProdottoDAO" %>
<%@ page import="com.tswproject.tswproj.SessionFacade" %>

<html>
<head>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="navbar.jsp" %>

    <% ProdottoBean prodotto = (ProdottoBean) request.getAttribute("product"); %>
    <title><%=prodotto.getNome()%></title>
</head>

<body>

    <% SessionFacade sessionFacade = new SessionFacade(request.getSession());
        if(sessionFacade.isLoggedIn() && sessionFacade.getIsAdmin()){ %>
    <button id="adminButton" class="adminButton">Modifica Prodotto</button>
    <form id="editProductForm" style="display: none;" action="${pageContext.request.contextPath}/editProductServlet" method="post">

        <input type="hidden" name="id" value="<%=prodotto.getId()%>">

        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="<%=prodotto.getNome()%>">

        <label for="descrizione">Descrizione:</label>
        <input type="text" id="descrizione" name="descrizione" value="<%=prodotto.getDescrizione()%>">

        <label for="prezzo">Prezzo:</label>
        <input type="number" id="prezzo" name="prezzo" value="<%=prodotto.getPrezzo()%>">

        <input type="submit" value="Conferma Modifiche">
    </form>

    <script>
        document.getElementById('adminButton').addEventListener('click', function() {
            document.getElementById('editProductForm').style.display = 'block';
        });
    </script>
<% } %>

    <div class="product-details">
        <div class="product-image">
            <img src="<%=prodotto.getImgPath()%>" alt="<%=prodotto.getNome()%>">
        </div>
        <div class="product-info-specs"> <!-- Nuovo div che raggruppa le informazioni del prodotto e le specifiche del prodotto -->
            <div class="product-info">
                <h1><%=prodotto.getNome()%></h1>
                <p><%=prodotto.getDescrizione()%></p>
                <h4>Prezzo: <%=prodotto.getPrezzo()%> €</h4>
            </div>

            <div class="product-specs">
                <h2>Dettagli prodotto</h2>
                <p>Categoria: <%=prodotto.getCategoria()%></p>
                <p>Taglia: <%=prodotto.getTaglia()%></p>
                <p>Range età consigliata: <%=prodotto.getMinEta()%>/<%=prodotto.getMaxEta()%></p>
            </div>
        </div>

        <div class="addToCart">
            <h3>Disponibilità: <%=prodotto.getDisponibilita()%></h3>
            <label for="quantity">Quantità:</label>
            <input type="number" id="quantity" name="quantity" min="1" max="99" value="1">
            <button class="addToCartButton" data-product-id="<%=prodotto.getId()%>">Aggiungi al Carrello</button>
        </div>
    </div>

    <%-- Visualizza le recensioni --%>
    <div class="reviews">
        <h2 style="font-style: italic">Recensioni degli utenti</h2>

        <%-- Recupera le recensioni e i prodotti consigliati dal tuo database --%>
        <% try(RecensioneDAO recensioneDAO = new RecensioneDAO()){
                List<RecensioneBean> recensioni = (List<RecensioneBean>) recensioneDAO.doRetrieveByProduct(prodotto.getId());

                if(recensioni.isEmpty()){ %>
                    <center><h3 style="font-style: italic">Nessuna recensione disponibile!</h3></center>
                <% }else{
                    for (RecensioneBean recensione : recensioni) { %>
                        <div class="review">
                            <h4>Utente n.<%=recensione.getIdUtente()%></h4>
                            <h3><%=recensione.getTitolo()%> - Valutazione: <%=recensione.getValutazione()%></h3>
                            <p><%=recensione.getCommento()%></p>
                            <p>Data: <%=recensione.getData()%></p>
                        </div>
                    <% }

                }
            }catch(Exception e){
                e.printStackTrace();
        }%>
    </div>

    <%-- Visualizza i prodotti consigliati --%>
    <h2 style="font-style: italic">Potrebbe piacerti...</h2>
    <div class="recommended-products">
        <%  try(ProdottoDAO prodottoDAO = new ProdottoDAO()){
                List<ProdottoBean> prodottiConsigliati = (List<ProdottoBean>) prodottoDAO.doRetrieveAllByCategory(prodotto.getCategoria());

                for (ProdottoBean prod : prodottiConsigliati) { %>
                    <div class="product">
                        <img src="<%=prod.getImgPath()%>" alt="<%=prod.getNome()%>">
                        <h4><%=prod.getNome()%></h4>
                        <p>Prezzo: <%=prod.getPrezzo()%> €</p>
                    </div>
                <% }
            }catch(Exception e){
                e.printStackTrace();
        }%>
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
