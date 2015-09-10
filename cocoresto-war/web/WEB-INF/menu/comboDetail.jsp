<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container-fluid">
    ${alert}
    <div class="row">
        <div class="col-xs-12 col-sm-12">
            <h1 class="text-greensea">${combo.name}<small>&nbsp;(${combo.category})</small></h1>
            <c:forEach var="dish" items="${combo.dishes}">
                <hr />
                <div class="row">
                    <div class="hidden-xs col-sm-4">
                        <img src="images/products/${dish.image}" alt="${dish.name}" class="img-thumbnail w100" />
                    </div>
                    <div class="col-xs-12 col-sm-8">
                        <h2 class="text-darkgray">${dish.name}<small>&nbsp;(${dish.category})</small></h2>
                        <c:if test="${not empty dish.country}">
                            <p class="h5 darkgray">Origine : ${dish.country}</p>
                        </c:if>
                        <c:if test="${not empty dish.description}">
                            <p class="lead">${dish.description}</p>
                        </c:if>
                        <c:if test="${dish.weight > 0}">
                            <hr />
                            <ul>
                                <li>Poids : ${dish.weight} g</li>
                            </ul>
                        </c:if>

                            <hr />
                            <ol>
                                <c:forEach var="nutritiveValue" items="${dish.nutritiveValues}" varStatus="loop">
                                    <c:if test="${nutritiveValue.quantity > 0}">
                                        <li>${nutritiveValue.name} : ${nutritiveValue.quantity} ${nutritiveValue.unit.name}</li>
                                        </c:if>
                                    </c:forEach>
                            </ol>

                    </div>
                </div>
            </c:forEach>
            <hr />
            <h2 class="text-info">${combo.totalPrice} € <c:if test="${not empty combo.discount}"><span class="label label-success">Promo : ${combo.discount}</span></c:if></h2>
        </div>
    </div>
</div>