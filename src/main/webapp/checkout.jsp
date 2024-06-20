<%@ page import="com.tswproject.tswproj.SessionFacade" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="model.prodotto.ProdottoDAO" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Checkout</title>
</head>
<body>
    <%
        SessionFacade userSession = new SessionFacade(request.getSession());
        Map<Long, Integer> productIDs = userSession.getCartProducts();
        Map<ProdottoBean, Integer> products = new HashMap<>();
        try(ProdottoDAO prodottoDAO = new ProdottoDAO()) {
            products = prodottoDAO.doRetrieveByKeys(productIDs);
        } catch (SQLException s){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // TODO: log
        }

        for (Map.Entry<ProdottoBean, Integer> entry : products.entrySet()) {
    %>
    <div class="product-details-box">
        <h3><%=entry.getKey().getNome()%></h3>
        <p>Quantità: <%=entry.getValue()%></p>
        <p>Prezzo singolo: <%=entry.getKey().getPrezzo()%></p>
        <p>Totale per prodotto: <%=entry.getKey().getPrezzo() * entry.getValue()%></p>
    </div>
    <br>
    <%}%>

    <p>Prezzo totale: <%=products.entrySet().stream().mapToDouble(e -> e.getKey().getPrezzo() * e.getValue()).sum()%></p>

    <p>Inserisci qui le informazioni di consegna</p>
    <!--TODO: sanifica input -->
    <!--TODO: Aggiungi info di default -->
    <form method="post" action="${pageContext.request.contextPath}/checkout">
        <label for="city-input">Città: </label><input id="city-input" type="text" name="city">
        <label for="cap-input">CAP: </label><input id="cap-input" type="number" name="cap">
        <label for="address-input">Indirizzo (via, corso, ...): </label><input id="address-input" type="text" name="address">
        <label for="additional-info-input">Informazioni di recapito aggiuntive: </label><input id="additional-info-input" type="text" name="other">
        <label for="receiver-input">Destinatario: </label><input id="receiver-input" type="text" name="receiver">
        <button type="submit">Conferma</button>
    </form>
</body>
</html>
