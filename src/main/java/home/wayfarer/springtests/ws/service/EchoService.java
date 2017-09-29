package home.wayfarer.springtests.ws.service;

import home.wayfarer.springtests.ws.object.WebSocketClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
public class EchoService {

    @Autowired
    private WebSocketClients webSocketClients;

    public void echo(int hash, String message) {
        // get session
        WebSocketSession session = webSocketClients.get(hash);

        try {
            session.sendMessage(new TextMessage("echo: " + message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        for (WebSocketSession session : webSocketClients.iterator()) {
            try {
                session.sendMessage(new TextMessage("broadcast: " + message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
