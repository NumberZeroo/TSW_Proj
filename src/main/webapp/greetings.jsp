<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recap</title>
    <script src="${pageContext.request.contextPath}/scripts/scrollController.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/scrollableContainer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/defaultProductAdvices.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/greetings.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
<%@include file="navbar.jsp" %>

<div class="greetings-container">
    <h1><i class="fas fa-check-circle"></i> Grazie per aver acquistato da noi</h1>
</div>

<h2> Potrebbe anche interessarti...</h2>
<div class="scroll-container">
    <button class="scroll-button left"><i class="fas fa-chevron-left"></i></button>
    <%@include file="productAdvices.jsp" %>
    <button class="scroll-button right"><i class="fas fa-chevron-right"></i></button>
</div>

<%@include file="footer.jsp" %>
</body>
</html>