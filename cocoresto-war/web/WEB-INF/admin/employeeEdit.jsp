<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../includes/head.jsp" %>
        <title>Editer l'employé</title>
    </head>
    <body>
        <div class="container-fluid">
            <form action="FrontController?option=employee" class="well form-horizontal" method="POST">
                <c:if test="${not empty employee}">
                    <h1>Modifier Employé</h1>
                </c:if>
                <c:if test="${empty employee}">
                    <h1>Nouvel Employé</h1>
                </c:if>

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
                        <input type="text" class="form-control" name="lastName" value="${employee.lastName}"> 
                    </div>
                </div>
                <div class="form-group">
                    <label for="firstName" class="col-sm-2 control-label">Prénom : </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="firstName" value="${employee.firstName}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">Mot de passe : </label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" name="password" value="${employee.password}"> 
                    </div>
                </div>
                <div class="form-group">
                    <label for="confirmPassword" class="col-sm-2 control-label">Confirmer mot de passe : </label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" name="confirmPassword"> 
                    </div>
                </div>
                <c:if test="${not empty employee}">
                    <button type="submit" class="btn btn-default" name="modifyIt">Modifier</button>
                </c:if>
                <c:if test="${empty employee}">
                    <button type="submit" class="btn btn-default" name="createIt">Valider</button>
                </c:if>
                <button type="submit" class="btn btn-default" name="cancelIt">Annuler</button>

            </form>
        </div>

        <%@include file="../includes/scripts.jsp" %>
    </body>
</html>
