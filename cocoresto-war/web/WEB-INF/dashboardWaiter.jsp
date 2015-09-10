<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="btn-group btn-group-justified mb-20 mt-40" role="group">
    <div class="btn-group" role="group">
        <div id="helpDiv">
            <a href="FrontController?option=customerOrder&task=help" class="btn btn-lg btn-lightred"><i class="fa fa-bell"></i> <strong><span id="helpSpan">${nbHelp}</span></strong> demande(s) d'aide en attente</a>
        </div>
    </div>
</div>


<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Commandes en cours pour ${userName}</strong></h1>
        <ul class="controls">
            <li>
                <a href="FrontController?option=customerOrder&task=new" role="button" tabindex="0" id="add-entry"><i class="fa fa-plus mr-5"></i> Ajouter</a>
            </li>
        </ul>
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

</section>
