
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Editer l'employé</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="FrontController?option=employee" method="POST">
            <div class="form-group">
            <label>Groupe</label>
            <select name="comboGroupEmployee">
                <option>Serveur</option>
                <option>Cuisinier</option>
            </select>
            </div>
            <div class="form-group">
            <label for="lastName">Nom : </label>
            <input type="text" name="lastName"> 
            </div>
            <div class="form-group">
            <label for="firstName">Prénom : </label>
            <input type="text" name="firstName">
            </div>
            <div class="form-group">
            <label for="password">Mot de passe : </label>
            <input type="password" name="password"> 
            </div>
            <div class="form-group">
            <label for="confirmPassword">Confirmer mot de passe : </label>
            <input type="password" name="confirmPassword"> 
            </div>
            <button type="submit" class="btn btn-default">Ajouter</button>
        </form>
        
        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
