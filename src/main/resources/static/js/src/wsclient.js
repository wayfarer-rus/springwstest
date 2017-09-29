/* global $ */
"use strict";

$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null){
       return null;
    }
    else{
       return decodeURI(results[1]) || 0;
    }
}

$(function () {
    var content = $('#content');

    // if user is running mozilla then use it's built-in WebSocket
    window.WebSocket = window.WebSocket || window.MozWebSocket;

    // if browser doesn't support WebSocket, just show some notification and exit
    if (!window.WebSocket) {
        content.html($('<p>', { text: 'Sorry, but your browser doesn\'t '
                                    + 'support WebSockets.'} ));
        return;
    }

    var uriName = 'ws://' + window.location.host + '/chatHandler';
    var connection = new WebSocket(uriName);

    connection.onopen = function () {
        console.log("WS connection established!");
        var name = $.urlParam('name');
        connection.send("Well, hello server! I'm " + (name?name:"Anonymous"));
    };

    connection.onclose = function (event) {
        if (!event.wasClean) {
            alert('Connection broken');
        } else {
            alert('Code: ' + event.code + ' cause: ' + event.reason);
        }
    };

    connection.onerror = function (error) {
        // just in there were some problems with conenction...
        content.html($('<p>', { text: 'Sorry, but there\'s some problem with your '
                                    + 'connection or the server is down.' } ));
    };

    // most important part - incoming messages
    connection.onmessage = function (message) {
        console.log("Message received: " + message.data);
    };
});