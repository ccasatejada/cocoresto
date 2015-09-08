window.onload = init;
var wsUri = "ws://" + document.location.host + document.location.pathname + "/actions";
var socket = new WebSocket(wsUri);
socket.onmessage = onMessage;

function onMessage(event){
    var help = JSON.parse(event.data);
    if(help.action === "add"){
        printHelpElement(help);
    }
    if(help.action === "remove"){
        document.getElementById(help.id).remove();
    }
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
    document.querySelector("#content strong").innerHTML = help;
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
    var id = form.elements["customerTable"].value;
    hideForm();
    document.getElementById("addHelp").reset();
    addHelp(customerTable);
}

function init(){
    showForm();
}