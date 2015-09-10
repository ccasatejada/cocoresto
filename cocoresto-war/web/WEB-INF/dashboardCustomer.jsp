<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="menu">
    <div class="row">
        <section class="col-sm-8">
            <%@include file="includes/customerMenu.jsp" %>
            <div id="menuContent">
                
            </div>
        </section>
        <section id="cartContent" class="col-sm-4">
            <div id="addHelp" class="mb-20">
                <form id="addHelpForm">
                    <input type="hidden" id="table" value="${table}">
                    <button type="button" class="btn btn-lg btn-lightred" onclick="formSubmit();"><i class="fa fa-bell"></i>&nbsp;Demander de l'aide</button>
                </form>
            </div>
            <%@include file="includes/cartList.jsp" %>
        </section>
    </div>
</div>