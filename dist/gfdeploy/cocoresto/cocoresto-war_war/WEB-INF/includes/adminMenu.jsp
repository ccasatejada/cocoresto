<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">

        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false">
                <span class="sr-only">Navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="FrontController?option=dashboard">CocoResto</a>
        </div>

        <div class="collapse navbar-collapse" id="navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="FrontController?option=employee">Employés</a></li>
                <li><a href="FrontController?option=customerTable">Tables</a></li>
                <li class="dropdown">
                    <a href="FrontController?option=dish" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Carte <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="FrontController?option=category">Catégories</a></li>
                        <li class="divider"></li>
                        <li><a href="FrontController?option=dish">Plats</a></li>
                        <li class="divider"></li>
                        <li><a href="FrontController?option=drink">Boissons</a></li>
                        <li class="divider"></li>
                        <li><a href="FrontController?option=combo">Menus</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="FrontController?option=tax" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Prix <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="FrontController?option=tax">Taxes</a></li>
                        <li class="divider"></li>
                        <li><a href="FrontController?option=discount">Promotions</a></li>
                    </ul>
                </li>
            </ul>

                <form class="navbar-right navbar-form" action="FrontController?option=login" method="post">
                    <button class="btn btn-primary" type="submit" name="disconnect">Se déconnecter</button>
                </form>
            
        </div>
    </div>
</nav>