<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>

        <title>Panneau d'administration</title>
    </head>
    <body id="minovate" class="appWrapper">
        <div id="wrap" class="animsition">
            <%@include file="../includes/adminMenu.jsp" %>
            <section id="content">
                <div class="page page-dashboard">

                    <div class="pageheader">
                        <h2>CocoResto <span>// Administrateur</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li><a href="FrontController"><i class="fa fa-home"></i> CocoResto</a></li>
                                <li><a href="FrontController?option=dashboard">Panneau d'administration</a></li>
                                <li><a href="FrontController?option=dish">Gestion des plats</a></li>
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
                            <ul class="controls">
                                <li>
                                    <a href="FrontController?option=dish&task=edit" role="button" tabindex="0" id="add-entry"><i class="fa fa-plus mr-5"></i> Ajouter</a>
                                </li>
                            </ul>
                        </div>
                        <div class="tile-body p-0">
                            <table class="table table-striped">
                                <thead>
                                    <tr class="bg-slategray">
                                        <th hidden>Id</th>
                                        <th>Nom</th>
                                        <th>Stock</th>
                                        <th>Région</th>
                                        <th>Poids</th>
                                        <th>Catégorie</th>
                                        <th>Prix</th>
                                        <th>Taxe</th>                    
                                        <th>Promotion</th>
                                        <th>Prix total</th>
                                        <th>Modifier</th>
                                        <th>Supprimer</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="dish" items="${dishes}" varStatus="loop">
                                        <tr>
                                            <td hidden>${dish.id}</td>
                                            <td>${dish.name}</td>
                                            <td>${dish.inventory}</td>
                                            <td>${dish.country}</td>
                                            <td>${dish.weight}</td>
                                            <td>${dish.category.name}</td>
                                            <td>${dish.price.price}</td>
                                            <td>${dish.tax.rate}</td>
                                            <td>${dish.discount.rate} (${dish.discount.beginDate} - ${dish.discount.endDate})</td>
                                            <td>${dish.totalPrice}</td>
                                            <td><a href="FrontController?option=dish&task=edit&id=${dish.id}">Modifier</a></td>
                                            <td><a href="FrontController?option=dish&task=delete&id=${dish.id}">Supprimer</a></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <div class="tile-footer dvd dvd-top">
                            <div class="row">
                                <div class="col-xs-12 text-right">
                                    <ul class="pagination pagination-sm m-0">
                                        <li><a href=""><i class="fa fa-chevron-left"></i></a></li>
                                        <li><a href="">1</a></li>
                                        <li><a href="">2</a></li>
                                        <li><a href="">3</a></li>
                                        <li><a href=""><i class="fa fa-chevron-right"></i></a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>    
                    </section>
                </div>
            </section>
        </div>
        <%@include file="../includes/scripts.jsp" %>

    </body>
</html>
