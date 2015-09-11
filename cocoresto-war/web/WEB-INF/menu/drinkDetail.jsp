<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container-fluid">
    ${alert}
    <div class="row">
        <div class="hidden-xs col-sm-4">
            <img src="images/products/${drink.image}" alt="${drink.name}" class="img-thumbnail w100" />
        </div>
        <div class="col-xs-12 col-sm-8">
            <h1 class="text-greensea">${drink.name}<small>&nbsp;(${drink.category})</small></h1>
            <c:if test="${not empty drink.description}">
                <p class="lead">${drink.description}</p>
            </c:if>
                
            <c:if test="${not empty drink.format}">
                <hr />
                <p>${format}</p>
            </c:if>

            <hr />
            <h2 class="text-info">${drink.totalPrice} € <c:if test="${not empty drink.discount}"><span class="label label-success">Promo : ${drink.discount}</span></c:if></h2>
        </div>

    </div>
</div>