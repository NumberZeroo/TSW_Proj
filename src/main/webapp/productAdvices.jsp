<%@ page import="model.prodotto.ProdottoDAO" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="java.util.List" %>

<div class="product-container gallery">
    <%  try(ProdottoDAO prodottoDAO = new ProdottoDAO()){
        List<ProdottoBean> prodottiConsigliati = (List<ProdottoBean>) prodottoDAO.doRetrieveAll("ASC");

        for (ProdottoBean prod : prodottiConsigliati) { %>
    <div class="product">
        <a href="product?id=<%=prod.getId()%>">
            <img src="<%=prod.getImgPath()%>" alt="<%=prod.getNome()%>">
            <h4><%=prod.getNome()%></h4>
        </a>
        <p>Prezzo: <%=prod.getPrezzo()%> &#8364;</p>
    </div>
    <% } %>
    <% }catch(Exception e){ %>
    <h3>Errore di visualizzazione</h3>
    <% }%>
</div>