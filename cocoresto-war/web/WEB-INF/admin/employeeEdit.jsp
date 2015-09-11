<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="tile">
    <div class="tile-header dvd dvd-btm bg-greensea">
        <c:if test="${not empty employee}">
            <h1 class="custom-font"><strong>Modifier Employé</strong></h1>
        </c:if>
        <c:if test="${empty employee}">
            <h1 class="custom-font"><strong>Nouvel Employé</strong></h1>
        </c:if>
    </div>
    <form action="FrontController?option=employee" class="form-horizontal" method="POST">
        <div class="tile-body">
            <div class="form-group">
                <label for="comboGroupEmployee" class="col-sm-2 control-label">Groupe</label>
                <div class="col-sm-10">
                    <select name="comboGroupEmployee" class="form-control">
                        <c:forEach var="employeeGroup" items="${employeeGroups}" varStatus="loop">
                            <c:if test="${not empty employee}">
                                <c:if test="${employee.employeeGroup.id==employeeGroup.id}">
                                    <option selected>${employeeGroup.name}</option>
                                </c:if>
                                <c:if test="${employee.employeeGroup.id!=employeeGroup.id}">
                                    <option>${employeeGroup.name}</option>
                                </c:if>
                            </c:if>

                            <c:if test="${empty employee}">
                                <option>${employeeGroup.name}</option>
                            </c:if>   
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="lastName" class="col-sm-2 control-label">Nom : </label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" required name="lastName" value="${employee.lastName}"> 
                </div>
            </div>
            <div class="form-group">
                <label for="firstName" class="col-sm-2 control-label">Prénom : </label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" required name="firstName" value="${employee.firstName}">
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-sm-2 control-label">Mot de passe : </label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" required name="password" value="${employee.password}"> 
                </div>
            </div>
            <div class="form-group">
                <label for="confirmPassword" class="col-sm-2 control-label">Confirmer mot de passe : </label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" required name="confirmPassword"> 
                </div>
            </div>
        </div> 
        <div class="tile-footer dvd dvd-top">
            <div class="row">
                <div class="col-xs-12 text-right">
                    <a href="FrontController?option=employee" class="btn btn-darkgray btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-remove"></i> <span>Annuler</a></button>
                    <c:if test="${not empty employee}">
                        <button type="submit" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyIt"><i class="fa fa-save"></i> <span>Modifier</span></button>
                    </c:if>
                    <c:if test="${empty employee}">
                        <button type="submit" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="createIt"><i class="fa fa-save"></i> <span>Valider</span></button>
                    </c:if>
                </div>
            </div>
        </div>
    </form>
</section>
