<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="includes/head.jsp" %>
        <title>Connexion</title>
    </head>
    <body>
        <div class="container-fluid">
            <h1 class="text-center text-white">CocoResto</h1>
        </div>
        <div class="module">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-4 col-sm-push-4">
                        <h2>Connexion</h2>
                        ${alert}
                        <form action="FrontController?option=login" method="post">
                            <c:if test="${not logged}">
                                <div class="form-group">
                                    <label>Code &nbsp;<input type="password" name="password" maxlength="4" /></label>
                                </div>
                                <button class="btn btn-primary" type="submit" name="connect">Se connecter</button>
                            </c:if>
                            <c:if test="${logged}">
                                <a class="btn btn-primary" href="FrontController?option=login&task=disconnect" name="disconnect">Se déconnecter</a>
                            </c:if>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <%@include file="includes/scripts.jsp" %>
    </body>
</html>
