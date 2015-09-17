<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Taxes</strong></h1>
        <ul class="controls">
            <li>
                <a href="FrontController?option=rate&task=editTax" role="button" tabindex="0" id="add-entry" name="addTax"><i class="fa fa-plus mr-5"></i> Ajouter</a>
            </li>
        </ul>
    </div>

    <div class="tile-body p-0">
        <table class="table table-striped">
            <thead>
                <tr class="bg-slategray">
                    <th>Identifiant</th>
                    <th>Montant</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="tax" items="${taxes}" varStatus="loop">
                    <tr>
                        <td>${tax.id}</td>
                        <td>${tax.rate}</td>
                        <td>
                            <a href="FrontController?option=rate&task=modifyTax&id=${tax.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyTax">Modifier</a>
                            <a href="FrontController?option=rate&task=deleteTax&id=${tax.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="deleteTax">Supprimer</a>
                        </td>
                    </tr>                   
                </c:forEach>
            </tbody>
        </table>
    </div>

    ${taxPagination}

</section>



<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Discounts</strong></h1>
        <ul class="controls">
            <li>
                <a href="FrontController?option=rate&task=editDiscount" role="button" tabindex="0" id="add-entry" name="addDiscount"><i class="fa fa-plus mr-5"></i> Ajouter</a>
            </li>
        </ul>
    </div>

    <div class="tile-body p-0">
        <table class="table table-striped">
            <thead>
                <tr class="bg-slategray">
                    <th>Identifiant</th>
                    <th>Montant</th>
                    <th>Date début</th>
                    <th>Date fin</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="discount" items="${discounts}" varStatus="loop">
                    <tr>
                        <td>${discount.id}</td>
                        <td>${discount.rate}</td>
                        <td><fmt:formatDate value='${discount.beginDate}' pattern='dd MMM yyyy'/></td>
                        <td><fmt:formatDate value='${discount.endDate}' pattern='dd MMM yyyy'/></td>
                        <td>
                            <a href="FrontController?option=rate&task=modifyDiscount&id=${discount.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyDiscount">Modifier</a>
                            <a href="FrontController?option=rate&task=deleteDiscount&id=${discount.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="deleteDiscount">Supprimer</a>
                        </td>
                    </tr>                   
                </c:forEach>
            </tbody>
        </table>
    </div>

    ${discountPagination}

</section>


