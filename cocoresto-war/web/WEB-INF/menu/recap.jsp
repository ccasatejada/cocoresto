<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Contenu de votre panier</strong></h1>
    </div>

    <div class="tile-footer dvd dvd-top">
        <div class="row">
            <form method="POST">
                <div class="col-xs-12 text-right">
                    <a href="FrontController?option=dashboard" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</span></a>
                    <button type="submit" name="confirm" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-save"></i> <span>Valider</span></button>
                </div>
            </form>
        </div>
    </div>

    <div class="tile-body p-0">

        <table class="table table-striped">
            <tbody>     
                <c:if test="${not empty cartDishes}">
                    <tr class="bg-slategray">
                        <th colspan="3">Plats</th>
                    </tr>
                    <c:forEach var="dish" items="${cartDishes}" varStatus="loop">
                        <tr>
                            <td colspan="2">${dish.name}</td>
                            <td>${dish.totalPrice}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${not empty cartDrinks}">
                    <tr class="bg-slategray">
                        <th colspan="3">Boissons</th>
                    </tr>
                    <c:forEach var="drink" items="${cartDrinks}" varStatus="loop">
                        <tr>
                            <td colspan="2">${drink.name}</td>
                            <td>${drink.totalPrice}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${not empty cartCombos}">
                    <tr class="bg-slategray">
                        <th colspan="3">Menus</th>
                    </tr>
                    <c:forEach var="combo" items="${cartCombos}" varStatus="loop">
                        <tr>
                            <td>${combo.name}</td>
                            <td>
                                <c:forEach var="dish" items="${combo.dishes}" varStatus="loop">
                                    <span>${dish.name}<span>&nbsp;/&nbsp;
                                        </c:forEach>                                
                                        </td>
                                        <td>${combo.totalPrice}</td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                                </table>
                                </div>
                                </section>
