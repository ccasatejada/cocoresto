<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="table table-striped">
    <thead>
        <tr class="bg-slategray">
            <th>Numéro</th>
            <th>Couverts</th>
            <th>Tablettes</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="table" items="${customerTables}" varStatus="loop">
            <tr>
                <td>Table n°${table.number}</td>
                <td>${table.capacity} couverts</td>
                <td>${table.nbTablet} tablettes</td>
                <td>
                    <a href="FrontController?option=customerOrder&task=edit&id=${customerOrder.id}" class="btn btn-greensea btn-rounded btn-ef btn-ef-5 btn-ef-5a" name="selectIt">Sélectionner</a>
                </td>
            </tr>                   
        </c:forEach>
    </tbody>
</table>

${pagination}
