window.onload = init;
var wsUri = "ws://" + document.location.host + document.location.pathname + "/actions";
alert(wsUri);
var socket = new WebSocket(wsUri);
socket.onmessage = onMessage;

function onMessage(event){
    
}

function addHelp(customerTable){
    var HelpAction = {
        action: "add",
        customerTable: customerTable
    };
    socket.send(JSON.stringify(addHelp));
}

function removeHelp(element){
    var id = element;
    var HelpAction = {
      action: "remove",
      id: id
    };
    socket.send(JSON.stringify(HelpAction));
}

function printHelpElement(help){
    
}

function showForm(){
   document.getElementById("addHelpForm"); 
}
//
//function hideForm(){
//    document.getElementById("addHelpForm");
//}

function formSubmit(){
    var form = document.getElementById("addHelpForm");
    var customerTable = form.elements["customerTable"].value;
    hideForm();
    document.getElementById("addHelp").reset();
    addHelp(customerTable);
}

function init(){
    showForm();
}