<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Boissons</strong></h1>
        <ul class="controls">
            <li>
                <a href="FrontController?option=drink&task=edit" role="button" tabindex="0" id="add-entry" name="addDrink"><i class="fa fa-plus mr-5"></i> Ajouter</a>
            </li>
        </ul>
    </div>

    <div class="tile-body p-0">
        <table class="table table-striped">
            <thead>
                <tr class="bg-slategray">
                    <th>Nom</th>
                    <th>Stock</th>
                    <th>Catégorie</th>
                    <th>Prix unitaire</th>
                    <th>Taxe</th>
                    <th>Discount</th>
                    <th>Prix total</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="drink" items="${drinks}" varStatus="loop">
                    <tr>
                        <td>${drink.name}</td>
                        <td>${drink.inventory}</td>
                        <td>${drink.category}</td>
                        <td>${drink.price}</td>
                        <td>${drink.tax}</td>
                        <td>${drink.discount}</td>
                        <td>${drink.totalPrice}</td>
                        <td>
                            <a href="FrontController?option=drink&task=modify&id=${drink.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyIt">Modifier</a>
                            <a href="FrontController?option=drink&task=delete&id=${drink.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="deleteIt">Supprimer</a>
                        </td>
                    </tr>                   
                </c:forEach>
            </tbody>
        </table>
    </div>

    ${pagination}

</section>

