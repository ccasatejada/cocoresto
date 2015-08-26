
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Edition : table</title>
    </head>
    <body>
        <c:if test="${not empty customerTable.number}">
            <h1>Edition de la table n°${customerTable.number}</h1>
        </c:if>
        <c:if test="${empty customerTable.number}">
            <h1>Ajouter une table</h1>
        </c:if>
        <div class="container-fluid">
            ${alert}
            <form action="FrontController?option=customerTable" method="post">
                <input type="hidden" name="id" value="${customerTable.id}">
                <div class="form-group">
                    <label for="number">Numéro de table :</label>
                    <input type="number" min="1" max="30" class="form-control" id="number" name="number" value="${customerTable.number}">
                </div>
                <div class="form-group">
                    <label for="capacity">Capacité de la table :</label>
                    <input type="number" min="1" max="10" class="form-control" id="capacity" name="capacity" value="${customerTable.capacity}">
                </div>
                <div class="form-group">
                    <label for="nbTablet">Nombre de tablettes :</label>
                    <input type="number" min="1" max="20" class="form-control" id="nbTablet" name="nbTablet" value="${customerTable.nbTablet}">
                </div>
                <button name="confirm" type="submit" class="btn btn-primary">Valider</button>
            </form>
            <%@include file="../includes/scripts.jsp" %>
        </div>
    </body>
</html>
