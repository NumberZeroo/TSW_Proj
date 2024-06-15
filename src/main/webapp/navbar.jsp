<%@ page import="com.tswproject.tswproj.SessionFacade" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" type="text/css" href="website.css">


<% boolean loggedIn = (new SessionFacade(request.getSession()).isLoggedIn()); %>

<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #ea944b">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/mostraCatalogoServlet">DarwinShop!</a>
        <button class="navbar-toggler" id="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="home.jsp">Home</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="#">Chi siamo</a> <!-- TODO: link to "about us" page -->
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="#">Collaboratori</a> <!-- TODO: link to "collaborators" page -->
                </li>
            </ul>

            <div class="d-flex">
<%--                <form class="d-flex ms-auto search-bar">--%>
<%--                    <input class="form-control me-2" type="search" placeholder="Cerca..." aria-label="Search">--%>
<%--                    <button class="btn btn-outline-success" type="submit">Invio</button>--%>
<%--                </form>--%>

                <div class="search-container">
                    <form class="search-form">
                        <input class="search-input" type="search" placeholder="Cerca..." aria-label="Search">
                        <button class="search-button" type="submit">
                            <i class="fas fa-search"></i>
                        </button>
                    </form>
                </div>

                <% if(loggedIn) { %>
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="profile.jsp">Profile
                            <i class="fas fa-user"></i>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="cart.jsp">
                            <i class="fas fa-shopping-cart"></i>
                        </a>
                    </li>
                </ul>

                <% } else { %>
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp">
                            <i class="fas fa-user"></i>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="cart.jsp">
                            <i class="fas fa-shopping-cart"></i>
                        </a>
                    </li>
                </ul>
                <% } %>
            </div>
        </div>
    </div>
</nav>