<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.prodotto.*" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>HomePage</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/home.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/defaultProductAdvices.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/scrollableContainer.css">
    <script src="${pageContext.request.contextPath}/scripts/scrollController.js"></script>
</head>
<body>
<%@ include file="navbar.jsp" %>

<main>
    <div class="descrizione">
        <h1>Benvenuti su DarwinShop</h1>
        <p>Scoprite i migliori prodotti per i vostri amici pelosi. Dalla pappa ai giocattoli, abbiamo tutto per rendere
            felici i vostri animali. Buono shopping!</p>
        <a href="${pageContext.request.contextPath}/mostraCatalogoServlet">Vai al catalogo</a>
    </div>
    <section>
        <h2>Potrebbe piacerti...</h2>
        <div class="scroll-container">
            <button class="scroll-button left"><i class="fas fa-chevron-left"></i></button>
            <%@include file="productAdvices.jsp" %>
            <button class="scroll-button right"><i class="fas fa-chevron-right"></i></button>
        </div>
    </section>

    <section>
        <h2>Alimentari</h2>
        <div class="scroll-container">
            <button class="scroll-button left"><i class="fas fa-chevron-left"></i></button>
            <div class="product-container gallery">
                <% try (ProdottoDAO prodottoDAO = new ProdottoDAO()) {
                    List<ProdottoBean> prodottiConsigliati = (List<ProdottoBean>) prodottoDAO.doRetrieveAllByCategory("Alimentari");

                    for (ProdottoBean prod : prodottiConsigliati) {
                        if (prod.isVisibile()) {%>
                <div class="product">
                    <a href="product?id=<%=prod.getId()%>">
                        <img src="<%=prod.getImgPath()%>" alt="<%=prod.getNome()%>">
                        <h4><%=prod.getNome()%>
                        </h4>
                    </a>
                    <p>Prezzo: <%=prod.getPrezzo()%> €</p>
                </div>
                <% }
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }%>
            </div>
            <button class="scroll-button right"><i class="fas fa-chevron-right"></i></button>
        </div>
    </section>

    <section>
        <h2>Giocattoli</h2>
        <div class="scroll-container">
            <button class="scroll-button left"><i class="fas fa-chevron-left"></i></button>
            <div class="product-container gallery">
                <% try (ProdottoDAO prodottoDAO = new ProdottoDAO()) {
                    List<ProdottoBean> prodottiConsigliati = (List<ProdottoBean>) prodottoDAO.doRetrieveAllByCategory("Giocattoli");

                    for (ProdottoBean prod : prodottiConsigliati) {
                        if (prod.isVisibile()) {%>
                <div class="product">
                    <a href="product?id=<%=prod.getId()%>">
                        <img src="<%=prod.getImgPath()%>" alt="<%=prod.getNome()%>">
                        <h4><%=prod.getNome()%>
                        </h4>
                    </a>
                    <p>Prezzo: <%=prod.getPrezzo()%> €</p>
                </div>
                <% }
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }%>
            </div>
            <button class="scroll-button right"><i class="fas fa-chevron-right"></i></button>
        </div>
    </section>
</main>

<%@ include file="footer.jsp" %>
</body>
</html>