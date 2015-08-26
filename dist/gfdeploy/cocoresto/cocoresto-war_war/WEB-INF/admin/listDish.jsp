
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>

        <title>Gestion des plats</title>
    </head>
    <body>
        <h1>Gestion des plats</h1>

        <a href="FrontController?option=dish&task=edit">Ajouter un plat</a>
        <table class="table table-striped">
            <caption>Plats</caption>
            <thead class="bg-info">
                <tr>
                    <th hidden>Id</th>
                    <th scope="col">Nom</th>
                    <th scope="col">Stock</th>
                    <th scope="col">Région</th>
                    <th scope="col">Poids</th>
                    <th scope="col">Catégorie</th>
                    <th scope="col">Prix</th>
                    <th scope="col">Taxe</th>
                    <th scope="col">Promotion</th>
                    <th scope="col">Modifier</th>
                    <th scope="col">Supprimer</th>
                </tr>
            </thead>
            <tbody id="dBody">
                <c:forEach var="dish" items="${dishes}" varStatus="loop">
                    <tr>
                        <td hidden>${dish.id}</td>
                        <td>${dish.name}</td>
                        <td>${dish.inventory}</td>
                        <td>${dish.country}</td>
                        <td>${dish.weight}</td>
                        <td>${dish.category.name}</td>
                        <td>${dish.price.price}</td>
                        <td>${dish.tax.rate}</td>
                        <td>${dish.discount.rate} (${dish.discount.beginDate} - ${dish.discount.endDate})</td>
                        <td><a href="FrontController?option=dish&task=edit&id=${dish.id}">Modifier</a></td>
                        <td><a href="FrontController?option=dish&task=delete&id=${dish.id}">Supprimer</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
