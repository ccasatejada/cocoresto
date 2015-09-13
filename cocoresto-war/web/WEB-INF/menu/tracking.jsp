<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="tile">
    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Suivi de votre commande</strong></h1>
    </div>
    <div class="tile-body p-0">
        <table class="table table-striped">
            <tbody>     
                <c:if test="${not empty dishOrderLines}">
                    <tr class="bg-slategray">
                        <th colspan="3">Plats</th>
                    </tr>
                    <c:forEach var="orderline" items="${dishOrderLines}" varStatus="loop">
                        <tr data-id="${orderline.id}">
                            <td>${orderline.dish.name}</td>
                            <td>${orderline.dish.category}</td>
                            <td>${orderline.status}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${not empty drinkOrderLines}">
                    <tr class="bg-slategray">
                        <th colspan="3">Boissons</th>
                    </tr>
                    <c:forEach var="orderline" items="${drinkOrderLines}" varStatus="loop">
                        <tr data-id="${orderline.id}">
                            <td>${orderline.drink.name}</td>
                            <td>${orderline.drink.category}</td>
                            <td>${orderline.status}</td>
                        </tr>
                    </c:forEach>drink
                </c:if>
                <c:if test="${not empty orderCombos}">
                    <tr class="bg-slategray">
                        <th colspan="3">Menus</th>
                    </tr>
                    <c:forEach var="orderline" items="${comboOrderLines}" varStatus="loop">
                        <tr data-id="${orderline.id}">
                            <td colspan="3">Plats du menu : ${orderline.combo.name}</td>
                        </tr>
                            <c:forEach var="orderline" items="${orderline.dishes}" varStatus="loop">
                                <tr data-id="${orderline.id}">
                                    <td>${orderline.dish.name}</td>
                                    <td>${orderline.dish.category}</td>
                                    <td>${orderline.status}</td>
                                </tr>
                            </c:forEach>                                
                    </c:forEach>
                </c:if>
            </tbody>
        </table>
    </div>
</section>
