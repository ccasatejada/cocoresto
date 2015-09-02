<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="includes/head.jsp" %>
        <title>Panneau d'administration</title>
    </head>
    <body id="minovate" class="appWrapper">
        <div id="wrap" class="animsition">

            <c:if test="${logged && group > 2}">
                <%@include file="includes/adminMenu.jsp" %>
            </c:if>

            <section id="content">
                <div class="page page-dashboard">

                    <c:if test="${logged}">
                        <div class="pageheader">
                            <h2>CocoResto <span>// ${userName}</span></h2>
                            <div class="page-bar">
                                <ul class="page-breadcrumb">
                                    <li><a href="FrontController?option=dashboard"><i class="fa fa-home"></i> CocoResto</a></li>
                                    <li><a href="FrontController?option=dashboard">Panneau d'administration</a></li>
                                    ${pathway}
                                </ul>
                                <div class="page-toolbar">
                                    <a role="button" tabindex="0" class="btn btn-lightred no-border pickDate">
                                        <i class="fa fa-calendar"></i>&nbsp;&nbsp;<span>${date}</span>
                                    </a>
                                </div>
                            </div>
                        </div> 
                    </c:if>

                    <c:if test="${logged}">
                        ${alert}
                    </c:if>
                    <jsp:include page="${content}" />
                </div>
            </section>

        </div>        
        <%@include file="includes/scripts.jsp" %>
    </body>
</html>