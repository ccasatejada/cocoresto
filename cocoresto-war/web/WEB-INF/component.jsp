<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="includes/head.jsp" %>
        <title>Panneau d'administration</title>
    </head>
    <body class="contentpane">
        <div class="tile">
        <jsp:include page="${content}" />
        </div>
    </body>
</html>