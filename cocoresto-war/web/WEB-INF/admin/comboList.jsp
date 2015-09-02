<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Menus</strong></h1>
        <ul class="controls">
            <li>
                <a href="FrontController?option=combo&task=edit" role="button" tabindex="0" id="add-entry"><i class="fa fa-plus mr-5"></i> Ajouter</a>
            </li>
        </ul>
    </div>
    <div class="tile-body p-0">
        <table class="table table-striped">
            <thead>
                <tr class="bg-slategray">
                    <th hidden>Id</th>
                    <th>Nom</th>
                    <th>Catégorie</th>
                    <th>Prix HT</th>
                    <th>Prix TTC</th>
                    <th>Promotion</th>                    
                    <th>Prix Affiché</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="combo" items="${combos}" varStatus="loop">
                    <tr>
                        <td hidden>${combo.id}</td>
                        <td>${combo.name}</td>
                        <td>${combo.category.name}</td>
                        <td>${combo.price.price}</td>
                        <td>${combo.priceWithTax}</td>
                        <td><c:if test="${not empty combo.discount}">${combo.discount.rate} (<fmt:formatDate value="${combo.discount.beginDate}" pattern="dd-MM-yyyy"/> - <fmt:formatDate value="${combo.discount.endDate}" pattern="dd-MM-yyyy"/>)</c:if></td>
                        <td>${combo.totalPrice}</td>
                        <td>
                            <a href="FrontController?option=combo&task=edit&id=${combo.id}"  class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-edit"></i> <span>Modifier</span></a>
                            <a href="FrontController?option=combo&task=delete&id=${combo.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-trash"></i> <span>Supprimer</span></a>
                        </td>                                           
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    ${pagination}   
</section>

