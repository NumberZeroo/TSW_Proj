<%@ page import="model.prodotto.ProdottoBean" %><%--
  Created by IntelliJ IDEA.
  User: developer
  Date: 6/7/24
  Time: 8:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>
<html>
<head>
    <%
        ProdottoBean prodotto = (ProdottoBean) request.getAttribute("product");
    %>
    <title><%=prodotto.getNome()%></title>
</head>
<body>
    <h2>Da decidere lo stile</h2>
    <h3><%=prodotto.getNome()%></h3>
    <p><%=prodotto.getPrezzo()%></p>
    <!--
    <form action="aggiungiAlCarrello" method="post">
        <input type="hidden" name="id" value="<%=prodotto.getId()%>">
        <button type="submit">Aggiungi al Carrello</button>
    </form> -->

    <button class="addToCartButton" data-product-id="<%=prodotto.getId()%>">Aggiungi al Carrello</button>

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
