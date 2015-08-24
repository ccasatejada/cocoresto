
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Gérer les employés</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <table>
            <tr>
                <th>Nom</th>
                <th>Prénom</th> 
            </tr>
            <c:forEach var="employee" items="" varStatus="loop">
                <tr>
                    <td>${lastName}</td>
                    <td>${firstName}</td>
                </tr>                   
            </c:forEach>

        </table>

        <div class="btn btn-default">
            <a href="FrontController?option=employee&task=edit" name="addEmp">Ajouter</a>
        </div>

        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
