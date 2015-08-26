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
        <div class="container-fluid">
            ${alert}
            <form action="FrontController?option=dish" method="post">
                <div class="form-group">
                    <label for="dishName"  class="col-sm-2 control-label">Nom :</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="dishName" name="dishName" required value="${dish.name}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description"  class="col-sm-2 control-label">Description</label>
                    <div class="col-sm-10">
                    <textarea class="form-control" rows="3" maxlength="500" id="description" name="description" required value="${dish.description}"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dishInventory"  class="col-sm-2 control-label">Stock :</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="dishInventory" name="dishInventory" required value="${dish.inventory}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="dishCountry"  class="col-sm-2 control-label">Région :</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="dishCountry" name="dishCountry" required value="${dish.country}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="dishWeight"  class="col-sm-2 control-label">Poids :</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="dishWeight" name="dishWeight" required value="${dish.weight}">
                    <div class="input-group-addon">grammes</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dishCategory"  class="col-sm-2 control-label">Catégorie :</label>
                    <div class="col-sm-10">
                    <select id="dishCategory">
                        <c:forEach var="category" items="${categories}" varStatus="loop"> 
                            <option name="category" value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dishPrice"  class="col-sm-2 control-label">Prix :</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="dishPrice" name="dishPrice" required value="${dish.price.price}">
                    <div class="input-group-addon">€</div>
                    </div>>
                </div>
                <div class="form-group">
                    <label for="dishTax"  class="col-sm-2 control-label">Taxe :</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="dishTax" name="dishTax" required value="${dish.tax.rate}">
                    <div class="input-group-addon">%</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dishDiscount"  class="col-sm-2 control-label">Promotion :</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="dishDiscount" name="dishDiscount" value="${dish.discount.rate}">
                    <div class="input-group-addon">%</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dishBeginDiscount"  class="col-sm-2 control-label">Date de début :</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="dishBeginDiscount" name="dishBeginDiscount" value="${dish.discount.beginDate}" placeholder="jj-mm-aaaa">
                    </div>
                </div>
                <div class="form-group">
                    <label for="dishEndDiscount"  class="col-sm-2 control-label">Date de fin :</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="dishEndDiscount" name="dishEndDiscount" value="${dish.discount.endDate}" placeholder="jj-mm-aaaa">
                    </div>
                </div>
                <input type="submit" class="btn btn-primary" name="confirm">
            </form>
            <%@include file="../includes/scripts.jsp" %>
        </div>
    </body>
</html>
