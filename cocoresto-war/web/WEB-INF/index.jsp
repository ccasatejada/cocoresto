<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="includes/head.jsp" %>
        <title>Connexion</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-sm-4 col-sm-push-4">
                    <h1>Connexion</h1>
                    ${alert}
                    <form action="FrontController?option=login" class="well" method="post">
                        <c:if test="${not logged}">
                            <div class="form-group">
                                <label>Code &nbsp;<input type="password" name="password" maxlength="4" /></label>
                            </div>
                            <button class="btn btn-primary" type="submit" name="connect">Se connecter</button>
                        </c:if>
                        <c:if test="${logged}">
                            <button class="btn btn-primary" type="submit" name="disconnect">Se déconnecter</button>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
        <%@include file="includes/scripts.jsp" %>
    </body>
</html>
