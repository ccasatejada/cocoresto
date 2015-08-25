<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administration Plat</title>
    </head>
    <body>
        <h1>Administration Plat</h1>

        <form>
            <div class="form-group">
                <label for="name">Nom :</label>
                <input type="text" class="form-control" id="name" value="${dish.name}">
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea class="form-control" rows="3" maxlength="500" id="description" name="description" value="${dish.description}"></textarea>
            </div>
            <div class="form-group">
                <label for="weight">Poids :</label>
                <input type="text" class="form-control" id="weight" value="${dish.weight}">
                <div class="input-group-addon">grammes</div>
            </div>
            <input type="submit" class="btn btn-primary" name="confirm">
        </form>
        <%@include file="../includes/scripts.jsp" %>

    </body>
</html>
