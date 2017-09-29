package home.wayfarer.springtests.ws.object;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;

public class WebSocketClients {
    private HashMap<Integer, WebSocketSession> clients = new HashMap<>();

    public void registerUser(int hash, WebSocketSession webSocketClient) {
        this.clients.put(hash, webSocketClient);
    }

    public void forgetUser(int hash) {
        if (clients != null && clients.containsKey(hash)) {
            clients.remove(hash);
        }
    }

    public WebSocketSession get(int hash) {
        if (clients != null && clients.containsKey(hash)) {
            return clients.get(hash);
        }

        return null;
    }

    public Iterable<? extends WebSocketSession> iterator() {
        return clients.values();
    }
}
