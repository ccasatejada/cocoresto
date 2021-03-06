<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="tile">
    <div class="tile-header dvd dvd-btm bg-greensea">
        <c:if test="${not empty discount}">
            <h1 class="custom-font"><strong>Modifier Discount</strong></h1>
        </c:if>
        <c:if test="${empty discount}">
            <h1 class="custom-font"><strong>Nouveau Discount</strong></h1>
        </c:if>
    </div>
    <form action="FrontController?option=rate" class="form-horizontal" method="POST">
        <div class="tile-body">
            <div class="form-group">
                <label for="amount" class="col-sm-2 control-label">Montant : </label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="amount" value="${discount.rate}"> 
                </div>
            </div>
            <div class="form-group">
                <label for="beginDate" class="col-sm-2 control-label">Date d�but : </label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="beginDate" required placeholder="jj/mm/aaaa" maxlength="10" value="<fmt:formatDate value='${discount.beginDate}' pattern='dd/MM/yyyy'/>"> 
                </div>
            </div>
            <div class="form-group">
                <label for="endDate" class="col-sm-2 control-label">Date fin : </label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="endDate" required placeholder="jj/mm/aaaa" maxlength="10" value="<fmt:formatDate value='${discount.endDate}' pattern='dd/MM/yyyy'/>"> 
                </div>
            </div>
        </div>
        <div class="tile-footer dvd dvd-top">
            <div class="row">
                <div class="col-xs-12 text-right">
                    <a href="FrontController?option=rate" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</span></a>
                    <c:if test="${not empty discount}">
                        <button type="submit" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyDiscount"><i class="fa fa-save"></i> <span>Modifier</span></button>
                    </c:if>
                    <c:if test="${empty discount}">
                        <button type="submit" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="createDiscount"><i class="fa fa-save"></i> <span>Valider</span></button>
                    </c:if>
                    <c:if test="${not empty isDrinkDiscount}">
                        <button type="submit" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="attachDiscount"><i class="fa fa-save"></i> <span>Attacher Discount</span></button>
                    </c:if>
                </div>
            </div>
        </div>
    </form>
</section>
