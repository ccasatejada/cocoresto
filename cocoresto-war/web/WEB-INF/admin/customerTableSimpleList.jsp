<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="FrontController?option=customerOrder" method="post">
    <button type="submit">Sélectionner</button>
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
                    <td>
                        <label>
                            <input name="customerTableId" value="${table.id}" type="radio" />
                            Table n°${table.number}
                        </label>
                    </td>
                    <td>${table.capacity} couverts</td>
                    <td>${table.nbTablet} tablettes</td>
                </tr>                   
            </c:forEach>
        </tbody>
    </table>
</form>

${pagination}
