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
                        <th>Numéro de la commande</th>
                        <th>Date</th>
                        <th>Statut</th>
                        <th>Couverts</th>
                        <th>Table</th>
                        <th>Tablettes</th>
                    </tr>
                </thead>
                <tbody>                    
                    <c:forEach var="customerOrder" items="${customerOrders}" varStatus="loop">
                        <tr>
                            <td><a class="btn btn-primary" href="FrontController?option=customerOrder&task=edit&id=${customerOrder.id}">Commande n°${customerOrder.number}</a></td>
                            <td><fmt:formatDate value="${customerOrder.orderDate}" pattern="dd MMM yyyy à hh:MM" /></td>
                            <td>
                                <div class="label label-${customerOrder.status}">${customerOrder.status.name}</div>
                            </td>
                            <td>${customerOrder.people} personnes</td>
                            <td>Table n°${customerOrder.customerTable.number}</td>
                            <td>${customerOrder.nbTablet} tablettes</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        ${pagination}

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
                        <th>Couverts</th>
                        <th>Table</th>
                        <th>Tablettes</th>
                    </tr>
                </thead>
                <tbody>                    
                    <c:forEach var="customerOrder" items="${customerOrders}" varStatus="loop">
                        <tr>
                            <td><a class="btn btn-primary" href="FrontController?option=customerOrder&task=edit&id=${customerOrder.id}">Commande n°${customerOrder.number}</a></td>
                            <td><fmt:formatDate value="${customerOrder.orderDate}" pattern="dd MMM yyyy à hh:MM" /></td>
                            <td>
                                <div class="label label-${customerOrder.status}">${customerOrder.status.name}</div>
                            </td>
                            <td>${customerOrder.people} personnes</td>
                            <td>Table n°${customerOrder.customerTable.number}</td>
                            <td>${customerOrder.nbTablet} tablettes</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        ${pagination}
    </div>
</section>
