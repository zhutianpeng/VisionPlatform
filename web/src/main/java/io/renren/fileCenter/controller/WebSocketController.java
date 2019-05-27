package io.renren.fileCenter.controller;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

@ServerEndpoint("/WebSocket/{userToken}")
@Component
public class WebSocketController {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象（userToken-webSocket键值对形式）。
    private static CopyOnWriteArraySet<WebSocketController> webSocketSet = new CopyOnWriteArraySet<WebSocketController>();
//    private static ConcurrentHashMap<String, WebSocketController> userWebSocketMap = new ConcurrentHashMap<String, WebSocketController>();


    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String userToken; //用户Id

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(@PathParam("userToken") String userToken, Session session) {
        this.webSocketInit(userToken, session); //建立连接时初始化方法
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("用户： " + this.userToken +  " 的连接关闭！当前在线人数为： " + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到来自用户 " + this.userToken + " 的信息: " + message);
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    //WebSocket初始化方法
    public synchronized void webSocketInit(String userToken, Session session){
        this.userToken = userToken;
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println("有一个连接开启，来自用户： " + this.userToken + " 当前在线人数为: " + getOnlineCount());
    }


    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static synchronized void sendInfo(String message, String userToken) throws IOException {
        System.out.println("推送消息到用户：" + userToken + " 消息内容： " + message);
        //遍历WebSocket池
        for (WebSocketController item : webSocketSet) {
            try {
                //userToken为null则全部推送
                if(userToken == null) {
                    item.sendMessage(message);
                }//指定userToken的情况下，将消息发送给特定对象
                else if(item.userToken.equals(userToken)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketController.onlineCount--;
    }
}

