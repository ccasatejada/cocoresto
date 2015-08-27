<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>

        <title>Edition : plat</title>
    </head>
    <body>
        <c:if test="${not empty dish}">
            <h1>Edition du plat : ${dish.name}</h1>
        </c:if>
        <c:if test="${empty dish}">
            <h1>Ajouter un plat</h1>
        </c:if>
        <div class="container-fluid">
            ${alert}
            <form action="FrontController?option=dish" method="post">
                <input type="hidden" value="${dish.id}" name="id"> 

                <div class="form-group">
                    <label for="dishName"  class="col-sm-2 control-label">Nom :</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="dishName" name="dishName" required value="${dish.name}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description"  class="col-sm-2 control-label">Description</label>
                    <div class="col-sm-10">
                        <textarea class="form-control" rows="3" maxlength="255" id="description" name="description" required>${dish.description}</textarea>
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
                        <div class="input-group">
                            <input type="text" class="form-control" id="dishWeight" name="dishWeight" required value="${dish.weight}">
                            <div class="input-group-addon">grammes</div>
                        </div>
                    </div>
                </div>
                <c:if test="${not empty dish.nutritiveValues}">            
                    <c:forEach var="nutritiveValue" items="${dish.nutritiveValues}" varStatus="loop"> 
                        <div class="form-group">
                            <label for="dishKcal"  class="col-sm-2 control-label">Kilocalories :</label>
                            <div class="col-sm-10">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="dishKcal" name="dishKcal" value="<c:if test="${nutritiveValue.name == 'kilocalories'}">${nutritiveValue.quantity}</c:if>">
                                        <div class="input-group-addon">grammes</div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="dishProtein"  class="col-sm-2 control-label">Proteines :</label>
                                <div class="col-sm-10">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="dishProtein" name="dishProtein" value="<c:if test="${nutritiveValue.name == 'protéines'}">${nutritiveValue.quantity}</c:if>">
                                        <div class="input-group-addon">grammes</div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="dishGlucid"  class="col-sm-2 control-label">Glucides :</label>
                                <div class="col-sm-10">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="dishGlucid" name="dishGlucid" value="<c:if test="${nutritiveValue.name == 'glucides'}">${nutritiveValue.quantity}</c:if>">
                                        <div class="input-group-addon">grammes</div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="dishLipid"  class="col-sm-2 control-label">Lipides :</label>
                                <div class="col-sm-10">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="dishLipid" name="dishLipid" value="<c:if test="${nutritiveValue.name == 'lipides'}">${nutritiveValue.quantity}</c:if>">
                                        <div class="input-group-addon">grammes</div>
                                    </div>
                                </div>
                            </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty dish.nutritiveValues}">
                    <div class="form-group">
                        <label for="dishKcal"  class="col-sm-2 control-label">Kilocalories :</label>
                        <div class="col-sm-10">
                            <div class="input-group">
                                <input type="text" class="form-control" id="dishKcal" name="dishKcal">
                                <div class="input-group-addon">grammes</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dishProtein"  class="col-sm-2 control-label">Proteines :</label>
                        <div class="col-sm-10">
                            <div class="input-group">
                                <input type="text" class="form-control" id="dishProtein" name="dishProtein">
                                <div class="input-group-addon">grammes</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dishGlucid"  class="col-sm-2 control-label">Glucides :</label>
                        <div class="col-sm-10">
                            <div class="input-group">
                                <input type="text" class="form-control" id="dishGlucid" name="dishGlucid">
                                <div class="input-group-addon">grammes</div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dishLipid"  class="col-sm-2 control-label">Lipides :</label>
                        <div class="col-sm-10">
                            <div class="input-group">
                                <input type="text" class="form-control" id="dishLipid" name="dishLipid">
                                <div class="input-group-addon">grammes</div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="dishCategory"  class="col-sm-2 control-label">Catégorie :</label>
                    <div class="col-sm-10">
                        <select id="dishCategory" name="dishCategory">
                            <c:forEach var="category" items="${categories}" varStatus="loop"> 
                                <option name="category" value="${category.id}" <c:if test="${dish.category.name eq category.name}">selected</c:if>>${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dishPrice"  class="col-sm-2 control-label">Prix :</label>
                    <div class="col-sm-10">
                        <div class="input-group">
                            <input type="text" class="form-control" id="dishPrice" name="dishPrice" required value="${dish.price.price}">
                            <div class="input-group-addon">€</div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dishTax"  class="col-sm-2 control-label">Taxe :</label>
                    <div class="col-sm-10">
                        <select id="dishTax" name="dishTax">
                            <c:forEach var="tax" items="${taxes}" varStatus="loop"> 
                                <option name="tax" value="${tax.id}" <c:if test="${dish.tax.id == tax.id}">selected</c:if>>${tax}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dishDiscount"  class="col-sm-2 control-label">Promotion :</label>
                    <div class="col-sm-10">
                        <div class="input-group">
                            <input type="text" class="form-control" id="dishDiscount" name="dishDiscount" value="${dish.discount.rate}">
                            <div class="input-group-addon">%</div>
                        </div>
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
                <div class="form-group">
                    <label for="imageDish" class="col-sm-2 control-label">Image :</label>
                    <div class="col-sm-10">                       
                        <input type="file" id="imageDish" name="imageDish" accept="image/jpeg"/>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary" name="confirm">Valider</button>
            </form>

            <%@include file="../includes/scripts.jsp" %>
        </div>
    </body>
</html>
