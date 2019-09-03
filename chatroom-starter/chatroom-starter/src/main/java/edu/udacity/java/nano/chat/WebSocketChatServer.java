package edu.udacity.java.nano.chat;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat/{username}")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private Session session;
    // This hashmap store all the new session for web socket client
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
    private static HashMap<String, String> users = new HashMap<>();

    private static void sendMessageToAll(String msg, Session session) throws IOException, EncodeException{

        onlineSessions.forEach((k,v) ->{

                    try {
                        session.getBasicRemote().sendObject(msg);

                    }
                    catch (IOException | EncodeException e){
                        e.printStackTrace();
                    }
        }
        );

    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        session.setMaxIdleTimeout(5 * 60 * 1000);
        session.getUserProperties().putIfAbsent("username", username);
        onlineSessions.put(username, session);
        session.getBasicRemote().sendText("onOpen -- Welcome!");

    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {

        //create a new Message object from jsonstr
        Message message = new Message(jsonStr);
        // sendMessageToAll
        try {
            session.getBasicRemote().sendObject(message);
            session.getBasicRemote().sendText(String.valueOf(message));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }

    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose::" +  session.getId());
        try {
            onlineSessions.remove(this);

            Message message = new Message();
            message.setUsernmame(users.get(session.getId()));
            message.setOnlineCount(-1);

            session.close();
        }
        catch (IOException e){
            e.printStackTrace();

        }

    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }



}
