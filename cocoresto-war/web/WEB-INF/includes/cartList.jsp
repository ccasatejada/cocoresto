<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="tile">
    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Votre panier</strong> <small class="text-white text-right">Commande n°${order.number}</small></h1>
    </div>

    <div class="tile-body p-0">
        <form action="FrontController?option=menu&task=recap" method="POST">
            <table id="cart" class="table table-striped scroll">
                <thead>
                    <tr class="bg-slategray">
                        <th>Nom</th>
                        <th>Prix</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th class="bg-default">Total</th>
                        <td colspan="2"><span id="cartTotal">${cartTotal}</span> &euro;</td>
                    </tr>
                </tfoot>
                <tbody>
                    <c:if test="${not empty cartDishes}">
                        <tr><th class="bg-info" colspan="3">Plats</th></tr>
                        <c:forEach var="dish" items="${cartDishes}" varStatus="loop">
                            <tr>
                                <td>${dish.name}</td>
                                <td>${dish.totalPrice}</td>
                                <td>
                                    <a data-task="remove" data-id="${dish.id}" data-price="${dish.totalPrice}" data-type="Plat" href="#" class="btn btn-lightred btn-rounded btn-ef" name="deleteIt"><i class="fa fa-minus-circle"></i></a>
                                </td>
                            </tr>                      
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty cartDrinks}">
                        <tr><th class="bg-info" colspan="3">Boissons</th></tr>
                        <c:forEach var="drink" items="${cartDrinks}" varStatus="loop">
                            <tr>
                                <td>${drink.name}</td>
                                <td>${drink.totalPrice}</td>
                                <td>
                                    <a data-task="remove" data-id="${drink.id}" data-price="${drink.totalPrice}" data-type="Boisson" href="#" class="btn btn-lightred btn-rounded btn-ef" name="deleteIt"><i class="fa fa-minus-circle"></i></a>
                                </td>
                            </tr>                      
                        </c:forEach>
                    </c:if>
                    <c:if test="${not empty cartCombos}">
                        <tr><th class="bg-info" colspan="3">Menus</th></tr>
                        <c:forEach var="combo" items="${cartCombos}" varStatus="loop">
                            <tr>
                                <td>${combo.name}</td>
                                <td>${combo.totalPrice}</td>
                                <td>
                                    <a data-task="remove" data-id="${combo.id}" data-price="${combo.totalPrice}" data-type="Menu" href="#" class="btn btn-lightred btn-rounded btn-ef" name="deleteIt"><i class="fa fa-minus-circle"></i></a>
                                </td>
                            </tr>                      
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
            <button type="submit" class="btn btn-greensea pull-right mt-10 mb-10" name="confirm">Valider</button>
        </form>
    </div>
</div>