<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <body id="minovate" class="appWrapper">
        <div id="wrap" class="animsition">
            <%@include file="../includes/adminMenu.jsp" %>
            <section id="content">
                <div class="page page-dashboard">

                    <div class="pageheader">
                        <h2>CocoResto <span>// Administrateur - ${userName}</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li><a href="FrontController?option=dashboard"><i class="fa fa-home"></i> CocoResto</a></li>
                                <li><a href="FrontController?option=dashboard">Panneau d'administration</a></li>
                                <li><a href="FrontController?option=combo">Gestion des menus</a></li>
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
                            <h1 class="custom-font"><strong>Commandes</strong></h1>
                        </div>
                        <form action="FrontController?option=customerOrder" method="post">
                            <div class="tile-body">
                                <input type="hidden" value="${combo.id}" name="id" /> 
                                <div class="form-group">
                                    <label for="name">Nom du menu :</label>
                                    <input type="text" class="form-control" id="name" name="nameCombo" value="${combo.name}">
                                </div>
                                <div class="form-group">
                                    <label for="comboCategory">Catégorie :</label>
                                    <select id="comboCategory" name="comboCategory">
                                        <c:forEach var="category" items="${categories}" varStatus="loop"> 
                                            <option name="category" value="${category.id}" <c:if test="${combo.category.name eq category.name}">selected</c:if>>${category.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="comboDish1">Entrée :</label>
                                    <select id="comboDish1" name="comboDish1">
                                        <c:forEach var="dish1" items="${dishes1}" varStatus="loop"> 
                                            <option name="dish1" value="${dish1.id}" <c:if test="${combo.dishes.dish.category.name eq 'Entree'}">selected</c:if>>${dishes1.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="comboDish2">Plat :</label>
                                    <select id="comboDish2" name="comboDish2">
                                        <c:forEach var="dish2" items="${dishes2}" varStatus="loop"> 
                                            <option name="dish2" value="${dish2.id}" <c:if test="${combo.dishes.dish.category.name eq 'Plat'}">selected</c:if>>${dishes2.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="comboDish3">Dessert :</label>
                                    <select id="comboDish3" name="comboDish3">
                                        <c:forEach var="dish3" items="${dishes3}" varStatus="loop"> 
                                            <option name="dish3" value="${dish1.id}" <c:if test="${combo.dishes.dish.category.name eq 'Dessert'}">selected</c:if>>${dishes3.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="comboPrice">Prix :</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="comboPrice" name="comboPrice" required value="${combo.price.price}">
                                        <div class="input-group-addon">€</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="comboTax">Taxe :</label>
                                    <select id="comboTax" name="comboTax">
                                        <c:forEach var="tax" items="${taxes}" varStatus="loop"> 
                                            <option name="tax" value="${tax.id}" <c:if test="${combo.tax.id == tax.id}">selected</c:if>>${tax}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="comboDiscount">Promotion :</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="comboDiscount" name="comboDiscount" value="${combo.discount.rate}">
                                        <div class="input-group-addon">%</div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="comboBeginDiscount">Date de début :</label>
                                    <input type="text" class="form-control" id="comboBeginDiscount" name="comboBeginDiscount" value="<fmt:formatDate value="${combo.discount.beginDate}" pattern="dd-MM-yyyy"/>" placeholder="jj-mm-aaaa">
                                </div>
                                <div class="form-group">
                                    <label for="comboEndDiscount">Date de fin :</label>
                                    <input type="text" class="form-control" id="comboEndDiscount" name="comboEndDiscount" value="<fmt:formatDate value="${combo.discount.endDate}" pattern="dd-MM-yyyy" />" placeholder="jj-mm-aaaa">
                                </div>
                            </div>
                            <div class="tile-footer dvd dvd-top">
                                <div class="row">
                                    <div class="col-xs-12 text-right">
                                        <a href="FrontController?option=combo" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</a></button>
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
