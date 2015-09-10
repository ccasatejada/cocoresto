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
// example of usage
// var tech = GetURLParameter('technology');
// var blog = GetURLParameter('blog');

function onMessage(event) {

}

function sendToWaiter() {

}

function sendToCustomer() {

}

function printAlertElement(alert) {

}


