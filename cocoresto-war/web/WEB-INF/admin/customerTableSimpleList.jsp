<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="table table-striped">
    <thead>
        <tr class="bg-slategray">
            <th>Numéro</th>
            <th>Couverts</th>
            <th>Tablettes</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="table" items="${customerTables}" varStatus="loop">
            <tr>
                <td><a href="">Table n°${table.number}</a></td>
                <td>${table.capacity} couverts</td>
                <td>${table.nbTablet} tablettes</td>
            </tr>                   
        </c:forEach>
    </tbody>
</table>

${pagination}
