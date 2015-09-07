<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Commandes</strong></h1>
        <!--
        <ul class="controls">
            <li>
                <a href="FrontController?option=customerOrder&task=add" role="button" tabindex="0" id="add-entry"><i class="fa fa-plus mr-5"></i> Ajouter</a>
            </li>
        </ul>
        -->
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
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>                    
                <c:forEach var="customerOrder" items="${customerOrders}" varStatus="loop">
                    <tr>
                        <td>Commande n°${customerOrder.number}</td>
                        <td><fmt:formatDate value="${customerOrder.orderDate}" pattern="dd MMM yyyy à hh:MM" /></td>
                        <td>
                            <div class="label label-${customerOrder.status}">${customerOrder.status.name}</div>

                        </td>
                        <td>${customerOrder.people} personnes</td>
                        <td>Table n°${customerOrder.customerTable.number}</td>
                        <td>${customerOrder.nbTablet} tablettes</td>
                        <td>
                            <a href="FrontController?option=customerOrder&task=edit&id=${customerOrder.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-edit"></i> <span>Consulter</span></a>
                            <!--<a href="FrontController?option=customerOrder&task=delete&id=${customerOrder.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-trash"></i> <span>Supprimer</span></a>-->
                        </td>
                    </tr>                                    
                </c:forEach>
            </tbody>
        </table>
    </div>

    ${pagination}

</section>
