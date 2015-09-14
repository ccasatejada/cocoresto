<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="tile">

    <div class="tile-header dvd dvd-btm bg-lightred">
        <h1 class="custom-font"><strong>Demandes d'aide en attente</strong></h1>
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
                    <th>Supprimer</th>
                </tr>
            </thead>
            <tbody>                    
            <c:forEach var="customerOrder" items="${customerHelpOrders}" varStatus="loop">
                <tr>
                    <td><a class="btn btn-primary" href="FrontController?option=customerOrder&task=cart&table=${customerOrder.customerTable.number}">Commande n°${customerOrder.number}</a></td>
                    <td><fmt:formatDate value="${customerOrder.orderDate}" pattern="dd MMM yyyy à hh:MM" /></td>
                <td>
                    <div class="label label-${customerOrder.status}">${customerOrder.status.name}</div>
                </td>
                <td>${customerOrder.people} personnes</td>
                <td>Table n°${customerOrder.customerTable.number}</td>
                <td>${customerOrder.nbTablet} tablettes</td>
                <td><a class="btn btn-lightred" href="FrontController?option=customerOrder&task=help" onClick="removeHelp(${customerOrder.customerTable.number})">Supprimer</a>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    ${pagination}

</section>

