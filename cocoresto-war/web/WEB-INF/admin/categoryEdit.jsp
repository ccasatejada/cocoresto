<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Edition : catégorie</title>
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
                                <li><a href="FrontController?option=customerTable">Gestion des tables</a></li>
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
                            <h1 class="custom-font"><strong>Tables</strong></h1>
                        </div>
                        <form action="FrontController?option=category" method="post">
                            <div class="tile-body">
                                <input type="hidden" value="${category.id}" name="id"> 
                                <div class="form-group">
                                    <label for="name">Nom de la catégorie :</label>
                                    <input type="text" class="form-control" id="name" name="nameCategory" value="${category.name}">
                                </div>
                                <div class="form-group">
                                    <label for="categoryType">Type :</label>
                                    <select id="categoryType" name="dishCategory"> 
                                        <option value="Plat" <c:if test="${category.type == 'Plat'}"> selected </c:if>> Plat</option>
                                        <option value="Boisson"  <c:if test="${category.type == 'Boisson'}"> selected </c:if> > Boisson</option>
                                    </select>
                                </div>
                                <div class="tile-footer dvd dvd-top">
                                    <div class="row">
                                        <div class="col-xs-12 text-right">
                                            <a href="FrontController?option=category" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</a></button>
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
