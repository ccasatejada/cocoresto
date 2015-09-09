<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="dishMenu" class="panel-group" role="tablist" aria-multiselectable="true">

    <c:forEach var="category" items="${categories}" varStatus="loop">

        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="heading${loop.index}">
                <h4 class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#dishMenu" href="#collapse${loop.index}" aria-expanded="false" aria-controls="collapse${loop.index}">
                        ${category.name}
                    </a>
                </h4>
            </div>
            <div id="collapse${loop.index}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${loop.index}">
                <div class="panel-body">            

                    <c:forEach var="dish" items="${dishes}" varStatus="loop">
                        <c:if test="${category.name eq  dish.category.name}">
                            <div class="col-sm-4 mt-20">
                                <a href="FrontController?option=menu&task=getDishDetail&id=${dish.id}" class="drinkDetail btn btn-cyan btn-rounded btn-ef btn-ef-5 btn-ef-5a btn-lg" name="getDetail">
                                    <figure>
                                        <img src="images/dishes/${dish.image}" alt="image:${dish.name}" height="220" width="220" class="img-rounded">
                                        <figcaption>${dish.name}</figcaption>
                                    </figure>
                                </a>
                            </div>
                        </c:if>
                    </c:forEach>
                    <!--${pagination}-->

                </div>
            </div>
        </div>          
    </c:forEach>

</div>
