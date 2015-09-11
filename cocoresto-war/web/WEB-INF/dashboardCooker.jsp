<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="tile">
    <div id="ordersToDo" class="col-lg-6 col-md-6 col-sm-6">
        <div class="tile-header dvd dvd-btm bg-greensea">
            <h1 class="custom-font"><strong>Plats à faire pour ${userName}</strong></h1>
        </div>

        <div class="tile-body p-0">
            <table class="table table-striped">
                <thead>
                    <tr class="bg-slategray">
                        <th>Commande</th>
                        <th>Date</th>
                        <th>Statut</th>
                        <th>Plats</th>
                        <th>Choisir</th>
                    </tr>
                </thead>
                <tbody>                    
                    <c:forEach var="co" items="${cos}" varStatus="loop">
                        <c:forEach var="dish" items="${co.dishes}" varStatus="loop">
                            <c:if test="${dish.status == 1}">
                                <tr class="text-center">
                                    <td><span class="bg-primary text-center mt-5">${co.number}</span></td>
                                    <td><span class="mt-5"><fmt:formatDate value="${co.orderDate}" pattern="dd/MM à hh:MM" /></span></td>
                                    <td>
                                        <span class="label label-${co.status} mt-5">${co.status.name}</span>
                                    </td>
                                    <td><span class="text-lg text-strong mt-5">${dish.name}</span></td>
                                    <td><a class="btn btn-primary center-block" href="FrontController?option=customerOrder&task=swap&id=${co.id}&dNb=${dish.id}">Préparer</a></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="combo" items="${co.combos}" varStatus="loop">
                            <c:forEach var="dishCombo" items="${combo.dishes}" varStatus="loop">
                                <c:if test="${dishCombo.status == 1}">
                                    <tr class="text-center">
                                        <td><span class="bg-primary text-center mt-5">${co.number}</span></td>
                                        <td><span class="mt-5"><fmt:formatDate value="${co.orderDate}" pattern="dd/MM à hh:MM" /></span></td>
                                        <td>
                                            <span class="label label-${co.status} mt-5">${co.status.name}</span>
                                        </td>
                                        <td><span class="text-lg text-strong mt-5">${dishCombo.name}</span></td>
                                        <td><a class="btn btn-primary center-block" href="FrontController?option=customerOrder&task=swap&id=${co.id}&dcNb=${dishCombo.id}&cId=${combo.id}">Préparer</a></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tile-header dvd dvd-btm bg-greensea">
            <h1 class="custom-font"><strong>Boissons à faire pour ${userName}</strong></h1>
        </div>

        <div class="tile-body p-0">
            <table class="table table-striped">
                <thead>
                    <tr class="bg-slategray">
                        <th>Numéro de la commande</th>
                        <th>Date</th>
                        <th>Statut</th>
                        <th>Boissons</th>
                        <th>Choisir</th>
                    </tr>
                </thead>
                <tbody>                    
                    <c:forEach var="co" items="${cos}" varStatus="loop">
                        <c:forEach var="drink" items="${co.drinks}" varStatus="loop">
                            <c:if test="${drink.status == 1}">
                                <tr class="text-center">
                                    <td><span class="bg-primary text-center mt-5">${co.number}</span></td>
                                    <td><span class="mt-5"><fmt:formatDate value="${co.orderDate}" pattern="dd/MM à hh:MM" /></span></td>
                                    <td>
                                        <span class="label label-${co.status} mt-5">${co.status.name}</span>
                                    </td>
                                    <td><span class="text-lg text-strong mt-5">${drink.name}</span></td>
                                    <td><a class="btn btn-primary center-block" href="FrontController?option=customerOrder&task=swap&id=${co.id}&drNb=${drink.id}">Préparer</a></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </div>

    <div id="ordersInProcess" class="col-lg-6 col-md-6 col-sm-6">

        <div class="tile-header dvd dvd-btm bg-greensea">
            <h1 class="custom-font"><strong>Plats en cours pour ${userName}</strong></h1>
        </div>

        <div class="tile-body p-0">
            <table class="table table-striped">
                <thead>
                    <tr class="bg-slategray">
                        <th>Numéro de la commande</th>
                        <th>Date</th>
                        <th>Statut</th>
                        <th>Plats</th>
                        <th>Choisir</th>
                    </tr>
                </thead>
                <tbody>                    
                    <c:forEach var="cOrder" items="${cos}" varStatus="loop">
                        <c:forEach var="dishOnPrep" items="${cOrder.dishes}" varStatus="loop">
                            <c:if test="${dishOnPrep.status == 2}">
                                <tr class="text-center">
                                    <td><span class="bg-primary text-center mt-5">${cOrder.number}</span></td>
                                    <td><span class="mt-5"><fmt:formatDate value="${cOrder.orderDate}" pattern="dd/MM à hh:MM" /></span></td>
                                    <td>
                                        <span class="label label-${cOrder.status} mt-5">${cOrder.status.name}</span>
                                    </td>
                                    <td><span class="text-lg text-strong mt-5">${dishOnPrep.name}</span></td>
                                    <td><a class="btn btn-primary center-block" href="FrontController?option=customerOrder&task=ready&id=${cOrder.id}&dNb=${dishOnPrep.id}">Prêt!</a></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="comboOnPrep" items="${cOrder.combos}" varStatus="loop">
                            <c:forEach var="dishComboOnPrep" items="${comboOnPrep.dishes}" varStatus="loop">
                                <c:if test="${dishComboOnPrep.status == 2}">
                                    <tr class="text-center">
                                        <td><span class="bg-primary text-center mt-5">${cOrder.number}</span></td>
                                        <td><span class="mt-5"><fmt:formatDate value="${cOrder.orderDate}" pattern="dd/MM à hh:MM" /></span></td>
                                        <td>
                                            <span class="label label-${cOrder.status} mt-5">${cOrder.status.name}</span>
                                        </td>
                                        <td><span class="text-lg text-strong mt-5">${dishComboOnPrep.name}</span></td>
                                        <td><a class="btn btn-primary center-block" href="FrontController?option=customerOrder&task=ready&id=${cOrder.id}&dcNb=${dishComboOnPrep.id}&cId=${combo.id}">Prêt!</a></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="tile-header dvd dvd-btm bg-greensea">
            <h1 class="custom-font"><strong>Boissons en cours pour ${userName}</strong></h1>
        </div>

        <div class="tile-body p-0">
            <table class="table table-striped">
                <thead>
                    <tr class="bg-slategray">
                        <th>Numéro de la commande</th>
                        <th>Date</th>
                        <th>Statut</th>
                        <th>Boissons</th>
                        <th>Choisir</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cOrder" items="${cos}" varStatus="loop">
                        <c:forEach var="drinkOnPrep" items="${cOrder.drinks}" varStatus="loop">
                            <c:if test="${drinkOnPrep.status == 2}">
                                <tr class="text-center">
                                    <td><span class="bg-primary text-center mt-5">${cOrder.number}</span></td>
                                    <td><span class="mt-5"><fmt:formatDate value="${cOrder.orderDate}" pattern="dd/MM à hh:MM" /></span></td>
                                    <td>
                                        <span class="label label-${cOrder.status} mt-5">${cOrder.status.name}</span>
                                    </td>
                                    <td><span class="text-lg text-strong mt-5">${drinkOnPrep.name}</span></td>
                                    <td><a class="btn btn-primary center-block" href="FrontController?option=customerOrder&task=ready&id=${cOrder.id}&drNb=${drinkOnPrep.id}">Prêt!</a></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</section>
