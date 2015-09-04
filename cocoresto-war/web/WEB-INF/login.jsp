<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <div class="row">
        <div class="col-sm-8 col-sm-push-2 mt-40">
            ${alert}
            <c:if test="${not logged}">
                <div class="row">

                    <div class="col-xs-12 col-sm-6">
                        <section class="tile">
                            <div class="tile-header dvd dvd-btm bg-greensea">
                                <h1 class="custom-font"><strong>Connexion du personnel</strong></h1>
                            </div>
                            <div class="tile-body">
                                <form action="FrontController?option=login" method="post">
                                    <div class="form-group">
                                        <label for="password">Saisissez votre code</label>
                                        <input class="form-control" type="password" name="password" id="password" maxlength="4" />
                                    </div>
                                    <button class="btn btn-primary" type="submit" name="connect">Se connecter</button>
                                </form>                
                            </div>
                        </section>
                    </div>

                    <div class="col-xs-12 col-sm-6">
                        <section class="tile">
                            <div class="tile-header dvd dvd-btm bg-greensea">
                                <h1 class="custom-font"><strong>Connexion client</strong></h1>
                            </div>
                            <div class="tile-body">
                                <form action="FrontController?option=login" method="post">
                                    <div class="form-group">
                                        <label for="tableNumber">Saisissez le numéro de table</label>
                                        <input class="form-control" type="text" name="tableNumber" id="tableNumber" maxlength="3" />
                                    </div>
                                    <button class="btn btn-primary" type="submit" name="connect">Se connecter</button>
                                </form>                
                            </div>
                        </section>
                    </div>

                </div>
            </c:if>

            <c:if test="${logged}">
                <a class="btn btn-primary" href="FrontController?option=login&task=disconnect" name="disconnect">Se déconnecter</a>
            </c:if>

        </div>
    </div>
</div>

