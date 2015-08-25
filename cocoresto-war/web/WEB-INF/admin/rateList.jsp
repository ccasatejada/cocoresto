<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Liste des taxes et discounts</title>
    </head>
    <body>
        <div class="container-fluid">
        <h1>Liste des taxes</h1>
        <table class="table table-striped">
            <tr>
                <th>Identifiant</th>
                <th>Montant</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="tax" items="${taxes}" varStatus="loop">
                <tr>
                    <td>${tax.id}</td>
                    <td>${tax.rate}</td>
                    <td>
                    <div class="btn btn-default">
                        <a href="FrontController?option=rate&task=modifyTax&id=${tax.id}" name="modifyIt">Modifier</a>
                    </div>
                    </td>
                    <td>
                    <div class="btn btn-default">
                        <a href="FrontController?option=rate&task=deleteTax&id=${tax.id}" name="deleteIt">Supprimer</a>
                    </div>
                    </td>
                </tr>                   
            </c:forEach>

        </table>

        <div class="btn btn-default">
            <a href="FrontController?option=rate&task=editTax" name="addTax">Ajouter</a>
        </div>
        
        </div>
        
        <div class="container-fluid">
        <h1>Liste des discounts</h1>
        <table class="table table-striped">
            <tr>
                <th>Identifiant</th>
                <th>Montant</th>
                <th>Date début</th>
                <th>Date fin</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="discount" items="${discounts}" varStatus="loop">
                <tr>
                    <td>${discount.id}</td>
                    <td>${discount.rate}</td>
                    <td>${discount.beginDate}</td>
                    <td>${discount.endDate}</td>
                    <td>
                    <div class="btn btn-default">
                        <a href="FrontController?option=rate&task=modifyDiscount&id=${discount.id}" name="modifyDiscount">Modifier</a>
                    </div>
                    </td>
                    <td>
                    <div class="btn btn-default">
                        <a href="FrontController?option=rate&task=deleteDiscount&id=${discount.id}" name="deleteDiscount">Supprimer</a>
                    </div>
                    </td>
                </tr>                   
            </c:forEach>

        </table>

        <div class="btn btn-default">
            <a href="FrontController?option=rate&task=editDiscount" name="addDiscount">Ajouter</a>
        </div>
        
        </div>

        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
