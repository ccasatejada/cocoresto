<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="tile">

    <div class="tile-header dvd dvd-btm bg-greensea">
        <h1 class="custom-font"><strong>Tables</strong></h1>
        <ul class="controls">
            <li>
                <a href="FrontController?option=customerTable&task=add" role="button" tabindex="0" id="add-entry"><i class="fa fa-plus mr-5"></i> Ajouter</a>
            </li>
        </ul>
    </div>

    <div class="tile-body p-0">
        <table class="table table-striped">
            <thead>
                <tr class="bg-slategray">
                    <th>Numéro de table</th>
                    <th>Nombre de places</th>
                    <th>Nombre de tablettes</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>                    
                <c:forEach var="customerTable" items="${customerTables}" varStatus="loop">
                    <tr>
                        <td>Table n°${customerTable.number}</td>
                        <td>${customerTable.capacity} places</td>
                        <td>${customerTable.nbTablet} tablettes</td>
                        <td>
                            <a href="FrontController?option=customerTable&task=edit&id=${customerTable.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-edit"></i> <span>Modifier</span></a>
                            <a href="FrontController?option=customerTable&task=delete&id=${customerTable.id}" class="btn btn-lightred btn-rounded btn-ef btn-ef-5 btn-ef-5a"><i class="fa fa-trash"></i> <span>Supprimer</span></a>
                        </td>
                    </tr>                                    
                </c:forEach>
            </tbody>
        </table>
    </div>

    ${pagination}

</section>
