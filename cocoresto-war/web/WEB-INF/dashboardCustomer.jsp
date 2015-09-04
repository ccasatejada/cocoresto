<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/includes/head.jsp" %>
        <title>JSP Page</title>
    </head>
    <body class="bg-white">
        <%@include file="/WEB-INF/includes/customerMenu.jsp" %>
        <section class="tile bg-white">
            <section id="menuContent" class="tile col-xs-12 col-sm-6 col-md-8">

            </section>

            <section id="cartContent" class="tile col-xs-6 col-md-4 mb-40">
                <h1>Votre panier</h1>
                <table class="table table-striped mb-40">
                    <thead>
                        <tr class="bg-slategray">
                            <th>Nom</th>
                            <th>Quantité</th>
                            <th>Prix</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="combo" items="${cartCombos}" varStatus="loop">
                            <tr>
                                <td>${combo.name}</td>
                                <td>0</td>
                                <td>0</td>
                                <td>
                                    <a href="FrontController?option=drink&task=modify&id=${combo.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyIt"><i class="fa fa-plus-circle"></i></a>
                                    <a href="FrontController?option=drink&task=delete&id=${combo.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="deleteIt"><i class="fa fa-minus-circle"></i></a>
                                </td>
                            </tr>                   
                        </c:forEach>
                        <c:forEach var="dish" items="${cartDishes}" varStatus="loop">
                            <tr>
                                <td>${dish.name}</td>
                                <td>0</td>
                                <td>0</td>
                                <td>
                                    <a href="FrontController?option=drink&task=modify&id=${dish.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyIt"><i class="fa fa-plus-circle"></i></a>
                                    <a href="FrontController?option=drink&task=delete&id=${dish.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="deleteIt"><i class="fa fa-minus-circle"></i></a>
                                </td>
                            </tr>                   
                        </c:forEach>
                        <c:forEach var="drink" items="${cartDrinks}" varStatus="loop">
                            <tr>
                                <td>${drink.name}</td>
                                <td>0</td>
                                <td>0</td>
                                <td>
                                    <a href="FrontController?option=drink&task=modify&id=${drink.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyIt"><i class="fa fa-plus-circle"></i></a>
                                    <a href="FrontController?option=drink&task=delete&id=${drink.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="deleteIt"><i class="fa fa-minus-circle"></i></a>
                                </td>
                            </tr>                   
                        </c:forEach>
                    </tbody>
                </table>

            </section>
        </section>

        <%@include file="/WEB-INF/includes/scripts.jsp" %>
    </body>
</html>
