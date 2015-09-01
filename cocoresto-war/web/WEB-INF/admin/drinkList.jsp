<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../includes/head.jsp" %>
        <title>Liste des boissons</title>
    </head>
    <body id="minovate" class="appWrapper">
        <div id="wrap" class="animsition">
            <%@include file="../includes/adminMenu.jsp" %>
            <section id="content">
                <div class="page page-dashboard">
                
                <table class="table table-striped">
                    <tr>
                        <th>Nom</th>
                        <th>Stock</th>
                        <th>Catégorie</th>
                        <th>Prix unitaire</th>
                        <th>Taxe</th>
                        <th>Discount</th>
                        <th>Prix total</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <c:forEach var="drink" items="${drinks}" varStatus="loop">
                        <tr>
                            <td>${drink.name}</td>
                            <td>${drink.inventory}</td>
                            <td>${drink.category}</td>
                            <td>${drink.price}</td>
                            <td>${drink.tax}</td>
                            <td>${drink.discount}</td>
                            <td>${drink.totalPrice}</td>
                            <td>
                                <div class="btn btn-default">
                                    <a href="FrontController?option=drink&task=modify&id=${drink.id}" name="modifyIt">Modifier</a>
                                </div>
                            </td>
                            <td>
                                <div class="btn btn-default">
                                    <a href="FrontController?option=drink&task=delete&id=${drink.id}" name="deleteIt">Supprimer</a>
                                </div>
                            </td>
                        </tr>                   
                    </c:forEach>

                </table>

                <div class="btn btn-default">
                    <a href="FrontController?option=drink&task=edit" name="addDrink">Ajouter</a>
                </div>
                </div>
            </section>
        </div>

        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
