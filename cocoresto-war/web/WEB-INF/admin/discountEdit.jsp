<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Editer le discount</title>
    </head>
    <body>
        <div class="container-fluid">
            <form action="FrontController?option=rate" class="well form-horizontal" method="POST">
                <c:if test="${not empty discount}">
                    <h1>Modifier Discount</h1>
                </c:if>
                <c:if test="${empty discount}">
                    <h1>Nouveau Discount</h1>
                </c:if>

                
                <div class="form-group">
                    <label for="amount" class="col-sm-2 control-label">Montant : </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="amount" value="${discount.rate}"> 
                    </div>
                </div>
                <div class="form-group">
                    <label for="beginDate" class="col-sm-2 control-label">Date début : </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="beginDate" value="${discount.beginDate}"> 
                    </div>
                </div>
                <div class="form-group">
                    <label for="endDate" class="col-sm-2 control-label">Montant : </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="endDate" value="${discount.endDate}"> 
                    </div>
                </div>
                
                <c:if test="${not empty discount}">
                    <button type="submit" class="btn btn-default" name="modifyDiscount">Modifier</button>
                </c:if>
                <c:if test="${empty discount}">
                    <button type="submit" class="btn btn-default" name="createDiscount">Valider</button>
                </c:if>
                <c:if test="${not empty drink}">
                    <button type="submit" class="btn btn-default" name="attachDiscount">Attacher Discount</button>
                    <!--<a href="FrontController?option=drink&task=attachDiscount&id=${drink.id}" name="attachDiscount">Attacher Discount</a>-->
                </c:if>    
                <button type="submit" class="btn btn-default" name="cancelDiscount">Annuler</button>

            </form>
        </div>

        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
