<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.ordine.*" %>
<%@ page import="model.ordine.OrdineBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="model.orderItem.OrderItemDAO" %>
<%@ page import="model.orderItem.OrderItemBean" %>
<%@ page import="model.prodotto.ProdottoDAO" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.tswproject.tswproj.RuntimeSQLException" %>

<html>
<head>
    <title>Storico Ordini Utente</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/adminPage.css">
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="content profile-container">
    <% SessionFacade sessionFacade = new SessionFacade(request.getSession()); %>
    <div class="menu-row">
        <button onclick="window.location.href='adminPage.jsp'">Torna alla pagina admin</button>
    </div>

    <div class="profile-content">
        <div class="profile-column">
            <div id="ordersSection">
                <h1>Storico Ordini</h1>
                <%
                    try (OrderItemDAO orderItemDAO = new OrderItemDAO()) {
                        Collection<OrdineBean> ordini = (Collection<OrdineBean>) request.getAttribute("ordini");
                        if (ordini.isEmpty()) { %>
                            <div class="empty-order">
                                <i class="fas fa-box-open"></i>
                                <p>Il cliente non ha effettuato nessun ordine</p>
                            </div>
                        <% } %>
                <%
                    Collection<OrderItemBean> orderItems = null;
                    for (OrdineBean ordine : ordini) {
                        orderItems = orderItemDAO.doRetrieveByOrder(ordine.getId());
                %>
                <div class="order-box">
                    <div class="order-header">
                        <p>Data Ordine: <%= ordine.getData() %>
                        </p>
                        <p>Totale
                            Ordine: <%= orderItems.stream().map(OrderItemBean::getPrezzo).reduce(Double::sum).orElse(0.0) %>
                        </p>
                        <p>ID Ordine: <%= ordine.getId() %>
                        </p>
                    </div>
                    <%
                        for (OrderItemBean orderItem : orderItems) {
                    %>
                    <%
                        try (ProdottoDAO prodottoDAO = new ProdottoDAO()) {
                            ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(orderItem.getIdProdotto());
                    %>
                    <div class="order-item">
                        <% if (prodotto.isVisibile()) { %>
                        <a href="product?id=<%= prodotto.getId() %>">
                            <img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">
                        </a>
                        <% } else { %>
                        <img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">
                        <% } %>
                        <div class="order-item-details">
                            <p><%= orderItem.getNome() %>
                            </p>
                            <p><%= orderItem.getPrezzo() %>
                            </p>
                            <p>Quantità: <%= orderItem.getQuantita() %>
                            </p>
                        </div>
                    </div>
                    <%
                        } catch (SQLException s) {
                            throw new RuntimeSQLException("Errore durante il recupero delle informazioni del prodotto", s);
                        }
                    %>
                    <%
                        }
                    %>
                    <div class="order-footer">
                        <form method="get" action="getInvoice">
                            <input type="hidden" name="orderId" value="<%=ordine.getId()%>">
                            <button type="submit" class="download-invoice">Scarica Fattura</button>
                        </form>
                    </div>
                </div>

                <%
                        }
                    } catch (SQLException s) {
                        throw new RuntimeSQLException("Errore durante il recupero degli ordini", s);
                    }
                %>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>