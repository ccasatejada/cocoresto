<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">

    <div class="row">
        <div class="col-sm-4 col-sm-push-4 mt-40">

            ${alert}

            <section class="tile">

                <div class="tile-header dvd dvd-btm bg-greensea">
                    <h1 class="custom-font"><strong>Connexion</strong></h1>
                </div>

                <div class="tile-body">
                    <form action="FrontController?option=login" method="post">
                        <c:if test="${not logged}">
                            <div class="form-group">
                                <label for="password">Saisissez votre code &nbsp;</label>
                                <input class="form-control" type="password" name="password" id="password" maxlength="4" />
                            </div>
                            <button class="btn btn-primary" type="submit" name="connect">Se connecter</button>
                        </c:if>
                        <c:if test="${logged}">
                            <a class="btn btn-primary" href="FrontController?option=login&task=disconnect" name="disconnect">Se déconnecter</a>
                        </c:if>
                    </form>                
                </div>
            </section>
        </div>
    </div>
</div>

