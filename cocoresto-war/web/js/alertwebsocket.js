var wsUri = "ws://" + document.location.host + document.location.pathname + "/alert";
//var wsUri2 = "ws://" + document.location.host + "/cocoresto-war/FrontController/alert";
var socketAlert = new WebSocket(wsUri);
socketAlert.onmessage = onMessage;
window.onload = formListener;

function onMessage(e) {
    var alert = JSON.parse(e.data);
    if (alert.action === "onprep") {

    }
    if (alert.action === "ready") {

    }

}

function formListener() {
    var formOnPrep = document.querySelectorAll('#ordersToDo .btn.status');
    for (var i = 0; i < formOnPrep.length; i++) {
        formOnPrep[i].addEventListener('click', function (e) {
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
        });
    }


    var formReady = document.querySelectorAll('#ordersInProcess .btn.status');
    for (var i = 0; i < formReady.length; i++) {
        formReady[i].addEventListener('click', function (e) {
            var product = e.target;
            alert(product);
            // id order 
            var corder = product.dataset.order;
            // id dish, combo/dishcombo, drink, on prep to ready
            var dishonprep = product.dataset.dish;
            var comboonprep = product.dataset.combo;
            var dishcomboonprep = product.dataset.dishcombo;
            var drinkonprep = product.dataset.drink;
            sendReady(corder, dishonprep, comboonprep, dishcomboonprep, drinkonprep);
            alert(corder + " dishop : " + dishonprep + " / comboop : " + comboonprep + " / dishcomboop : " + dishcomboonprep + " / drink : " + drinkonprep);
        });
    }
}

function formReadyListener() {

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
    alert(AlertAction);
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


