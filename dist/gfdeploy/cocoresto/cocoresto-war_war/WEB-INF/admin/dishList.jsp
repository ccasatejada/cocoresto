<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Plats</strong></h1>
        <ul class="controls">
            <li>
                <a href="FrontController?option=dish&task=edit" role="button" tabindex="0" id="add-entry"><i class="fa fa-plus mr-5"></i> Ajouter</a>
            </li>
        </ul>
    </div>
    <div class="tile-body p-0">
        <table class="table table-striped">
            <thead>
                <tr class="bg-slategray">
                    <th hidden>Id</th>
                    <th>Nom</th>
                    <th>Stock</th>
                    <th>Région</th>
                    <th>Poids</th>
                    <th>Catégorie</th>
                    <th>Prix HT</th>
                    <th>Prix TTC</th>
                    <th>Promotion</th>                    
                    <th>Prix Affiché</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="dish" items="${dishes}" varStatus="loop">
                    <tr>
                        <td hidden>${dish.id}</td>
                        <td>${dish.name}</td>
                        <td>${dish.inventory}</td>
                        <td>${dish.country}</td>
                        <td>${dish.weight}</td>
                        <td>${dish.category.name}</td>
                        <td>${dish.price.price}</td>
                        <td>${dish.priceWithTax}</td>
                        <td><c:if test="${not empty dish.discount}">${dish.discount.rate} (<fmt:formatDate value="${dish.discount.beginDate}" pattern="dd-MM-yyyy"/> - <fmt:formatDate value="${dish.discount.endDate}" pattern="dd-MM-yyyy"/>)</c:if></td>
                        <td>${dish.totalPrice}</td>
                        <td>
                            <a href="FrontController?option=dish&task=edit&id=${dish.id}"  class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-edit"></i> <span>Modifier</span></a>
                            <a href="FrontController?option=dish&task=delete&id=${dish.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-trash"></i> <span>Supprimer</span></a>
                        </td>                                           
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    ${pagination}   
</section>

