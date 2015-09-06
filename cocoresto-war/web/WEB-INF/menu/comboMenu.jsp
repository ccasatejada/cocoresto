<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="comboMenu" class="container-fluid">
    <div class="row">
        <h1>Ca marche!</h1>
        <c:forEach var="combo" items="${combos}" varStatus="loop">
            <div class="col-sm-4 mt-20">
                <a href="FrontController?option=menu&task=getComboDetail&id=${combo.id}" class="drinkDetail btn btn-cyan btn-rounded btn-ef btn-ef-5 btn-ef-5a btn-lg" name="getDetail">
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>                    
                </a>
            </div>
        </c:forEach>
    </div>
</div>

