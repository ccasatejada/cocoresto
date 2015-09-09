<div class="row mb-40" id="customerMenu">
    <div class="col-xs-12">
        <div class="btn-group btn-group-justified" role="group">
            <div class="btn-group" role="group">
                <a class="btn btn-greensea btn-lg" data-query="dishes"><i class="fa fa-list"></i>&nbsp;Plats</a>
            </div>
            <div class="btn-group" role="group">
                <a class="btn btn-greensea btn-lg" data-query="drinks"><i class="fa fa-list"></i>&nbsp;Boissons</a>
            </div>
            <div class="btn-group" role="group">
                <a class="btn btn-greensea btn-lg" data-query="combos"><i class="fa fa-list"></i>&nbsp;Menus</a>
            </div>
        </div>
    </div>
</div>

<div id="addHelp">
    <form id="addHelpForm">
        <input type="hidden" id="table" value="${table}">
        <input type="button" class="button" value="Demander de l'aide" onclick=formSubmit()>
    </form>
</div>
