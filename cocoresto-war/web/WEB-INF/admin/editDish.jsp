<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>

        <title>Administration Plat</title>
    </head>
    <body>
        <h1>Administration Plat</h1>

        <form action="FrontController?option=dish" method="post">
            <div class="form-group">
                <label for="dishName">Nom :</label>
                <input type="text" class="form-control" id="dishName" name="dishName" required value="${dish.name}">
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea class="form-control" rows="3" maxlength="500" id="description" name="description" required value="${dish.description}"></textarea>
            </div>
            <div class="form-group">
                <label for="dishCountry">Région :</label>
                <input type="text" class="form-control" id="dishCountry" name="dishCountry" required value="${dish.country}">
            </div>
            <div class="form-group">
                <label for="dishWeight">Poids :</label>
                <input type="text" class="form-control" id="dishWeight" name="dishWeight" required value="${dish.weight}">
                <div class="input-group-addon">grammes</div>
            </div>
            <div class="form-group">
                <label for="dishCountry">Catégorie :</label>
                <select id="dishCategory">
                    <c:forEach var="category" items="${categories}" varStatus="loop"> 
                        <option>${category.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="dishPrice">Prix :</label>
                <input type="text" class="form-control" id="dishWeight" name="dishWeight" required value="${dish.price.price}">
                <div class="input-group-addon">€</div>
            </div>
            <div class="form-group">
                <label for="dishTax">Taxe :</label>
                <input type="text" class="form-control" id="dishWeight" name="dishWeight" required value="${dish.tax.rate}">
                <div class="input-group-addon">%</div>
            </div>
            <div class="form-group">
                <label for="dishDiscount">Promotion :</label>
                <input type="text" class="form-control" id="dishDiscount" name="dishDiscount" value="${dish.discount.rate}">
                <div class="input-group-addon">%</div>
            </div>
            <div class="form-group">
                <label for="dishBeginDiscount">Date de début :</label>
                <input type="text" class="form-control" id="dishBeginDiscount" name="dishBeginDiscount" value="${dish.discount.beginDate}" placeholder="jj-mm-aaaa">
            </div>
            <div class="form-group">
                <label for="dishEndDiscount">Date de fin :</label>
                <input type="text" class="form-control" id="dishEndDiscount" name="dishEndDiscount" value="${dish.discount.endDate}" placeholder="jj-mm-aaaa">
            </div>
            <input type="submit" class="btn btn-primary" name="confirm">
        </form>
        <%@include file="../includes/scripts.jsp" %>

    </body>
</html>
