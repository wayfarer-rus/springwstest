package home.wayfarer.springtests.ws.controller;

import home.wayfarer.springtests.ws.object.WebSocketClients;
import home.wayfarer.springtests.ws.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatHandler extends TextWebSocketHandler {

    @Autowired
    private WebSocketClients webSocketClients;

    @Autowired
    private EchoService echoService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Connection established. Session hash-code: " + session.hashCode());
        webSocketClients.registerUser(session.hashCode(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("Connection closed. Session hash-code: " + session.hashCode() + "\n" +
                "Status: " + status.toString());
        webSocketClients.forgetUser(session.hashCode());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Message from client. Session hash-code: " + session.hashCode());
        System.out.println(message.getPayload());

        echoService.echo(session.hashCode(), message.getPayload());
        echoService.broadcast(message.getPayload());
    }

}
