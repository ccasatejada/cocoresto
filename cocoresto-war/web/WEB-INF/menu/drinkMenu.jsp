<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/includes/head.jsp" %>
        <title>Menu Boissons</title>
    </head>
    <body>
        <%@include file="/WEB-INF/includes/customerMenu.jsp" %>
        <section class="tile">

            <div class="container-fluid">
                <div class="row">
                    <c:forEach var="drink" items="${drinks}" varStatus="loop">
                        <div class="col-sm-4 mt-20">
                            <a href="FrontController?option=menu&task=getDrinkDetail&id=${drink.id}" class="btn btn-cyan btn-rounded btn-ef btn-ef-5 btn-ef-5a btn-lg" name="modifyIt">
                                <figure>
                                    <img src="images/drinks/${drink.image}" alt="image:${drink.name}" height="220" width="220" class="img-rounded">
                                    <figcaption>${drink.name}</figcaption>
                                </figure>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>
        <%@include file="/WEB-INF/includes/scripts.jsp" %>
    </body>
</html>