<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chi Siamo?</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/chiSiamo.css">
    <%@ include file="navbar.jsp" %>
</head>
<body>
<div class="descrizione">
    <h1>Benvenuti su DarwinShop</h1>
    <p>Siamo Ema ed Ema, due appassionati di animali che hanno creato un luogo speciale per i vostri amici a quattro zampe.</p>
    <p>Offriamo una vasta gamma di prodotti di alta qualità per cani, gatti, uccelli, roditori e altri animali domestici. La nostra missione è rendere la vita degli animali e dei loro proprietari più felice e facile.</p>
    <p>Grazie per aver scelto DarwinShop. Siamo qui per aiutare voi e i vostri animali a vivere meglio.</p>
</div>

<div class="EandE">
    <div class="Ema">
        <img src="${pageContext.request.contextPath}/img/EmaEma/Ema.jpg" alt="Amore Emanuele Luigi">
        <p>"La grandezza di una nazione e il suo progresso morale si possono giudicare dal modo in cui tratta i suoi animali."</p>
    </div>

    <div class="Ema">
        <img src="${pageContext.request.contextPath}/img/EmaEma/EmaAltro.jpg" alt="Emanuele Falanga">
        <p>"Gli animali non sono tutta la nostra vita, ma la rendono completa."</p>
    </div>
</div>

<%@ include file="footer.jsp"%>
</body>
</html>
