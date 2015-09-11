<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section id="header" class="scheme-default">
    <header class="clearfix">

        <div class="branding scheme-default">
            <a class="brand" href="FrontController?option=dashboard">
                <span><strong>COCO</strong>RESTO</span>
            </a>
        </div>

        <c:if test="${logged && group > 2}">
            <ul class="nav-left pull-left list-inline">
                <li class="dropdown">
                    <a href="FrontController?option=employee" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Employés <span class="caret"></span></a>
                    <ul class="dropdown-menu animated littleFadeInRight" role="menu">
                        <li><a href="FrontController?option=employee" tabindex="0"><i class="fa fa-list"></i>Liste</a></li>
                        <li><a href="FrontController?option=employee&task=edit" tabindex="0"><i class="fa fa-plus"></i>Ajouter</a></li>
                    </ul>          
                </li>
                <li class="dropdown">
                    <a href="FrontController?option=customerTable" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Tables <span class="caret"></span></a>
                    <ul class="dropdown-menu animated littleFadeInRight" role="menu">
                        <li><a href="FrontController?option=customerTable" tabindex="0"><i class="fa fa-list"></i>Liste</a></li>
                        <li><a href="FrontController?option=customerTable&task=add" tabindex="0"><i class="fa fa-plus"></i>Ajouter</a></li>
                    </ul>          
                </li>
                <li class="dropdown">
                    <a href="FrontController?option=customerOrder" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Commandes <span class="caret"></span></a>
                    <ul class="dropdown-menu animated littleFadeInRight" role="menu">
                        <li><a href="FrontController?option=customerOrder" tabindex="0"><i class="fa fa-list"></i>Liste</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="FrontController?option=rate" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Taxes <span class="caret"></span></a>
                    <ul class="dropdown-menu animated littleFadeInRight" role="menu">
                        <li><a href="FrontController?option=rate" tabindex="0"><i class="fa fa-list"></i>Liste</a></li>
                        <li><a href="FrontController?option=rate&task=editTax" tabindex="0"><i class="fa fa-plus"></i>Ajouter une taxe</a></li>
                        <li><a href="FrontController?option=rate&task=editDiscount" tabindex="0"><i class="fa fa-plus"></i>Ajouter une promo</a></li>
                    </ul>          
                </li>
                <li class="dropdown">
                    <a href="FrontController?option=category" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Catégories <span class="caret"></span></a>
                    <ul class="dropdown-menu animated littleFadeInRight" role="menu">
                        <li><a href="FrontController?option=category" tabindex="0"><i class="fa fa-list"></i>Liste</a></li>
                        <li><a href="FrontController?option=category&task=edit" tabindex="0"><i class="fa fa-plus"></i>Ajouter</a></li>
                    </ul>          
                </li>
                <li class="dropdown">
                    <a href="FrontController?option=dish" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Plats <span class="caret"></span></a>
                    <ul class="dropdown-menu animated littleFadeInRight" role="menu">
                        <li><a href="FrontController?option=dish" tabindex="0"><i class="fa fa-list"></i>Liste</a></li>
                        <li><a href="FrontController?option=dish&task=edit" tabindex="0"><i class="fa fa-plus"></i>Ajouter</a></li>
                    </ul>          
                </li>
                <li class="dropdown">
                    <a href="FrontController?option=drink" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Boissons <span class="caret"></span></a>
                    <ul class="dropdown-menu animated littleFadeInRight" role="menu">
                        <li><a href="FrontController?option=drink" tabindex="0"><i class="fa fa-list"></i>Liste</a></li>
                        <li><a href="FrontController?option=drink&task=edit" tabindex="0"><i class="fa fa-plus"></i>Ajouter</a></li>
                    </ul>          
                </li>
                <li class="dropdown">
                    <a href="FrontController?option=combo" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Menus <span class="caret"></span></a>
                    <ul class="dropdown-menu animated littleFadeInRight" role="menu">
                        <li><a href="FrontController?option=combo" tabindex="0"><i class="fa fa-list"></i>Liste</a></li>
                        <li><a href="FrontController?option=combo&task=edit" tabindex="0"><i class="fa fa-plus"></i>Ajouter</a></li>
                    </ul>          
                </li>
            </ul>
        </c:if>
        
        <c:if test="${logged && group > 0}">
            <ul class="nav-right pull-right list-inline">
                <li class="dropdown">
                    <a href="FrontController?option=login&task=disconnect" tabindex="0"><i class="fa fa-sign-out"></i> Déconnexion</a>
                </li>
            </ul>
        </c:if>
    </header>
</section>    