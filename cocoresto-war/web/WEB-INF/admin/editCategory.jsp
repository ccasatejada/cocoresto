
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administration Catégorie</title>
    </head>
    <body>
        <h1>Administration Catégorie</h1>

        <form action="FrontController?option=category" method="post">
            <div class="form-group">
                <label for="name">Nom de la catégorie :</label>
                <input type="text" class="form-control" name="nameCategory" value="${category.name}">
            </div>
            <input type="submit" class="btn btn-primary" name="confirm">
        </form>
    </body>
</html>
