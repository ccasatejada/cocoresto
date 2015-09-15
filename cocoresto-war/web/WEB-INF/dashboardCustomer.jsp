<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="menu">
    <div class="row">
        <section class="col-sm-8">
            <%@include file="includes/customerMenu.jsp" %>
            <div id="menuContent"></div>
        </section>
        <section id="cartContent" class="col-sm-4">
            <c:if test="${group eq 0}">
                <div id="addHelp" class="mb-20">
                    <form id="addHelpForm">
                        <input type="hidden" id="table" value="${table}">
                        <button type="button" class="btn btn-lg btn-lightred" onclick="formSubmit();"><i class="fa fa-bell"></i>&nbsp;Demander de l'aide</button>
                    </form>
                </div>
            </c:if>   
            <%@include file="includes/cartList.jsp" %>
        </section>
    </div>
    <div class="modal fade" id="detailModal" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="detailModalLabel">Détail</h4>
                </div>
                <div class="modal-body">
                    <iframe height="400" src="" style="border:none;width:100%;"></iframe>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
                </div>
            </div>
        </div>
    </div>
</div>