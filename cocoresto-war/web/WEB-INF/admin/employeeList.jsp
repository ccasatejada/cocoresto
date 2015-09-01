<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Gérer les employés</title>
    </head>
    <body>
        <div class="container-fluid" id="employeeList">
        <h1>Liste des employés</h1>
        <table class="table table-striped">
            <tr>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Poste</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach var="employee" items="${employees}" varStatus="loop">
                <tr>
                    <td>${employee.lastName}</td>
                    <td>${employee.firstName}</td>
                    <td>${employee.employeeGroup}</td>
                    <td>
                    <div class="btn btn-default">
                        <a href="FrontController?option=employee&task=modify&id=${employee.id}" name="modifyIt">Modifier</a>
                    </div>
                    </td>
                    <td>
                    <div class="btn btn-default">
                        <a href="FrontController?option=employee&task=delete&id=${employee.id}" name="deleteIt">Supprimer</a>
                    </div>
                    </td>
                </tr>                   
            </c:forEach>

        </table>

        <div class="btn btn-default">
            <a href="FrontController?option=employee&task=edit" name="addEmp">Ajouter</a>
        </div>
        
        </div>

        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
