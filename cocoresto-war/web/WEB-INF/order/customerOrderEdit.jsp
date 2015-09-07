<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Commande n�${customerOrder.number}</strong></h1>
    </div>
    <form id="editOrder" action="FrontController?option=customerOrder" method="post">

        <div class="tile-body">

            <div class="text-right">
                <c:if test="${customerOrder.status != 'CANCELLED'}">
                    <button class="btn btn-danger" type="submit" name="cancel">Annuler la commande</button>
                </c:if>
            </div>

            <fieldset>
                <legend>Informations</legend>
                <input type="hidden" name="id" value="${customerOrder.id}">

                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="number">Num�ro de commande : <span>*</span></label>
                            <input type="text" class="form-control" id="number" name="number" value="${customerOrder.number}" readonly required />
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="orderDate">Date de la commande : <span>*</span></label>
                            <input type="text" class="form-control" id="orderDate" name="orderDate" value="<fmt:formatDate value="${customerOrder.orderDate}" pattern="dd MMM yyyy � hh:MM" />" readonly required />
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="status">Statut de la commande : <span>*</span></label>
                            <input type="text" class="form-control" id="status" name="status" value="${customerOrder.status.name}" readonly required />
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="employee">Cr�ateur de la commande : <span>*</span></label>
                            <input class="form-control" id="employee" name="employee" required value="${customerOrder.employee.firstName} ${customerOrder.employee.lastName}" readonly />
                        </div>
                    </div>
                </div>

            </fieldset>
            <fieldset>
                <legend>D�tails</legend>
                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label for="people">Nombre de couverts : <span>*</span></label>
                            <input type="number" min="1" max="${customerTableCapacityMax}" class="form-control input-lg" id="people" name="people" value="0" required />
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div id="customerTableGroup" class="form-group">
                            <label for="customerTable">Table disponible : <span>*</span></label>
                            <select name="customerTable" id="customerTable"class="form-control input-lg" required>
                                <option value="0" disabled>S�lectionnez une table disponible</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div id="nbTabletGroup" class="form-group">
                            <label for="nbTablet">Nombre de tablettes : <span>*</span></label>
                            <input type="number" min="1" class="form-control input-lg" id="nbTablet" name="nbTablet" value="1" required />
                        </div>
                    </div>
                </div>

            </fieldset>
            <fieldset>
                <legend>Panier</legend>

                <c:if test="${not empty customerOrder.drinks}">
                    <div class="panel panel-primary">
                        <div class="panel-heading">Boissons</div>
                        <div class="panel-body">    
                            <c:forEach items="${customerOrder.drinks}" var="drink" varStatus="loop">

                            </c:forEach>
                        </div>
                        <div class="panel-footer">
                            <a class="btn btn-primary" href="">Ajouter une boisson</a>
                        </div>
                    </div>
                </c:if>

                <c:if test="${not empty customerOrder.dishes}">
                    <div class="panel panel-primary">
                        <div class="panel-heading">Plats</div>
                        <div class="panel-body">    
                            <c:forEach items="${customerOrder.dishes}" var="drink" varStatus="loop">

                            </c:forEach>
                        </div>
                        <div class="panel-footer">
                            <a class="btn btn-primary" href="">Ajouter un plat</a>
                        </div>
                    </div>
                </c:if>

                <c:if test="${not empty customerOrder.combos}">
                    <div class="panel panel-primary">
                        <div class="panel-heading">Menus</div>
                        <div class="panel-body">    
                            <c:forEach items="${customerOrder.combos}" var="drink" varStatus="loop">

                            </c:forEach>
                        </div>
                        <div class="panel-footer">
                            <a class="btn btn-primary" href="">Ajouter une boisson</a>
                        </div>
                    </div>
                </c:if>

            </fieldset>
        </div>

        <div class="tile-footer dvd dvd-top">
            <div class="row">
                <div class="col-xs-12 text-right">
                    <a href="FrontController?option=customerOrder" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</a></button>
                    <button type="submit" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="confirm"><i class="fa fa-save"></i> <span>Valider</span></button>
                </div>
            </div>
        </div>
    </form>
</section>

<div class="modal fade" id="listModal" tabindex="-1" role="dialog" aria-labelledby="listModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="listModalLabel">Liste</h4>
            </div>
            <div class="modal-body">
                <iframe style="background: #fff; border: none;" src="FrontController?option=customerTable&task=simpleList&layout=component" width="100%" height="300px" scrolling="auto"></iframe>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
            </div>
        </div>
    </div>
</div>                                    

