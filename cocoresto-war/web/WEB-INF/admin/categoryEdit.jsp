<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Administration Catégorie</title>
    </head>
    <body>
        <h1>Administration Catégorie</h1>
        <div class="container-fluid">
            ${alert}

            <form action="FrontController?option=category" method="post" class="well form-horizontal">
                <input type="hidden" value="${category.id}" name="id"> 
                <div class="form-group">
                    <label for="name"  class="col-sm-2 control-label">Nom de la catégorie :</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="name" name="nameCategory" value="${category.name}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="categoryType"  class="col-sm-2 control-label">Catégorie :</label>
                    <div class="col-sm-10">
                        <select id="categoryType" name="dishCategory"> 
                            <option value="Plat" <c:if test="${category.type == 'Plat'}"> selected </c:if>> Plat</option>
                            <option value="Boisson"  <c:if test="${category.type == 'Boisson'}"> selected </c:if> > Boisson</option>
                        </select>
                    </div> 
                </div>    
                <button type="submit" class="btn btn-default" name="confirm">OK</button>
            </form>

        </div>
        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
