<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Catégories</strong></h1>
    </div>
    <form action="FrontController?option=category" method="post">

        <div class="tile-body">

            <input type="hidden" value="${category.id}" name="id" /> 
            <div class="form-group">
                <label for="name">Nom de la catégorie :</label>
                <input type="text" class="form-control" id="name" name="nameCategory" value="${category.name}">
            </div>
            <div class="form-group">
                <label for="categoryType">Type :</label>
                <select id="categoryType" name="categoryType"> 
                    <option value="Plat" <c:if test="${category.type == 'Plat'}"> selected </c:if>> Plat</option>
                    <option value="Boisson"  <c:if test="${category.type == 'Boisson'}"> selected </c:if>> Boisson</option>
                    <option value="Dejeuner"  <c:if test="${category.type == 'Dejeuner'}"> selected </c:if>> Dejeuner</option>
                    <option value="Diner"  <c:if test="${category.type == 'Diner'}"> selected </c:if>> Diner</option>
                    <option value="Brunch"  <c:if test="${category.type == 'Brunch'}"> selected </c:if>> Brunch</option>
                </select>
            </div>

        </div>

        <div class="tile-footer dvd dvd-top">
            <div class="row">
                <div class="col-xs-12 text-right">
                    <a href="FrontController?option=category" class="btn btn-darkgray btn-rounded btn-ef"><i class="fa fa-remove"></i> <span>Annuler</a></button>
                    <button type="submit" class="btn btn-greensea btn-rounded btn-ef" name="confirm"><i class="fa fa-save"></i> <span>Valider</span></button>
                </div>
            </div>
        </div>
    </form>
</section>

