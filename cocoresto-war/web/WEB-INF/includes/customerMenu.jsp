<div class="row">
    <div class="card-container col-lg-4 col-sm-6 col-sm-12">
        <div class="card">
            <div class="front bg-greensea">
                <div class="row">
                    <div class="col-xs-4">
                        <i class="fa fa-cutlery fa-4x"></i>
                    </div>
                    <div class="col-xs-8">
                        <p class="text-elg text-strong mb-0">Plats</p>
                        <span>${countDish}</span>
                    </div>
                </div>
            </div>
            <div class="back bg-greensea">
                <div class="row">
                    <div class="col-xs-12">
                        <a href="#" id="getDishes"><i class="fa fa-list fa-2x"></i> Voir la liste</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="card-container col-lg-4 col-sm-6 col-sm-12">
        <div class="card">
            <div class="front bg-blue">
                <div class="row">
                    <div class="col-xs-4">
                        <i class="fa fa-glass fa-4x"></i>
                    </div>
                    <div class="col-xs-8">
                        <p class="text-elg text-strong mb-0">Boissons</p>
                        <span>${countDrink}</span>
                    </div>
                </div>
            </div>
            <div class="back bg-blue">
                <div class="row">
                    <div class="col-xs-12">
                        <a href="#" id="getDrinks"><i class="fa fa-list fa-2x"></i> Voir la liste</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="card-container col-lg-4 col-sm-6 col-sm-12">
        <div class="card">
            <div class="front bg-lightred">
                <div class="row">
                    <div class="col-xs-4">
                        <i class="fa fa-file fa-4x"></i>
                    </div>
                    <div class="col-xs-8">
                        <p class="text-elg text-strong mb-0">Menus</p>
                        <span>${countCombo}</span>
                    </div>
                </div>
            </div>
            <div class="back bg-lightred">
                <div class="row">
                    <div class="col-xs-12">
                        <a href="#" id="getCombos"><i class="fa fa-list fa-2x"></i> Voir la liste</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="card-container col-lg-4 col-sm-6 col-sm-12">
        <div class="card">
            <div class="front bg-lightred">
                <div class="row">
                    <div class="col-xs-4">
                        <i class="fa fa-info-circle fa-4x"></i>
                    </div>
                    <div class="col-xs-8">
                        <p class="text-elg text-strong mb-0">Demander de l'aide</p>
                    </div>
                    <div class="back bg-lightred">
                        <div class="row">
                            <div class="col-xs-12">
                                <div id="addHelp">
                                    <form id="addHelpForm">
                                        <input type="hidden" id="customerTable" value="${customerOrder.customerTable.number}">
                                        <input type="button" class="button" value="Demander de l'aide" onclick=formSubmit()>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



