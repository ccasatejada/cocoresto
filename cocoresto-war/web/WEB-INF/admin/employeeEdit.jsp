
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Editer l'employé</title>
    </head>
    <body>
        <form action="FrontController?option=employee" class="well form-horizontal" method="POST">
            <div class="form-group">
                <label for="comboGroupEmployee" class="col-sm-2 control-label">Groupe</label>
                <div class="col-sm-10">
                <select name="comboGroupEmployee" class="form-control">
                    <option>Serveur</option>
                    <option>Cuisinier</option>
                </select>
                </div>
            </div>
            <div class="form-group">
                <label for="lastName" class="col-sm-2 control-label">Nom : </label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="lastName"> 
                </div>
            </div>
            <div class="form-group">
                <label for="firstName" class="col-sm-2 control-label">Prénom : </label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="firstName">
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-sm-2 control-label">Mot de passe : </label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" name="password"> 
                </div>
            </div>
            <div class="form-group">
                <label for="confirmPassword" class="col-sm-2 control-label">Confirmer mot de passe : </label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" name="confirmPassword"> 
                </div>
            </div>
            <button type="submit" class="btn btn-default">Ajouter</button>
        </form>

        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
