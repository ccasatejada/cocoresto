
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>

        <title>Panneau d'administration</title>
    </head>
    <body>
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
                            <table class ="table table-striped">
                                <caption>Catégories</caption>
                                <thead class="bg-info">
                                    <tr>
                                        <th hidden>Id</th>
                                        <th scope="col">Catégorie</th>
                                        <th scope="col">Modifier</th>
                                        <th scope="col">Supprimer</th>
                                    </tr>
                                </thead>
                                <tbody id="cBody">
                                    <c:forEach var="category" items="${categories}" varStatus="loop">
                                        <tr>
                                            <td hidden>${category.id}</td>
                                            <td>${category.name}</td>
                                            <td><a href="FrontController?option=category&task=edit&id=${category.id}">Modifier</a></td>
                                            <td><a href="FrontController?option=category&task=delete&id=${category.id}">Supprimer</a></td>
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
