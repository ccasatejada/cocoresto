<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="comboDetails" class="container-fluid">
    <div class="row">
        <h1>${combo.name}</h1>
        <form>
            <c:forEach var="dish" items="${combo.dishes}">
                <div class="col-xs-6">
                    <img src="images/dishes/${dish.image}" alt="image:${dish.name}" height="220" width="220" class="img-rounded">
                </div>
                <div class="col-xs-6">
                    <div class="group-format">
                        <h2>${dish.name}</h2>
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
                            <c:if test="${nutritiveValue.quantity > 0}"><p>"${nutritiveValue.name} : ${nutritiveValue.quantity} ${nutritiveValue.unit.name}"</p></c:if>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
            <div class="group-format">
                <input type="submit" id="add-entry" name="addCombo" class="btn btn-cyan btn-rounded btn-ef btn-ef-5 btn-ef-5a btn-lg" value="Ajouter">
            </div>
        </form>
    </div>

</div>
</div>


