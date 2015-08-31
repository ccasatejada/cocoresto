<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../includes/head.jsp" %>
        <title>Panneau d'administration</title>
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
                                <li><a href="FrontController?option=dashboard"><i class="fa fa-home"></i> CocoResto</a></li>
                                <li><a href="FrontController?option=dashboard">Panneau d'administration</a></li>
                                <li><a href="FrontController?option=customerOrder">Gestion des commandes</a></li>
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

                                <fieldset>
                                    <legend>Informations</legend>

                                    <input type="hidden" name="id" value="${customerOrder.id}">
                                    <c:if test="${not empty customerOrder.number}">
                                        <div class="form-group">
                                            <label for="number">Numéro de commande : <span>*</span></label>
                                            <input type="text" class="form-control" id="number" name="number" value="${customerOrder.number}" readonly required />
                                        </div>
                                    </c:if>

                                    <c:if test="${not empty customerOrder.orderDate}">
                                        <div class="form-group">
                                            <label for="orderDate">Date de la commande : <span>*</span></label>
                                            <input type="text" class="form-control" id="orderDate" name="orderDate" value="<fmt:formatDate value="${customerOrder.orderDate}" pattern="dd MMM yyyy à hh:MM" />" readonly required />
                                        </div>
                                    </c:if>

                                    <div class="form-group">
                                        <label for="status">Statut de la commande : <span>*</span></label>
                                        <select class="form-control" id="status" name="status" required>
                                            <option value="">STATUS</option>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label for="employee">Créateur de la commande : <span>*</span></label>
                                        <select class="form-control" id="employee" name="employee" required>
                                            <option value="">EMPLOYE</option>
                                        </select>
                                    </div>

                                </fieldset>
                                <fieldset>
                                    <legend>Détails</legend>

                                    <div class="form-group">
                                        <label for="customerTable">Table de la commande : <span>*</span></label>
                                        <select class="form-control" id="customerTable" name="customerTable" required>
                                            <option value="iddetable">N° de table</option>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label for="people">Nombre de couverts : <span>*</span></label>
                                        <input type="number" min="1" class="form-control" id="people" name="people" value="${customerOrder.people}" required />
                                    </div>

                                    <div class="form-group">
                                        <label for="nbTablet">Nombre de tablettes : <span>*</span></label>
                                        <input type="number" min="1" class="form-control" id="nbTablet" name="nbTablet" value="${customerOrder.nbTablet}" required />
                                    </div>

                                </fieldset>
                                <fieldset>
                                    <legend>Panier</legend>
                                    
                                    

                                </fieldset>
                            </div>

                            <div class="tile-footer dvd dvd-top">
                                <div class="row">
                                    <div class="col-xs-12 text-right">
                                        <a href="FrontController?option=customerOrder" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</a></button>
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

