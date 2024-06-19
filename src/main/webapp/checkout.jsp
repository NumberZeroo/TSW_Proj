<%@ page import="com.tswproject.tswproj.SessionFacade" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="model.prodotto.ProdottoDAO" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: developer
  Date: 6/19/24
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
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

</body>
</html>
