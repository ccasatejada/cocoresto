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
                    <a href="#" class="btn btn-module text-center">
                        <i class="glyphicon glyphicon-folder-open"></i>
                        <br/>Categories
                    </a>
                </div>
                <div class="col-sm-3">
                    <a href="#" class="btn btn-module text-center">
                        <i class="fa fa-user"></i>
                        <br/>Plats
                    </a>
                </div>
                <div class="col-sm-3">
                    <a href="#" class="btn btn-module text-center">
                        <i class="glyphicon glyphicon-glass"></i>
                        <br/>Boissons
                    </a>
                </div>
                <div class="col-sm-3">
                    <a href="#" class="btn btn-module text-center">
                        <i class="fa fa-user"></i>
                        <br/>Menus
                    </a>
                </div>
            </div>
            
            <div class="row">
                <div class="col-sm-3">
                    <a href="#" class="btn btn-module text-center">
                        <i class="glyphicon glyphicon-user"></i>
                        <br/>Utilisateurs
                    </a>
                </div>
                <div class="col-sm-3">
                    <a href="#" class="btn btn-module text-center">
                        <i class="fa fa-user"></i>
                        <br/>Tables
                    </a>
                </div>
                <div class="col-sm-3">
                    <a href="#" class="btn btn-module text-center">
                        <i class="fa fa-user"></i>
                        <br/>Commandes
                    </a>
                </div>
                <div class="col-sm-3">
                    <a href="#" class="btn btn-module text-center">
                        <i class="glyphicon glyphicon-certificate"></i>
                        <br/>Promos
                    </a>
                </div>
            </div>
            
            
        </div>
        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>