<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Recap</title>
    <script src="${pageContext.request.contextPath}/scripts/scrollController.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/scrollableContainer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/defaultProductAdvices.css">
</head>
<body>
    <%@include file="navbar.jsp"%>

    <h1>Grazie per aver comprato da noi</h1>
    <h3>Potrebbero anche interessarti...</h3>
    <div class="scroll-container">
        <button class="scroll-button left">&#9664;</button>
        <%@include file="productAdvices.jsp"%>
        <button class="scroll-button right">&#9654;</button>
    </div>

    <%@include file="footer.jsp"%>
</body>
</html>
