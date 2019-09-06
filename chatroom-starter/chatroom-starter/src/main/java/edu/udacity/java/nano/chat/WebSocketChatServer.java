package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
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
@ServerEndpoint(value = "/chat/{username}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class)
public class WebSocketChatServer {

    public String theType;

    /**
     * All chat sessions.
     */
    private Session session;
    // This hashmap store all the new session for web socket client
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
//    private static HashMap<String, String> users = new HashMap<>();

    private static void sendMessageToAll(String msg, Session session) throws IOException, EncodeException{
        if(session.isOpen()){
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


    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {
        this.session = session;
//        session.setMaxIdleTimeout(5 * 60 * 1000);
//        session.getUserProperties().putIfAbsent("username", username);
       // onlineSessions.put(username, this.session);
//        session.getBasicRemote().sendText("onOpen -- Welcome!");
        onlineSessions.put(session.getId(), session);
        Message message = new Message("SPEAK", username, "Connected", onlineSessions.size());
        broadcast(message);

    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) throws EncodeException, IOException {
//        Message message1 = new Message(message.setType("SPEAK"), message.getUsernmame(),message.getMessage(), onlineSessions.size());
        Message message = new Message();
        message.getUsernmame();
        message.setType("SPEAK");
        message.setMessage(jsonStr);
        message.setOnlineCount(onlineSessions.size());
        broadcast(message);
//        broadcast(Message.jsonConverter(message));
//        sendMessage(Message.jsonConverter(message));
//        JSON.toJSONString(new Message(, username, message, onlineCount));

        //(String type, String username, String message, int onlineCount)
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
//            message.setUsernmame(users.get(session.getId()));
            message.setOnlineCount(onlineSessions.size()-1);

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

    private void broadcast(Message message) throws IOException, EncodeException {
        onlineSessions.forEach((endpoint, sess) -> {
            synchronized (endpoint) {
                try {

                    session.getBasicRemote().
                            sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
