<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/includes/head.jsp" %>
        <title>JSP Page</title>
    </head>
    <body id="minovate" class="appWrapper">
        <div id="wrap" class="animsition">
            <section id="content">

                <div class="page page-dashboard">

                    <%@include file="/WEB-INF/includes/customerMenu.jsp" %>
                    <section id="menuContent" class="tile col-lg-8 col-sm-6 col-sm-12 p-0">

                    </section>


                    <%@include file="/WEB-INF/includes/cartList.jsp" %>
                </div>
            </section>
        </div>
        <%@include file="/WEB-INF/includes/scripts.jsp" %>
    </body>
</html>
