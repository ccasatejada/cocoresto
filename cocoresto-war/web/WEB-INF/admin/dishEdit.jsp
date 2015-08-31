<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>

        <title>Edition : plat</title>
    </head>
    <body id="minovate" class="appWrapper">
        <div id="wrap" class="animsition">
            <%@include file="../includes/adminMenu.jsp" %>
            <section id="content">
                <div class="page page-dashboard">

                    <div class="pageheader">
                        <h2>CocoResto <span>// Administrateur - ${userName}</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li><a href="FrontController"><i class="fa fa-home"></i> CocoResto</a></li>
                                <li><a href="FrontController?option=dashboard">Panneau d'administration</a></li>
                                <li><a href="FrontController?option=dish">Gestion des plats</a></li>
                                <li><a href="#">Edition</a></li>
                            </ul>
                            <div class="page-toolbar">
                                <a role="button" tabindex="0" class="btn btn-lightred no-border pickDate">
                                    <i class="fa fa-calendar"></i>&nbsp;&nbsp;<span>${date}</span>
                                </a>
                            </div>
                        </div>
                    </div>

                    ${alert}

                    <section class="tile">

                        <div class="tile-header dvd dvd-btm bg-greensea">
                            <h1 class="custom-font"><strong>Plats</strong></h1>
                        </div>
                        <form action="FrontController?option=dish" method="post">
                            <input type="hidden" value="${dish.id}" name="id"> 

                            <div class="tile-body">

                                <div class="form-group">
                                    <label for="dishName">Nom :</label>
                                    <input type="text" class="form-control" id="dishName" name="dishName" required value="${dish.name}">
                                </div>
                                <div class="form-group">
                                    <label for="description">Description</label>
                                    <textarea class="form-control" rows="3" maxlength="255" id="description" name="description" required>${dish.description}</textarea>
                                </div>
                                <div class="form-group">
                                    <label for="dishInventory">Stock :</label>
                                    <input type="text" class="form-control" id="dishInventory" name="dishInventory" required value="${dish.inventory}">
                                </div>
                                <div class="form-group">
                                    <label for="dishCountry">Région :</label>
                                    <input type="text" class="form-control" id="dishCountry" name="dishCountry" required value="${dish.country}">
                                </div>
                                <div class="form-group">
                                    <label for="dishWeight">Poids :</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="dishWeight" name="dishWeight" required value="${dish.weight}">
                                        <div class="input-group-addon">grammes</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dishKcal">Kilocalories :</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="dishKcal" name="dishKcal" value="${nutritiveValue[0]}">
                                        <div class="input-group-addon">kcal</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dishProtein">Proteines :</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="dishProtein" name="dishProtein" value="${nutritiveValue[1]}">
                                        <div class="input-group-addon">grammes</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dishGlucid">Glucides :</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="dishGlucid" name="dishGlucid" value="${nutritiveValue[2]}">
                                        <div class="input-group-addon">grammes</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dishLipid">Lipides :</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="dishLipid" name="dishLipid" value="${nutritiveValue[3]}">
                                        <div class="input-group-addon">grammes</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dishCategory">Catégorie :</label>
                                    <select id="dishCategory" name="dishCategory">
                                        <c:forEach var="category" items="${categories}" varStatus="loop"> 
                                            <option name="category" value="${category.id}" <c:if test="${dish.category.name eq category.name}">selected</c:if>>${category.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="dishPrice">Prix :</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="dishPrice" name="dishPrice" required value="${dish.price.price}">
                                        <div class="input-group-addon">€</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dishTax">Taxe :</label>
                                    <select id="dishTax" name="dishTax">
                                        <c:forEach var="tax" items="${taxes}" varStatus="loop"> 
                                            <option name="tax" value="${tax.id}" <c:if test="${dish.tax.id == tax.id}">selected</c:if>>${tax}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="dishDiscount">Promotion :</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="dishDiscount" name="dishDiscount" value="${dish.discount.rate}">
                                        <div class="input-group-addon">%</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dishBeginDiscount">Date de début :</label>
                                    <input type="text" class="form-control" id="dishBeginDiscount" name="dishBeginDiscount" value="<fmt:formatDate value="${dish.discount.beginDate}" pattern="dd-MM-yyyy"/>" placeholder="jj-mm-aaaa">
                                </div>
                                <div class="form-group">
                                    <label for="dishEndDiscount">Date de fin :</label>
                                    <input type="text" class="form-control" id="dishEndDiscount" name="dishEndDiscount" value="<fmt:formatDate value="${dish.discount.endDate}" pattern="dd-MM-yyyy" />" placeholder="jj-mm-aaaa">
                                </div>
                                <div class="form-group">
                                    <label for="imageDish" class="col-sm-2 control-label">Image :</label>
                                    <input type="file" id="imageDish" name="imageDish" accept="image/jpeg"/>
                                </div>
                            </div>
                            <div class="tile-footer dvd dvd-top">
                                <div class="row">
                                    <div class="col-xs-12 text-right">
                                        <a href="FrontController?option=dish" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</a></button>
                                        <button type="submit" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="confirm"><i class="fa fa-save"></i> <span>Valider</span></button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </section>
                </div>
            </section>
        </div>
        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
