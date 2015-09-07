<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="drinkMenu">
    <c:forEach var="drink" items="${drinks}" varStatus="loop">
        <c:if test="${(((loop.index))%3) == 0 || loop.index == 0}">
            <div class="row">
            </c:if>
            <div class="card-container col-lg-4 col-sm-12 col-md-12">
                <div class="card">
                    <div class="front bg-blue">
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 text-center">
                                <figure>
                                    <img src="images/drinks/${drink.image}" alt="image:${drink.name}" class="drinkImages">
                                    <figcaption><p class="text-elg text-strong mb-0 mt-20">${drink.name}</p></figcaption>
                                </figure>
                            </div>
                        </div>
                    </div>
                    <div class="back bg-blue">
                        <div class="row">
                            <div class="col-xs-12">
                                <a href="FrontController?option=menu&task=getDrinkDetail&id=${drink.id}" class="drinkDetail" name="getDetail"><i class="fa fa-glass fa-4x mt-40 mb-40 p-10"></i><p class="text-elg text-strong mb-0 mt-40 p-10"> Voir le détail</p></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${((loop.index + 1)%3) == 0 && loop.index != 0}">
            </div>
        </c:if>
    </c:forEach>
</div>



