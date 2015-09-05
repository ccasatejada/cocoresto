<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="dishMenu" class="container-fluid">
    <div class="row">
        <h1>Ca marche!</h1>
        <c:forEach var="dish" items="${dishes}" varStatus="loop">
            <div class="col-sm-4 mt-20">
                <a href="FrontController?option=menu&task=getDishDetail&id=${dish.id}" class="drinkDetail btn btn-cyan btn-rounded btn-ef btn-ef-5 btn-ef-5a btn-lg" name="getDetail">
                    <figure>
                        <img src="../images/dishes/${dish.image}" alt="image:${dish.name}" height="220" width="220" class="img-rounded">
                        <figcaption>${dish.name}</figcaption>
                    </figure>
                </a>
            </div>
        </c:forEach>
    </div>
    ${pagination}
</div>
