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
                        <h2>CocoResto <span>// Administrateur</span></h2>
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li><a href="FrontController?option=dashboard"><i class="fa fa-home"></i> CocoResto</a></li>
                                <li><a href="FrontController?option=dashboard">Panneau d'administration</a></li>
                                <li><a href="FrontController?option=customerOrder">Gestion des commandes</a></li>
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

                        <div class="tile-body p-0">
                            <table class="table table-striped">
                                <thead>
                                    <tr class="bg-slategray">
                                        <th>Numéro de la commande</th>
                                        <th>Date</th>
                                        <th>Statut</th>
                                        <th>Couverts</th>
                                        <th>Table</th>
                                        <th>Tablettes</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>                    
                                    <c:forEach var="customerOrder" items="${customerOrders}" varStatus="loop">
                                        <tr>
                                            <td>Table n°${customerOrder.number}</td>
                                            <td>${customerOrder.orderDate}</td>
                                            <td>${customerOrder.status}</td>
                                            <td>${customerOrder.people} personnes</td>
                                            <td>Table n°${customerOrder.customerTable.number}</td>
                                            <td>${customerOrder.nbTablet} tablettes</td>
                                            <td>
                                                <a href="FrontController?option=customerOrder&task=edit&id=${customerOrder.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-edit"></i> <span>Modifier</span></a>
                                                <a href="FrontController?option=customerOrder&task=delete&id=${customerOrder.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-trash"></i> <span>Supprimer</span></a>
                                            </td>
                                        </tr>                                    
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        ${pagination}

                    </section>
                </div>
            </section>
        </div>
        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
