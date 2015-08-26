<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Gestion des tables</title>
    </head>
    <body>
        <h1>Gestion des tables</h1>
        <div class="container-fluid">
            <a href="FrontController?option=customerTable&task=add" class="btn btn-success">Ajouter une table</a>
            <table class="table table-striped">
                <tr>
                    <th>Numéro</th>
                    <th>Capacité</th>
                    <th>Tablettes</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach var="customerTable" items="${customerTables}" varStatus="loop">
                    <tr>
                        <td>Table n°${customerTable.number}</td>
                        <td>${customerTable.capacity} personnes</td>
                        <td>${customerTable.nbTablet} tablettes</td>
                        <td>
                            <div class="btn btn-default">
                                <a href="FrontController?option=customerTable&task=edit&id=${customerTable.id}" name="modifyIt">Modifier</a>
                            </div>
                        </td>
                        <td>
                            <div class="btn btn-default">
                                <a href="FrontController?option=customerTable&task=delete&id=${customerTable.id}" name="deleteIt">Supprimer</a>
                            </div>
                        </td>
                    </tr>                   
                </c:forEach>

            </table>        
        </div>
        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
