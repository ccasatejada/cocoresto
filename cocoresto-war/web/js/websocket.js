//window.onload = init;
 var wsUri = "ws://" + document.location.host + document.location.pathname + "/actions";
//var wsUri = "ws://localhost:8080/FrontController?option=dashboard/actions";
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
        id: customerTable,
        count: 1
    };
    alert(HelpAction);
    socket.send(JSON.stringify(HelpAction));
    alert(JSON.stringify(HelpAction));
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
//    document.querySelector("#content strong").innerHTML = help.count;
    var content = document.getElementById("content");
    var helpDiv = document.createElement("div");
    helpDiv.setAttribute("id", help.id);
    helpDiv.setAttribute("class", "help")
    content.appendChild(helpDiv);
    
    var helpCount = document.createElement("span");
    helpCount.setAttribute("class", "helpCount");
    helpCount.innerHTML = "<strong>" + help.count + "</strong>";
    helpDiv.appendChild(helpCount);
}

//function showForm(){
//   document.getElementById("addHelpForm"); 
//}
//
//function hideForm(){
//    document.getElementById("addHelpForm");
//}

function formSubmit(){
    var form = document.getElementById("addHelpForm");
    var id = form.elements["customerTable"].value;
//    hideForm();
//    document.getElementById("addHelp").reset();
    addHelp(id);
}

//function init(){
//    showForm();
//}