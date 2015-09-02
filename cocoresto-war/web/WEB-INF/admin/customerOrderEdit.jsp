<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Commande</strong></h1>
    </div>
    <form action="FrontController?option=customerOrder" method="post">

        <div class="tile-body">

            <fieldset>
                <legend>Informations</legend>

                <input type="hidden" name="id" value="${customerOrder.id}">
                <c:if test="${not empty customerOrder.number}">
                    <div class="form-group">
                        <label for="number">Numéro de commande : <span>*</span></label>
                        <input type="text" class="form-control" id="number" name="number" value="${customerOrder.number}" readonly required />
                    </div>
                </c:if>

                <c:if test="${not empty customerOrder.orderDate}">
                    <div class="form-group">
                        <label for="orderDate">Date de la commande : <span>*</span></label>
                        <input type="text" class="form-control" id="orderDate" name="orderDate" value="<fmt:formatDate value="${customerOrder.orderDate}" pattern="dd MMM yyyy à hh:MM" />" readonly required />
                    </div>
                </c:if>

                <div class="form-group">
                    <label for="status">Statut de la commande : <span>*</span></label>
                    <select class="form-control" id="status" name="status" required>
                        <c:forEach items="${statusList}" var="status" varStatus="loop">
                            <option value="${status}"<c:if test="${status eq customerOrder.status}"> selected</c:if>>${status.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="employee">Créateur de la commande : <span>*</span></label>
                    <input class="form-control" id="employee" name="employee" required value="${customerOrder.employee.firstName} ${customerOrder.employee.lastName}" readonly />
                </div>

            </fieldset>
            <fieldset>
                <legend>Détails</legend>

                <div class="form-group">
                    <label for="customerTable">Table de la commande : <span>*</span></label>
                    <div class="input-group">
                        <input class="form-control" id="customerTable" name="customerTable" required value="Table n°${customerOrder.customerTable.number}" readonly />
                        <span class="input-group-btn">
                            <button id="modifyCustomerTable" class="btn btn-info" type="button" data-toggle="modal" data-target="#listModal">Modifier</button>
                        </span>
                    </div>                                    
                </div>

                <div class="form-group">
                    <label for="people">Nombre de couverts : <span>*</span></label>
                    <input type="number" min="1" max="${customerOrder.customerTable.capacity}" class="form-control" id="people" name="people" value="${customerOrder.people}" required />
                </div>

                <div class="form-group">
                    <label for="nbTablet">Nombre de tablettes : <span>*</span></label>
                    <input type="number" min="1" max="${customerOrder.customerTable.nbTablet}" class="form-control" id="nbTablet" name="nbTablet" value="${customerOrder.nbTablet}" required />
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
        <form action="FrontController?option=customerOrder&task=edit&id=${customerOrder.id}" method="post" class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="listModalLabel">Liste</h4>
            </div>
            <div class="modal-body">
                <iframe style="background: #fff; border: none;" src="FrontController?option=customerTable&task=simpleList&layout=component" width="100%" height="300px" scrolling="auto"></iframe>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
                <button type="button" class="btn btn-primary" name="save">Sauver</button>
            </div>
        </form>
    </div>
</div>                                    


