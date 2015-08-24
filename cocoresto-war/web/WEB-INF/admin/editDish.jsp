<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <form>
            <div class="form-group">
                <label for="name">Nom :</label>
                <input type="text" class="form-control" id="name">
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea class="form-control" rows="3" maxlength="500" id="description" name="description"></textarea>
            </div>
            <div class="form-group">
                <label for="weight">Poids :</label>
                <input type="text" class="form-control" id="weight">
                <div class="input-group-addon">grammes</div>
            </div>
        </form>
    </body>
</html>
