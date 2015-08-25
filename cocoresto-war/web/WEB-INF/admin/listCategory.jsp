
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestion des catégories</title>
    </head>
    <body>
        <h1>Gestion des catégories</h1>

        <a href="FrontController?option=category&task=edit">Ajouter une catégorie</a>
        <table class ="table table-striped">
            <caption>Catégories</caption>
            <thead class="bg-info">
                <tr>
                    <th hidden>Id</th>
                    <th scope="col">Catégorie</th>
                    <th scope="col">Liste de produit</th>
                    <th scope="col">Modifier</th>
                    <th scope="col">Supprimer</th>
                </tr>
            </thead>
            <tbody id="cBody">
                <c:forEach var="category" items="${categories}" varStatus="loop">
                    <tr>
                        <td hidden>${category.id}</td>
                        <td>${category.name}</td>
                        <td><a href="FrontController?option=dish">Liste de produits</a></td>
                        <td><a href="FrontController?option=category&task=edit&id=${category.id}">Modifier</a></td>
                        <td><a href="FrontController?option=category&task=delete&id=${category.id}">Supprimer</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
