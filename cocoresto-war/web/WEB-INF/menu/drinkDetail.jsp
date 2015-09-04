<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="drinkDetails" class="container-fluid">
    <div class="row">
        <div class="col-xs-6">
            <img src="images/drinks/${drink.image}" alt="image:${drink.name}" height="220" width="220" class="img-rounded">
        </div>

        <div class="col-xs-6">
            <form>
                <div class="group-format">
                    <h1>${drink.name}</h1>
                </div>
                <div class="group-format">
                    <p>${drink.description}</p>
                </div>
                <div class="group-format">
                    <c:forEach var="format" items="${drink.formats}" varStatus="loop">
                        <input type="radio" name="format" value="${format}">&nbsp;${format}
                    </c:forEach>
                </div>
                <div class="group-format">
                    <p>${drink.totalPrice} €</p>
                </div>
                <div class="group-format">
                    <p>${drink.discount}</p>
                </div>
                <div class="group-format">
                    <input type="submit" id="add-entry" name="addDrink" class="btn btn-cyan btn-rounded btn-ef btn-ef-5 btn-ef-5a btn-lg" value="Ajouter">
                </div>
            </form>
        </div>

    </div>
</div>

