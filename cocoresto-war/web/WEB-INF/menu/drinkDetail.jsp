<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Détail de la boisson</title>
    </head>
    <body>

        <section class="tile">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xs-6 col-sm-4">
                        <img src="images/drinks/${drink.image}" alt="image:${drink.name}" height="220" width="220" class="img-rounded">
                    </div>

                    <div class="col-xs-6 col-sm-4">
                        <form>
                            <div class="group-format">
                                <h1>${drink.name}</h1>
                            </div>
                            <div class="group-format">
                                <p>${drink.description}</p>
                            </div>
                            <div class="group-format">
                                <c:forEach var="format" items="${drink.formats}" varStatus="loop">
                                    <input type="radio" name="format" value="${format}">&nbsp;${format}
                                </c:forEach>
                            </div>
                            <div class="group-format">
                                <p>${drink.totalPrice} €</p>
                            </div>
                            <div class="group-format">
                                <p>${drink.discount}</p>
                            </div>
                            <div class="group-format">
                                <input type="submit" id="add-entry" name="addDrink" class="btn btn-cyan btn-rounded btn-ef btn-ef-5 btn-ef-5a btn-lg" value="Ajouter">
                            </div>
                        </form>
                    </div>
                    <div class="col-xs-6 col-sm-4">
                        <h1>Votre panier</h1>
                        <table class="table table-striped">
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
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
