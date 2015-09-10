<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="dishDetails" class="container-fluid">
    ${alert}
    <div class="row">
        <div class="col-xs-6">
            <img src="images/dishes/${dish.image}" alt="image:${dish.name}" height="220" width="220" class="img-rounded">
        </div>

        <div class="col-xs-6">
            <form>
                <div class="group-format">
                    <h1>${dish.category}</h1>
                </div>
                <div class="group-format">
                    <h1>${dish.name}</h1>
                </div>
                <div class="group-format">
                    <p>${dish.description}</p>
                </div>
                <div class="group-format">
                    <p>${dish.country}</p>
                </div>
                <div class="group-format">
                    <p>${dish.weight} g</p>
                </div>
                <div class="group-format">
                    <c:forEach var="nutritiveValue" items="${dish.nutritiveValues}" varStatus="loop">
                        <c:if test="${nutritiveValue.quantity > 0}"><p>${nutritiveValue.name} : ${nutritiveValue.quantity} ${nutritiveValue.unit.name}</p></c:if>
                    </c:forEach>
                </div>
                <div class="group-format">
                    <p>${dish.totalPrice} €</p>
                </div>
                <div class="group-format">
                    <p>${dish.discount}</p>
                </div>
                <div class="group-format">
                    <input type="submit" id="add-entry" name="addDish" class="btn btn-cyan btn-rounded btn-ef btn-ef-5 btn-ef-5a btn-lg" value="Ajouter">
                </div>
            </form>
        </div>

    </div>
</div>


