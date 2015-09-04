<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${customerTableCapacityMax > 0}">
    <section class="tile">

        <div class="tile-header dvd dvd-btm bg-greensea">
            <h1 class="custom-font"><strong>Nouvelle Commande</strong></h1>
        </div>
        <form id="newOrder" action="FrontController?option=customerOrder&task=new" method="post">

            <div class="tile-body">

                <fieldset>
                    <legend>Détails</legend>

                    <div class="form-group">
                        <label for="people">Nombre de couverts : <span>*</span></label>
                        <input type="number" min="1" max="${customerTableCapacityMax}" class="form-control input-lg" id="people" name="people" value="0" required />
                    </div>

                    <div id="customerTableGroup" class="form-group">
                        <label for="customerTable">Table disponible : <span>*</span></label>
                        <select name="customerTable" id="customerTable"class="form-control input-lg" required>
                            <option value="0" disabled>Sélectionnez une table disponible</option>
                        </select>
                    </div>

                    <div id="nbTabletGroup" class="form-group">
                        <label for="nbTablet">Nombre de tablettes : <span>*</span></label>
                        <input type="number" min="1" class="form-control input-lg" id="nbTablet" name="nbTablet" value="1" required />
                    </div>

                </fieldset>

            </div>

            <div class="tile-footer dvd dvd-top">
                <div class="row">
                    <div class="col-xs-12 text-right">
                        <a href="FrontController?option=dashboard" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</a></button>
                        <button type="submit" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="confirm"><i class="fa fa-save"></i> <span>Valider</span></button>
                    </div>
                </div>
            </div>
        </form>
    </section>
</c:if>

<a href="FrontController?option=dashboard" class="btn btn-primary btn-lg"><i class="fa fa-chevron-circle-left"></i>&nbsp;Retour au panneau d'administration</a>
