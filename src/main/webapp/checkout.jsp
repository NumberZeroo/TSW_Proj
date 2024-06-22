<%@ page import="com.tswproject.tswproj.SessionFacade" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="model.prodotto.ProdottoDAO" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="model.infoConsegna.InfoConsegnaDAO" %>
<%@ page import="model.infoConsegna.InfoConsegnaBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.tswproject.tswproj.RuntimeSQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Checkout</title>
</head>
<body>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/checkout.css">
    <script src="${pageContext.request.contextPath}/scripts/checkout.js" type="module"></script>

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

    <%
        InfoConsegnaBean defaultInfoConsegna;
        try(InfoConsegnaDAO infoConsegnaDAO = new InfoConsegnaDAO()) {
            defaultInfoConsegna = infoConsegnaDAO.doRetrieveDefault(userSession.getUserId());
        } catch(SQLException s){
            throw new RuntimeSQLException("Problema durante la ricerca dei dati di spedizione dell'utente", s);
        }


    %>
        <div id="shipment-options">
            <%if (defaultInfoConsegna != null){%>
            <form id = "shipment-info-form" name="shipment-selection">
                <input name="g1" type="radio" id="radio_<%=defaultInfoConsegna.getId()%>" value="<%=defaultInfoConsegna.getId()%>" checked="checked">
                <div id="shipment-selection-<%=defaultInfoConsegna.getId()%>">
                    <label for="radio_<%=defaultInfoConsegna.getId()%>"  class="shipment-option">
                        <strong><%=defaultInfoConsegna.getVia()%></strong> <br>
                                <%=defaultInfoConsegna.getCitta()%><br>
                                <%=defaultInfoConsegna.getDestinatario()%> <br>
                    </label>
                </div>
            </form>
            <button id="get-shipment-infos-btn">Modifica</button> <!-- TODO: ajax deve togliere le default info e aggiungere tutte le info  -->
            <button id="add-shipment-infos-btn" hidden>Aggiungi metodo di spedizione</button>
            <%} else {%>
            <p>Inserisci qui le informazioni di consegna</p>
            <!--TODO: sanifica input -->
            <form method="post" action="${pageContext.request.contextPath}/checkout">
                <label for="city-input">Città: </label><input id="city-input" type="text" name="city"> <br>
                <label for="cap-input">CAP: </label><input id="cap-input" type="number" name="cap"><br>
                <label for="address-input">Indirizzo (via, corso, ...): </label><input id="address-input" type="text" name="address"><br>
                <label for="additional-info-input">Informazioni di recapito aggiuntive: </label><input id="additional-info-input" type="text" name="other"><br>
                <label for="receiver-input">Destinatario: </label><input id="receiver-input" type="text" name="receiver"><br>
                <input name="willBeDefault" type="hidden" value="1">
                <button type="submit">Conferma</button>
            </form>
            <% } %>
        </div>

    <!-- TODO: questo bottone dovrebbe essere cliccabile solo se è stato selezionato qualcosa
                tramite js imposta il value all'id del radio selezionato -->
    <form method="post" action="${pageContext.request.contextPath}/checkout">
        <input id="selected-option" type="hidden" value="-1">
    </form>
</body>
</html>
