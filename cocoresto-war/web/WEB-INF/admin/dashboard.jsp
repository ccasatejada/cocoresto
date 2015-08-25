<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="../includes/head.jsp" %>
        <title>Panneau d'administration</title>
    </head>
    <body>
        <%@include file="../includes/adminMenu.jsp" %>
        <div class="container-fluid">
            ${alert}

            <div class="row">
                <div class="col-sm-3">
                    <a href="FrontController?option=category" class="btn btn-module text-center">
                        <i class="fa fa-folder"></i>
                        <br/>Categories
                    </a>
                </div>
                <div class="col-sm-3">
                    <a href="FrontController?option=dish" class="btn btn-module text-center">
                        <i class="fa fa-cutlery"></i>
                        <br/>Plats
                    </a>
                </div>
                <div class="col-sm-3">
                    <a href="FrontController?option=drink" class="btn btn-module text-center">
                        <i class="fa fa-glass"></i>
                        <br/>Boissons
                    </a>
                </div>
                <div class="col-sm-3">
                    <a href="FrontController?option=combo" class="btn btn-module text-center">
                        <i class="fa fa-leanpub"></i>
                        <br/>Menus
                    </a>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-3">
                    <a href="FrontController?option=employee" class="btn btn-module text-center">
                        <i class="fa fa-users"></i>
                        <br/>Utilisateurs
                    </a>
                </div>
                <div class="col-sm-3">
                    <a href="FrontController?option=customerTable" class="btn btn-module text-center">
                        <i class="fa fa-connectdevelop"></i>
                        <br/>Tables
                    </a>
                </div>
                <div class="col-sm-3">
                    <a href="FrontController?option=customerOrder" class="btn btn-module text-center">
                        <i class="fa fa-money"></i>
                        <br/>Commandes
                    </a>
                </div>
                <div class="col-sm-3">
                    <a href="FrontController?option=discount" class="btn btn-module text-center">
                        <i class="fa fa-certificate"></i>
                        <br/>Promos
                    </a>
                </div>
            </div>


        </div>
        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>