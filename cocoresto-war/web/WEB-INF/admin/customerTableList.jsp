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
                                <li><a href="FrontController?option=customerTable">Gestion des tables</a></li>
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
                            <h1 class="custom-font"><strong>Tables</strong></h1>
                            <ul class="controls">
                                <li>
                                    <a href="FrontController?option=customerTable&task=add" role="button" tabindex="0" id="add-entry"><i class="fa fa-plus mr-5"></i> Ajouter</a>
                                </li>
                            </ul>
                        </div>

                        <div class="tile-body p-0">
                            <table class="table table-striped">
                                <thead>
                                    <tr class="bg-slategray">
                                        <th>Numéro de table</th>
                                        <th>Nombre de places</th>
                                        <th>Nombre de tablettes</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>                    
                                    <c:forEach var="customerTable" items="${customerTables}" varStatus="loop">
                                        <tr>
                                            <td>Table n°${customerTable.number}</td>
                                            <td>${customerTable.capacity} places</td>
                                            <td>${customerTable.nbTablet} tablettes</td>
                                            <td>
                                                <a href="FrontController?option=customerTable&task=edit&id=${customerTable.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-edit"></i> <span>Modifier</span></a>
                                                <a href="FrontController?option=customerTable&task=delete&id=${customerTable.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-trash"></i> <span>Supprimer</span></a>
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
