<section id="cartContent" class="tile col-lg-4 col-sm-6 col-sm-12 p-0">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Votre panier</strong></h1>
    </div>

    <div class="tile-body p-0">
        <table class="table table-striped">
            <thead>
                <tr class="bg-slategray">
                    <th>Nom</th>
                    <th>Quantité</th>
                    <th>Prix</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="combo" items="${cartCombos}" varStatus="loop">
                <tr>
                    <td>${combo.name}</td>
                    <td>0</td>
                    <td>0</td>
                    <td>
                        <a href="FrontController?option=drink&task=modify&id=${combo.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyIt"><i class="fa fa-plus-circle"></i></a>
                        <a href="FrontController?option=drink&task=delete&id=${combo.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="deleteIt"><i class="fa fa-minus-circle"></i></a>
                    </td>
                </tr>                   
            </c:forEach>
            <c:forEach var="dish" items="${cartDishes}" varStatus="loop">
                <tr>
                    <td>${dish.name}</td>
                    <td>0</td>
                    <td>0</td>
                    <td>
                        <a href="FrontController?option=drink&task=modify&id=${dish.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyIt"><i class="fa fa-plus-circle"></i></a>
                        <a href="FrontController?option=drink&task=delete&id=${dish.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="deleteIt"><i class="fa fa-minus-circle"></i></a>
                    </td>
                </tr>                   
            </c:forEach>
            <c:forEach var="drink" items="${cartDrinks}" varStatus="loop">
                <tr>
                    <td>${drink.name}</td>
                    <td>0</td>
                    <td>0</td>
                    <td>
                        <a href="FrontController?option=drink&task=modify&id=${drink.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyIt"><i class="fa fa-plus-circle"></i></a>
                        <a href="FrontController?option=drink&task=delete&id=${drink.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="deleteIt"><i class="fa fa-minus-circle"></i></a>
                    </td>
                </tr>                   
            </c:forEach>
            </tbody>
        </table>
    </div>

    ${pagination}

</section>