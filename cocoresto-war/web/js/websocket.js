var wsUri = "ws://" + document.location.host + document.location.pathname + "/actions";
var socket = new WebSocket(wsUri);
socket.onmessage = onMessage;

function onMessage(event) {
    var help = JSON.parse(event.data);
    if (help.action === "add") {
        printHelpElement(help);
    }
    if (help.action === "remove") {
        document.getElementById(help.id).remove();
    }
}

function addHelp(table) {
    var HelpAction = {
        action: "add",
        id: table,
        count: 1
    };
    alert(HelpAction);
    socket.send(JSON.stringify(HelpAction));
    alert(JSON.stringify(HelpAction));
}

function removeHelp(element) {
    var table = element;
    var HelpAction = {
        action: "remove",
        id: table,
        count: -1
    };
    socket.send(JSON.stringify(HelpAction));
}

function printHelpElement(help) {
//    if(parseInt(help.count) === '0'){
//    var helpDiv = document.getElementById("helpDiv");
//    helpDiv.setAttribute("class", "btn-group btn-group-justified mb-20 mt-40");
//    helpDiv.setAttribute("role", "group");
//
//    var divAlert = document.createElement("div");
//    divAlert.setAttribute("class", "btn-group");
//    divAlert.setAttribute("role", "group");
//    helpDiv.appendChild(divAlert);
//
//    var divSpan = document.createElement("span");
//    divSpan.innerHTML = "<a href=\"FrontController?option=customerOrder&task=help\" class=\"btn btn - lg btn - lightred\"><i class=\"fa fa - bell\"></i> <span id=\"helpSt\"><strong>"+ help.count +"</strong></span> demande(s) d'aide en attente</a>";
//    divAlert.appendChild(divSpan);
//    } else {
//        var divSpan = document.getElementById("helpSt");
//        divSpan.innerHTML = help.count;
//    }
    var helpSpan = document.getElementById("helpSpan");
    helpSpan.innerHTML = help.count;
}

function hideForm() {
    document.getElementById("addHelp").remove();
}

function formSubmit() {
    var form = document.getElementById("addHelpForm");
    var table = form.elements["table"].value;
    hideForm();
    addHelp(table);
}
