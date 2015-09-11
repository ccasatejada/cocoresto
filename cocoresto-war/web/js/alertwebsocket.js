var wsUri = "ws://" + document.location.host + document.location.pathname + "/alert";
var socket = new WebSocket(wsUri);
socket.onmessage = onMessage;

function GetURLParameter(sParam) {
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] === sParam) {
            return sParameterName[1];
        }
    }
};
// example of usage for : "http://dummy.com/?technology=jquery&blog=jquerybyexample". 
// var tech = GetURLParameter('technology');
// var blog = GetURLParameter('blog');

function onMessage(event) {
    var alert = JSON.parse(event.data);
    
}

// document.querySelectorAll('#ordersToDo .btn.status').addEventListener('click', function(e) {
//  var product = e.target;
//  var order = product.dataset.order;
//  var dish = product.dataset.dish
//  var combo = product.dataset.combo
//  var dishcombo = product.dataset.dishcombo
//  var drink = product.dataset.drink
//  var corder = product.dataset.corder
//  var dishonprep = product.dataset.dishonprep
//  var comboonprep = product.dataset.comboonprep
//  var dishcomboonprep = product.dataset.dishcomboonprep
//  var drinkonprep = product.dataset.drinkonprep
// 
// });
// 
// product.dataset.dish
// product.dataset.drink
// product.dataset.combo

// var product = document.querySelectorAll('#ordersInProcess .btn.status').addEventListener('click', mafonction);
// product.dataset.order
// product.dataset.dish
// product.dataset.drink
// product.dataset.combo

function sendToWaiter() {
    var AlertAction = {
        action: "add",
        id : order,
        
    }
}

function sendToCustomer() {

}

function printAlertElement(alert) {
    
}


