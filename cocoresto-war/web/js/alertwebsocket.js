var wsUri = "ws://" + document.location.host + document.location.pathname + "/alert";
var socketAlert = new WebSocket(wsUri);
socketAlert.onmessage = onMessage;

//function GetURLParameter(sParam) {
//    var sPageURL = window.location.search.substring(1);
//    var sURLVariables = sPageURL.split('&');
//    for (var i = 0; i < sURLVariables.length; i++) {
//        var sParameterName = sURLVariables[i].split('=');
//        if (sParameterName[0] === sParam) {
//            return sParameterName[1];
//        }
//    }
//};
// example of usage for : "http://dummy.com/?technology=jquery&blog=jquerybyexample". 
// var tech = GetURLParameter('technology');
// var blog = GetURLParameter('blog');

function onMessage(e) {
    var alert = JSON.parse(e.data);
    if (alert.action === "onprep") {

    }
    if (alert.action === "ready") {

    }

}

function eventListener(){
document.querySelectorAll('#ordersToDo .btn.status').addEventListener('click', function () {
    var product = e.target;
    alert(product);
    // id order
    var order = product.dataset.order;
    // id dish, combo/dishcombo, drink, validate to on prep
    var dish = product.dataset.dish;
    var combo = product.dataset.combo;
    var dishcombo = product.dataset.dishcombo;
    var drink = product.dataset.drink;
    sendOnPrep(order, dish, combo, dishcombo, drink);
    alert(order + " dish : " + dish + " / combo : " + combo + " / dishcombo : " + dishcombo + " / drink : " + drink);

    // id order 
    var corder = product.dataset.corder;
    // id dish, combo/dishcombo, drink, on prep to ready
    var dishonprep = product.dataset.dishonprep;
    var comboonprep = product.dataset.comboonprep;
    var dishcomboonprep = product.dataset.dishcomboonprep;
    var drinkonprep = product.dataset.drinkonprep;
    sendReady(corder, dishonprep, comboonprep, dishcomboonprep, drinkonprep);
    alert(corder + " dishop : " + dishonprep + " / comboop : " + comboonprep + " / dishcomboop : " + dishcomboonprep + " / drink : " + drinkonprep);

});
}

function sendOnPrep(order, dish, combo, dishcombo, drink) {
    var AlertAction = {
        action: "onprep",
        order: order,
        dish: dish,
        combo: combo,
        dishcombo: dishcombo,
        drink: drink
    };
    socketAlert.send(JSON.stringify(AlertAction));
}

function sendReady(corder, dishonprep, comboonprep, dishcomboonprep, drinkonprep) {
    var AlertAction = {
        action: "ready",
        order: corder,
        dish: dishonprep,
        combo: comboonprep,
        dishcombo: dishcomboonprep,
        drink: drinkonprep
    };
    socketAlert.send(JSON.stringify(AlertAction));
}

function printAlertElement(alert) {

}


