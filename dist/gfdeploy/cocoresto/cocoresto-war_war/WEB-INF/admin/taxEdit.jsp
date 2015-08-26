<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Editer la taxe</title>
    </head>
    <body>
        <div class="container-fluid">
            <form action="FrontController?option=rate" class="well form-horizontal" method="POST">
                <c:if test="${not empty tax}">
                    <h1>Modifier Taxe</h1>
                </c:if>
                <c:if test="${empty tax}">
                    <h1>Nouvelle Taxe</h1>
                </c:if>

                
                <div class="form-group">
                    <label for="amount" class="col-sm-2 control-label">Montant : </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="amount" value="${tax.rate}"> 
                    </div>
                </div>
                
                <c:if test="${not empty tax}">
                    <button type="submit" class="btn btn-default" name="modifyTax">Modifier</button>
                </c:if>
                <c:if test="${empty tax}">
                    <button type="submit" class="btn btn-default" name="createTax">Valider</button>
                </c:if>
                <button type="submit" class="btn btn-default" name="cancelTax">Annuler</button>

            </form>
        </div>

        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
