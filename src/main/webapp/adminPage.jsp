<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.utente.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.tswproject.tswproj.RuntimeSQLException" %>
<%@ page import="model.prodotto.*" %>
<%@ page import="model.ordine.OrdineDAO" %>
<%@ page import="model.ordine.OrdineBean" %>
<%@ page import="model.orderItem.OrderItemDAO" %>
<%@ page import="model.orderItem.OrderItemBean" %>
<%@ page import="java.util.Collection" %>
<html>
<head>
    <title>Profilo Utente</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/adminPage.css">
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="content profile-container">
    <% SessionFacade sessionFacade = new SessionFacade(request.getSession()); %>
    <div class="menu-row">
        <button id="infoButton">Elenco utenti</button>
        <%--        <button id="ordersButton">Disponibiltà</button>--%>
        <button id="allOrderButton">Ordini</button>
        <button id="availabilityButton">Disponibilità</button>

        <form action="logout" method="post">
            <input type="submit" value="Logout" id="logoutButton">
        </form>
    </div>

    <div class="profile-content">
        <div class="profile-column">
            <div id="infoSection" style="display: none;">
                <div class="utenti">
                    <div class="utente-header">
                        <h1>Elenco utenti</h1>
                    </div>
                    <% try (UtenteDAO utenteDAO = new UtenteDAO()) { %>
                    <% List<UtenteBean> utenti = (List<UtenteBean>) utenteDAO.doRetrieveAll("ASC"); %>
                    <% if (!utenti.isEmpty()) { %>
                    <table>
                        <thead>
                        <tr>
                            <th></th> <!-- Colonna per l'immagine -->
                            <th>Username</th>
                            <th>Email</th>
                            <th>Admin</th>
                            <th>Storico</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% for (UtenteBean utente : utenti) {
                            if (utente.getId() != sessionFacade.getUserId()) {%>
                        <tr>
                            <td><img class="utente-image" src="<%= utente.getImgPath() %>"
                                     alt="<%= utente.getUsername() %>">
                            </td> <!-- Immagine dell'utente -->
                            <td><%= utente.getUsername() %>
                            </td>
                            <td><%= utente.getEmail() %>
                            </td>
                            <td>
                                <!-- button modifica admin -->
                                <form action="changeUserRoleServlet" method="post">
                                    <input type="hidden" name="userId" value="<%= utente.getId() %>">
                                    <input type="hidden" name="makeAdmin" value="<%= !utente.getIsAdmin() %>">
                                    <input type="submit" class="changeAdminButton"
                                           value="<%= utente.getIsAdmin() ? "Rimuovi admin" : "Rendi admin" %>"
                                           onclick="confirmChangeAdmin(event, '<%= utente.getId() %>', '<%= !utente.getIsAdmin() %>')">
                                </form>
                            </td>
                            <td>
                                <!-- Form che porta alla pagina di storico ordini dell'utente cliccato -->
                                <form action="orderHistory" method="post">
                                    <input type="hidden" name="userId" value="<%= utente.getId() %>">
                                    <input type="submit" class="orderHistoryButton" value="Storico ordini">
                                </form>
                            </td>
                        </tr>
                        <% }
                        } %>
                        </tbody>
                    </table>
                    <% }
                    } catch (SQLException e) {
                        throw new RuntimeSQLException("Errore durante il recupero delle informazioni degli utenti", e);
                    } %>
                </div>
            </div>
            <div id="availabilitySection" class="dispSection" style="display: none;">
                <h1>Disponibilità</h1>
                <% try (ProdottoDAO prodottoDAO = new ProdottoDAO()) { %>
                <% List<ProdottoBean> prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveAll("ASC"); %>
                <% if (!prodotti.isEmpty()) { %>
                <% for (ProdottoBean prodotto : prodotti) { %>
                <div class="item-box">
                    <div class="item-image">
                        <img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">
                    </div>
                    <div class="item-details">
                        <h2 class="item-name"><%= prodotto.getNome() %>
                        </h2>
                        <p class="item-price"><%= prodotto.getPrezzo() %>
                        </p>
                        <p class="item-quantity">Disponibilità: <%= prodotto.getDisponibilita() %>
                    </div>
                    <button class="refillButton <%= prodotto.getDisponibilita() < 5 ? "lowStock" : "" %>"
                            data-id="<%= prodotto.getId() %>" onclick="showForm('<%= prodotto.getId() %>')">Refill
                    </button>
                    <form id="refillForm<%= prodotto.getId() %>" class="refillForm" action="refillProductServlet"
                          method="post"
                          style="display: none;">
                        <input type="hidden" name="productId" value="<%= prodotto.getId() %>">
                        <input type="number" name="quantity" min="1" placeholder="Quantità">
                        <input type="submit" value="Conferma" class="refillFormButton">
                    </form>
                </div>
                <% } %>
                <% } %>
                <% } catch (SQLException e) {
                    throw new RuntimeSQLException("Errore durante il recupero delle informazioni dei prodotti", e);
                } %>
            </div>

            <div id="allOrderSection" class="allOrderSection" style="display: none;">
                <div class="allOrderHeader">
                    <h1>Ordini</h1>
                    <form id="orderByDate" method="post" enctype="multipart/form-data"
                          action="${pageContext.request.contextPath}/orderByDate">
                        <div class="dateDiv">
                            <label for="startDate">Inizia:</label>
                            <input id="startDate" type="date" name="startDate">
                        </div>
                        <div class="dateDiv">
                            <label for="endDate"> Fino a:</label>
                            <input id="endDate" type="date" name="endDate">
                        </div>
                        <input type="submit" class="allOrderButton" value="Filtra">
                    </form>
                </div>
                <% try (OrdineDAO ordineDAO = new OrdineDAO(); OrderItemDAO orderItemDAO = new OrderItemDAO()) {
                    String startDate = request.getParameter("startDate");
                    String endDate = request.getParameter("endDate");

                    if (startDate == null || endDate == null) {
                        startDate = "";
                        endDate = "";
                    }

                    List<OrdineBean> ordini = (List<OrdineBean>) ordineDAO.doRetrieveByDate(startDate, endDate);
                    if (ordini.isEmpty()) { %>
                        <div class="empty-order">
                            <i class="fas fa-box-open"></i>
                            <p>I clienti non hanno effettuato ordini...</p>
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

<script>
    function showForm(productId) {
        var form = document.getElementById('refillForm' + productId);
        var button = document.querySelector('.refillButton[data-id="' + productId + '"]');
        if (form.style.display === 'none') {
            form.style.display = 'block';
            button.style.display = 'none';
        } else {
            form.style.display = 'none';
            button.style.display = 'block';
        }
    }
</script>

<script>
    // Seleziona i pulsanti e le sezioni
    let infoButton = document.getElementById('infoButton');
    // let ordersButton = document.getElementById('ordersButton');

    let infoSection = document.getElementById('infoSection');
    // let ordersSection = document.getElementById('ordersSection');

    let availabilityButton = document.getElementById('availabilityButton');
    let availabilitySection = document.getElementById('availabilitySection');

    let allOrderButton = document.getElementById('allOrderButton');
    let allOrderSection = document.getElementById('allOrderSection');

    availabilityButton.addEventListener('click', function () {
        showSection('availabilitySection');
    });

    allOrderButton.addEventListener('click', function () {
        showSection('allOrderSection');
    });

    // Funzione per nascondere tutte le sezioni
    function hideAllSections() {
        infoSection.style.display = 'none';
        availabilitySection.style.display = 'none';
        allOrderSection.style.display = 'none';
        // ordersSection.style.display = 'none';
    }

    // Funzione per mostrare una sezione specifica
    function showSection(sectionId) {
        hideAllSections();
        let section = document.getElementById(sectionId);
        section.style.display = 'block';
    }

    // Aggiungi gestori di eventi di click ai pulsanti
    infoButton.addEventListener('click', function () {
        showSection('infoSection');
    });

    // ordersButton.addEventListener('click', function () {
    //     showSection('ordersSection');
    // });

    showSection('allOrderSection');
</script>

<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script>
    function confirmChangeAdmin(event, userId, makeAdmin) {
        event.preventDefault();  // Previene l'invio del form
        var form = event.target.form;  // Ottiene il form

        swal({
            title: "Sei sicuro?",
            text: "Vuoi davvero cambiare il ruolo di questo utente?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willChange) => {
                if (willChange) {
                    form.submit();  // Invia il form
                }
            });
    }
</script>

</body>
</html>