
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
        <div class="container-fluid">
            ${alert}
            <form action="FrontController?option=category" method="post">
                <div class="form-group">
                    <label for="name"  class="col-sm-2 control-label">Nom de la catégorie :</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="name" name="nameCategory" value="${category.name}">
                    </div>
                </div>
                <input type="submit" class="btn btn-primary" name="confirm">
            </form>
            <%@include file="../includes/scripts.jsp" %>
        </div>
    </body>
</html>