
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Administration Catégorie</title>
    </head>
    <body>
        <h1>Administration Catégorie</h1>

        <form action="FrontController?option=category" method="post">
            <div class="form-group">
                <label for="name">Nom de la catégorie :</label>
                <input type="text" class="form-control" id="name" name="nameCategory" value="${category.name}">
            </div>
            <input type="submit" class="btn btn-primary" name="confirm">
        </form>
        <%@include file="../includes/scripts.jsp" %>

    </body>
</html>
