<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="menu">
    <%@include file="/WEB-INF/includes/customerMenu.jsp" %>
    <div class="row">
        <section id="menuContent" class="col-sm-8">

        </section>
        <section id="cartContent" class="col-sm-4">
            <%@include file="/WEB-INF/includes/cartList.jsp" %>
        </section>
    </div>
</div>