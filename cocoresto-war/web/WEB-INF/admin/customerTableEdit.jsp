
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Edition : table</title>
    </head>
    <body>
        <h1>Edition de la table n°${customerTable.number}</h1>
        <div class="container-fluid">
            ${alert}
            <form action="FrontController?option=customerTable" method="post">
                <div class="form-group">
                    <label for="name">xxx :</label>
                    <input type="text" class="form-control" id="name" name="nameCategory" value="${customerTable.numéro}">
                </div>
                <button type="submit" class="btn btn-primary">Valider</button>
            </form>
            <%@include file="../includes/scripts.jsp" %>
        </div>
    </body>
</html>
