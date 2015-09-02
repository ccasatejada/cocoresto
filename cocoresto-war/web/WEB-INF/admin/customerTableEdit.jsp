<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Tables</strong></h1>
    </div>
    <form action="FrontController?option=customerTable" method="post">

        <div class="tile-body">

            <input type="hidden" name="id" value="${customerTable.id}">
            <div class="form-group">
                <label for="number">Numéro de table : <span>*</span></label>
                <input type="number" placeholder="1" min="1" max="30" class="form-control" id="number" name="number" value="${customerTable.number}">
            </div>
            <div class="form-group">
                <label for="capacity">Capacité de la table : <span>*</span></label>
                <input type="number" placeholder="1" min="1" max="10" class="form-control" id="capacity" name="capacity" value="${customerTable.capacity}">
            </div>
            <div class="form-group">
                <label for="nbTablet">Nombre de tablettes : <span>*</span></label>
                <input type="number" placeholder="1" min="1" max="20" class="form-control" id="nbTablet" name="nbTablet" value="${customerTable.nbTablet}">
            </div>

        </div>

        <div class="tile-footer dvd dvd-top">
            <div class="row">
                <div class="col-xs-12 text-right">
                    <a href="FrontController?option=customerTable" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</a></button>
                    <button type="submit" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="confirm"><i class="fa fa-save"></i> <span>Valider</span></button>
                </div>
            </div>
        </div>
    </form>
</section>
