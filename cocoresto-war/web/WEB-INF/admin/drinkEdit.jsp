<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Editer la boisson</title>
    </head>
    <body>
        <div class="container-fluid">
            <form action="FrontController?option=drink" class="well form-horizontal" method="POST">
                <c:if test="${not empty drink}">
                    <h1>Modifier Boisson</h1>
                </c:if>
                <c:if test="${empty drink}">
                    <h1>Nouvelle Boisson</h1>
                </c:if>

                <div class="form-group">
                    <label for="formatsList" class="col-sm-2 control-label">Format</label>
                    <div class="col-sm-10">
                        <c:if test="${empty drink}">
                            <c:forEach var="format" items="${formats}" varStatus="loop">
                                <input type="checkbox" name="formatsList${loop.index}" value="${format.id}">${format.name}
                            </c:forEach>
                        </c:if>
                        <c:if test="${not empty drink}">
                            <c:forEach var="format" items="${uncheckedFormats}" varStatus="loop">
                                <input type="checkbox" name="listUncheck${loop.index}" value="${format.id}">${format.name}
                            </c:forEach>
                            <c:forEach var="formatDrink" items="${drink.formats}" varStatus="loop">
                                <input type="checkbox" name="listCheck${loop.index}" value="${formatDrink.id}" checked>${formatDrink.name}
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
                <div class="form-group">
                    <label for="comboCategory" class="col-sm-2 control-label">Catégorie</label>
                    <div class="col-sm-10">
                        <select name="comboCategory" class="form-control">
                            <c:forEach var="category" items="${categories}" varStatus="loop">
                                <c:if test="${not empty drink}">
                                    <c:if test="${drink.category.id==category.id}">
                                        <option selected>${category.name}</option>
                                    </c:if>
                                    <c:if test="${drink.category.id!=category.id}">
                                        <option>${category.name}</option>
                                    </c:if>
                                </c:if>

                                <c:if test="${empty drink}">
                                    <option>${category.name}</option>
                                </c:if>   
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">Nom : </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="name" value="${drink.name}"> 
                    </div>
                </div>
                <div class="form-group">
                    <label for="inventory" class="col-sm-2 control-label">Stock : </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="inventory" value="${drink.inventory}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-2 control-label">Description : </label>
                    <div class="col-sm-10">
                        <textarea class="form-control" rows="3" maxlength="500" name="description">${drink.description}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="comboDiscount" class="col-sm-2 control-label">Discount : </label>
                    <div class="col-sm-10">
                        <select name="comboDiscount" class="form-control">
                            <c:if test="${empty drink.discount || empty drink}">
                                <option value="empty"></option>
                            </c:if>
                            <c:forEach var="discount" items="${discounts}" varStatus="loop">
                                <c:if test="${drink.discount.id==discount.id}">
                                    <option value="${discount.id}" selected>${discount.rate} / ${discount.beginDate} / ${discount.endDate}</option>
                                </c:if>
                                <c:if test="${drink.discount.id!=discount.id}">
                                    <option value="${discount.id}">${discount.rate} / ${discount.beginDate} / ${discount.endDate}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <div class="btn btn-default">
                            <a href="FrontController?option=rate&task=editDiscount" name="addDiscount">Nouveau Discount</a>
                        </div>
                        <c:if test="${not empty drink && not empty drink.discount}">
                            <div class="btn btn-default">
                                <a href="FrontController?option=drink&task=deleteDiscountDrink&id=${drink.id}" name="deleteDiscountDrink">Détacher Discount</a>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="form-group">
                    <label for="comboTax" class="col-sm-2 control-label">Taxe : </label>
                    <div class="col-sm-10">
                        <select name="comboTax" class="form-control">
                            <c:forEach var="tax" items="${taxes}" varStatus="loop">
                                <c:if test="${not empty drink}">
                                    <c:if test="${drink.tax.id==tax.id}">
                                        <option selected>${tax.rate}</option>
                                    </c:if>
                                    <c:if test="${drink.tax.id!=tax.id}">
                                        <option>${tax.rate}</option>
                                    </c:if>
                                </c:if>

                                <c:if test="${empty drink}">
                                    <option name="${tax.id}">${tax.rate}</option>
                                </c:if>   
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="price" class="col-sm-2 control-label">Prix Unitaire HT : </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="price" value="${drink.price}">
                    </div>
                </div>
                <c:if test="${not empty drink}">
                    <div class="form-group">
                        <label for="totalPrice" class="col-sm-2 control-label">Prix TTC : </label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="totalPrice" value="${drink.totalPrice}" disabled>
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="image" class="col-sm-2 control-label">Image : </label>
                    <div class="col-sm-10">                       
                        <input type="file" name="image" accept="image/jpeg" value="${drink.image}"/>
                    </div>
                </div>
                <c:if test="${not empty drink}">
                    <button type="submit" class="btn btn-default" name="modifyIt">Modifier</button>
                </c:if>
                <c:if test="${empty drink}">
                    <button type="submit" class="btn btn-default" name="createIt">Valider</button>
                </c:if>
                <button type="submit" class="btn btn-default" name="cancelIt">Annuler</button>

            </form>
        </div>

        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
