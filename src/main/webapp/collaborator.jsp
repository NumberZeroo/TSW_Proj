<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Collaboratori - DarwinShop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/collab.css">

    <%@ include file="navbar.jsp" %>
</head>
<body>

<div class="collabContainer">
    <div class="descrizione">
        <h1>Collabora con DarwinShop</h1>
        <p>Noi di DarwinShop crediamo nell'importanza della collaborazione per offrire i migliori prodotti e servizi per i nostri amici animali. Siamo grati ai nostri collaboratori che condividono la nostra passione e ci aiutano a crescere ogni giorno.</p>
    </div>

    <div class="section">
        <h2>I nostri Collaboratori</h2>
        <div class="row">
            <div class="col">
                <div class="collaborator">
                    <img src="img/collab/enpa.png" alt="ENPA">
                    <h4>ENPA</h4>
                </div>
            </div>
            <div class="col">
                <div class="collaborator">
                    <img src="img/collab/STD.jpg" alt="STD">
                    <h4>STD</h4>
                </div>
            </div>
            <div class="col">
                <div class="collaborator">
                    <img src="img/collab/WRS.jpg" alt="WorldRise">
                    <h4>WorldRise</h4>
                </div>
            </div>
        </div>
    </div>

    <div class="section">
        <p class="text-center">Informati sui nostri collaboratori visitando i loro profili individuali.</p>
    </div>

</div>

<%@ include file="footer.jsp" %>

</body>
</html>
