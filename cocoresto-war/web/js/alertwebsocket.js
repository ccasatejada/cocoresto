var wsUri = "ws://" + document.location.host + document.location.pathname + "/alert";
//var wsUri2 = "ws://" + document.location.host + "/cocoresto-war/FrontController/alert";
var socketAlert = new WebSocket(wsUri);
socketAlert.onmessage = onMessage;
window.onload = formListener;

function onMessage(e) {
    var alertStatus = JSON.parse(e.data);
    var tdOP = null;
    var tds = document.getElementsByTagName("td");
    for (var i = 0; i < tds.length; i++) {
        console.log(parseInt(tds[i].dataset.idorderline) + " : " + alertStatus.idOrderLineAlert)
        
        if (parseInt(tds[i].dataset.idorderline) == alertStatus.idOrderLineAlert) {
            console.log("coucou");
            tdOP = tds[i];
        }
    }

    if (alertStatus.action === "onprep") {
        tdOP.innerHTML = "<span class=\"label label-info\">" + alertStatus.status + "</span>";
    }
    if (alertStatus.action === "ready") {
        tdOP.innerHTML = "<span class=\"label label-success\">" + alertStatus.status + "</span>";

    }
    //tdOP.appendChild(spanOP);
}

function formListener() {
    var formOnPrep = document.querySelectorAll('#ordersToDo .btn.status');
    for (var i = 0; i < formOnPrep.length; i++) {
        formOnPrep[i].addEventListener('click', function (e) {
            var product = e.target;
            // id order
            var order = product.dataset.order;
            // id dish, combo/dishcombo, drink, validate to on prep
            var dish = product.dataset.dish;
            var combo = product.dataset.combo;
            var dishcombo = product.dataset.dishcombo;
            var drink = product.dataset.drink;
            sendOnPrep(order, dish, combo, dishcombo, drink);
        });
    }


    var formReady = document.querySelectorAll('#ordersInProcess .btn.status');
    for (var i = 0; i < formReady.length; i++) {
        formReady[i].addEventListener('click', function (e) {
            var product = e.target;
            // id order 
            var corder = product.dataset.order;
            // id dish, combo/dishcombo, drink, on prep to ready
            var dishonprep = product.dataset.dish;
            var comboonprep = product.dataset.combo;
            var dishcomboonprep = product.dataset.dishcombo;
            var drinkonprep = product.dataset.drink;
            sendReady(corder, dishonprep, comboonprep, dishcomboonprep, drinkonprep);
        });
    }
}


function sendOnPrep(order, dish, combo, dishcombo, drink) {
    if (typeof dish !== "undefined") {
        var AlertAction = {
            action: "onprep",
            element: "dish",
            order: order,
            dish: dish
        };
        socketAlert.send(JSON.stringify(AlertAction));
    }
    if (typeof drink !== "undefined") {
        var AlertAction = {
            action: "onprep",
            element: "drink",
            order: order,
            drink: drink
        };
        socketAlert.send(JSON.stringify(AlertAction));
    }
    if (typeof combo !== "undefined") {
        var AlertAction = {
            action: "onprep",
            element: "combo",
            order: order,
            combo: combo,
            dishcombo: dishcombo
        };
        socketAlert.send(JSON.stringify(AlertAction));
    }
}

function sendReady(corder, dishonprep, comboonprep, dishcomboonprep, drinkonprep) {
    if (typeof dishonprep !== "undefined") {
        var AlertAction = {
            action: "ready",
            element: "dish",
            order: corder,
            dish: dishonprep
        };
        socketAlert.send(JSON.stringify(AlertAction));
    }
    if (typeof drinkonprep !== "undefined") {
        var AlertAction = {
            action: "ready",
            element: "drink",
            order: corder,
            drink: drinkonprep
        };
        socketAlert.send(JSON.stringify(AlertAction));
    }
    if (typeof comboonprep !== "undefined") {
        var AlertAction = {
            action: "ready",
            element: "combo",
            order: corder,
            combo: comboonprep,
            dishcombo: dishcomboonprep
        };
        socketAlert.send(JSON.stringify(AlertAction));
    }
}



