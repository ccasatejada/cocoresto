<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Employés</strong></h1>
        <ul class="controls">
            <li>
                <a href="FrontController?option=employee&task=edit" role="button" tabindex="0" id="add-entry" name="addEmployee"><i class="fa fa-plus mr-5"></i> Ajouter</a>
            </li>
        </ul>
    </div>

    <div class="tile-body p-0">
        <table class="table table-striped">
            <thead>
                <tr class="bg-slategray">
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Poste</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="employee" items="${employees}" varStatus="loop">
                    <tr>
                        <td>${employee.lastName}</td>
                        <td>${employee.firstName}</td>
                        <td>${employee.employeeGroup}</td>
                        <td>
                            <a href="FrontController?option=employee&task=modify&id=${employee.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="modifyIt">Modifier</a>
                            <a href="FrontController?option=employee&task=delete&id=${employee.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="deleteIt">Supprimer</a>
                        </td>
                    </tr>                   
                </c:forEach>
            </tbody>
        </table>
    </div>

    ${pagination}

</section>