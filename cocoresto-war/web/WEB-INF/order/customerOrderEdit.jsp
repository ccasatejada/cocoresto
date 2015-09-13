<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Commande n°${customerOrder.number}</strong></h1>
    </div>
    <!-- <form id="editOrder" class="edit" action="FrontController?option=customerOrder&task=edit&id=${customerOrder.id}" method="post"> -->
    <form action="FrontController?option=customerOrder&task=edit&id=${customerOrder.id}" method="post">

        <div class="tile-body">

            <div class="text-right">
                <c:if test="${customerOrder.status != 'CANCELLED'}">
                    <button class="btn btn-danger" type="submit" name="cancel" onclick="if(!window.confirm('Voulez-vous vraiment annuler cette commande ?')){return false;}">Annuler la commande</button>
                </c:if>
            </div>

            <fieldset>
                <legend>Informations</legend>
                <input type="hidden" name="id" value="${customerOrder.id}">

                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="number">Numéro de commande : <span>*</span></label>
                            <input type="text" class="form-control" id="number" name="number" value="${customerOrder.number}" readonly required />
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="orderDate">Date de la commande : <span>*</span></label>
                            <input type="text" class="form-control" id="orderDate" name="orderDate" value="<fmt:formatDate value="${customerOrder.orderDate}" pattern="dd MMM yyyy à hh:MM" />" readonly required />
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
                            <label for="employee">Créateur de la commande : <span>*</span></label>
                            <input class="form-control" id="employee" name="employee" required value="${customerOrder.employee.firstName} ${customerOrder.employee.lastName}" readonly />
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <legend>Détails</legend>
                <div class="row">
                    <div class="col-sm-4">
                        <div class="form-group">
                            <label for="people">Nombre de couverts : <span>*</span></label>
                            <input type="number" min="1" max="${customerOrder.customerTable.capacity}" class="form-control input-lg" id="people" name="people" value="${customerOrder.people}" <c:if test="${customerOrder.status != 'OPENED'}">readonly </c:if>required />
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div id="customerTableGroup" class="form-group">
                            <label for="customerTable">Table disponible : <span>*</span></label>
                            <input name="customerTable" id="customerTable" class="form-control input-lg" readonly required value="${customerOrder.customerTable.number}" />
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div id="nbTabletGroup" class="form-group">
                            <label for="nbTablet">Nombre de tablettes : <span>*</span></label>
                            <input type="number" min="1" max="${customerOrder.customerTable.nbTablet}" class="form-control input-lg" id="nbTablet" name="nbTablet" value="${customerOrder.nbTablet}" <c:if test="${customerOrder.status != 'OPENED'}">readonly </c:if>required />
                        </div>
                    </div>
                </div>
            </fieldset>
            <c:if test="${not empty customerOrder.drinks or not empty customerOrder.dishes or not empty customerOrder.combos}">
                <fieldset>
                    <legend>Contenu de la commande</legend>
                    <c:if test="${not empty customerOrder.drinks}">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Boissons</div>
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <c:forEach items="${customerOrder.drinks}" var="drinkOrderLine" varStatus="loop">
                                        <tr data-id="${drinkOrderLine.id}">
                                            <td>${drinkOrderLine.drink.name} <small>${drinkOrderLine.drink.format.name}</small></td>
                                            <td>${drinkOrderLine.drink.category}</td>
                                            <td>${drinkOrderLine.status}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty customerOrder.dishes}">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Plats</div>
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <c:forEach items="${customerOrder.dishes}" var="dishOrderLine" varStatus="loop">
                                        <tr data-id="${dishOrderLine.id}">
                                            <td>${dishOrderLine.dish.name}</td>
                                            <td>${dishOrderLine.dish.category}</td>
                                            <td>${dishOrderLine.status}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty customerOrder.combos}">
                        <div class="panel panel-primary">
                            <div class="panel-heading">Menus</div>
                            <div class="panel-body">
                                <table class="table table-striped">
                                    <c:forEach items="${customerOrder.combos}" var="orderline" varStatus="loop">
                                        <thead>
                                            <tr data-id="${orderline.id}">
                                                <th colspan="3">Plats du menu : ${orderline.combo.name}</th>
                                            </tr>
                                        </thead>
                                        <c:forEach var="orderline" items="${orderline.dishes}" varStatus="loop">
                                            <tr data-id="${orderline.id}">
                                                <td>${orderline.dish.name}</td>
                                                <td>${orderline.dish.category}</td>
                                                <td>${orderline.status}</td>
                                            </tr>
                                        </c:forEach>                                
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                </fieldset>
            </c:if>            
        </div>

        <div class="tile-footer dvd dvd-top">
            <div class="row">
                <div class="col-xs-12 text-right">
                    <a href="FrontController?option=dashboard" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</a></button>
                    <c:if test="${customerOrder.status == 'OPENED'}">
                        <button type="submit" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="confirm"><i class="fa fa-save"></i> <span>Valider</span></button>
                    </c:if>                    
                </div>
            </div>
        </div>
    </form>
</section>