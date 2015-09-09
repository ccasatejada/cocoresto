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
                    <c:forEach var="co" items="${coToDo}" varStatus="loop">
                        <c:forEach var="dish" items="${co.dishes}" varStatus="loop">
                            <tr class="text-center">
                                <td><div class="bg-primary text-center mt-5">${co.number}</div></td>
                                <td><div class="mt-5"><fmt:formatDate value="${co.orderDate}" pattern="dd/MM à hh:MM" /></div></td>
                                <td>
                                    <div class="label label-${co.status} mt-5">${co.status.name}</div>
                                </td>
                                <td><p class="text-lg text-strong mt-5">${dish.name}</p></td>
                                <td><a class="btn btn-primary center-block" href="FrontController?option=customerOrder&task=swap&id=${co.id}">Préparer</a></td>
                            </tr>
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
                    <c:forEach var="co" items="${coToDo}" varStatus="loop">
                        <c:forEach var="drink" items="${co.drinks}" varStatus="loop">
                            <tr class="text-center">
                                <td><div class="bg-primary text-center mt-5">${co.number}</div></td>
                                <td><div class="mt-5"><fmt:formatDate value="${co.orderDate}" pattern="dd/MM à hh:MM" /></div></td>
                                <td>
                                    <div class="label label-${co.status} mt-5">${co.status.name}</div>
                                </td>
                                <td><p class="text-lg text-strong mt-5">${drink.name}</p></td>
                                <td><a class="btn btn-primary center-block" href="FrontController?option=customerOrder&task=swap&id=${co.id}">Préparer</a></td>
                            </tr>
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
                    <c:forEach var="cOrder" items="${coOnPrep}" varStatus="loop">
                        <c:forEach var="dishOnPrep" items="${cOrder.dishes}" varStatus="loop">
                            <tr class="text-center">
                                <td><div class="bg-primary text-center mt-5">${cOrder.number}</div></td>
                                <td><div class="mt-5"><fmt:formatDate value="${cOrder.orderDate}" pattern="dd/MM à hh:MM" /></div></td>
                                <td>
                                    <div class="label label-${cOrder.status} mt-5">${cOrder.status.name}</div>
                                </td>
                                <td><p class="text-lg text-strong mt-5">${dishOnPrep.name}</p></td>
                                <td><a class="btn btn-primary center-block" href="FrontController?option=customerOrder&task=ready&id=${cOrder.id}">Prêt!</a></td>
                            </tr>
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
                    <c:forEach var="cOrder" items="${coOnPrep}" varStatus="loop">
                        <c:forEach var="drinkOnPrep" items="${coOnPrep.drinks}" varStatus="loop">
                            <tr class="text-center">
                                <td><div class="bg-primary text-center mt-5">${cOrder.number}</div></td>
                                <td><div class="mt-5"><fmt:formatDate value="${cOrder.orderDate}" pattern="dd/MM à hh:MM" /></div></td>
                                <td>
                                    <div class="label label-${cOrder.status} mt-5">${cOrder.status.name}</div>
                                </td>
                                <td><p class="text-lg text-strong mt-5">${drinkOnPrep.name}</p></td>
                                <td><a class="btn btn-primary center-block" href="FrontController?option=customerOrder&task=ready&id=${cOrder.id}">Prêt!</a></td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</section>
