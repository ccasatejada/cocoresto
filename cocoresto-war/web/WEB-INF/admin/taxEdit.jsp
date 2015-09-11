<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="tile">
    <div class="tile-header dvd dvd-btm bg-greensea">
        <c:if test="${not empty tax}">
            <h1 class="custom-font"><strong>Modifier Taxe</strong></h1>
        </c:if>
        <c:if test="${empty tax}">
            <h1 class="custom-font"><strong>Nouvelle Taxe</strong></h1>
        </c:if>
    </div>
    <form action="FrontController?option=rate" class="form-horizontal" method="POST">
        <div class="tile-body">
            <div class="form-group">
                <label for="amount" class="col-sm-2 control-label">Montant : </label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="amount" value="${tax.rate}"> 
                </div>
            </div>
        </div>
        <div class="tile-footer dvd dvd-top">
            <div class="row">
                <div class="col-xs-12 text-right">
                    <a href="FrontController?option=rate" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</span></a>
                    <c:if test="${not empty tax}">
                        <button type="submit" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyTax"><i class="fa fa-save"></i> <span>Modifier</span></button>
                    </c:if>
                    <c:if test="${empty tax}">
                        <button type="submit" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="createTax"><i class="fa fa-save"></i> <span>Valider</span></button>
                    </c:if>
                </div>
            </div>
        </div>
    </form>
</section>
