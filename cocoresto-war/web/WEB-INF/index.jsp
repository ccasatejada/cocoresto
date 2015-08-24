<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="includes/head.jsp" %>
        <title>Connexion</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="well">
                <h1>Connexion</h1>
                <form action="" method="post">
                    <div class="form-group">
                        <label>Code secret&nbsp;<input type="password" maxlength="4"  /></label>
                    </div>
                </form>
            </div>
        </div>
        <%@include file="includes/scripts.jsp" %>
    </body>
</html>
