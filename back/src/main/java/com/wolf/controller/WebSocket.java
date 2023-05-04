package com.wolf.controller;



import com.alibaba.fastjson.*;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wolf.entity.Friend;
import com.wolf.entity.Message;
import com.wolf.service.IFriendService;
import com.wolf.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
@Slf4j
@ServerEndpoint("/websocket/{uid}")
//此注解相当于设置访问URL
public class WebSocket {
    //private static Map<Integer,Session> pool;
    private Session session;
    private Integer uid;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    private static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<Integer,Session> sessionPool = new ConcurrentHashMap<Integer,Session>();


    private static IMessageService iMessageService;
    @Autowired
    public void setIMessageService(IMessageService iMessageService){
        this.iMessageService=iMessageService;
    }

    private static IFriendService iFriendService;
    @Autowired
    public void setIFriendService(IFriendService iFriendService){
        this.iFriendService=iFriendService;
    }

    @OnOpen
    public void onOpen(Session session,@PathParam(value="uid")Integer uid) {
        try {
            this.session = session;
            this.uid = uid;
            webSockets.add(this);
            sessionPool.put(uid, session);
            log.info("【websocket消息】有新的连接，总数为:"+webSockets.size());
        } catch (Exception e) {
        }
    }

    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.uid);
            log.info("【websocket消息】连接断开，总数为:"+webSockets.size());
        } catch (Exception e) {
        }
    }
    @OnError
    public void onError(Session session, Throwable t) {
        //什么都不想打印都去掉就好了
        log.info("【websocket消息】出现未知错误 ");
        //打印错误信息，如果你不想打印错误信息，去掉就好了
        //这里打印的也是  java.io.EOFException: null
        t.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
        JSONObject jsonObject = JSON.parseObject(message);
//        String sessionId = this.session.getRequestParameterMap().get("sessionId").get(0);
//        if (sessionId == null){
//            System.out.println("sessionId 错误");
//        }
        Message newMessage = new Message();
        newMessage.setContent(jsonObject.getString("content"));
        newMessage.setCreatedDate(LocalDateTime.now());
        newMessage.setReceiveId(jsonObject.getInteger("receiveId"));
        newMessage.setSendId(jsonObject.getInteger("sendId"));

        System.out.println(newMessage);
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
//        Map<String,Object> params=new HashMap<String,Object>();
//        params.put("friend_id",newMessage.getReceiveId());
//        params.put("uid",newMessage.getSendId());
//        wrapper.allEq(params);
        wrapper.eq("friend_id",newMessage.getReceiveId());
        wrapper.eq("uid",newMessage.getSendId());
        Friend friend = iFriendService.getOne(wrapper);
        newMessage.setDialogue(friend.getDialogue());
        // 消息持久化

        log.info("【websocket消息】收到客户端消息:"+message);
        System.out.println(newMessage);
        //发送消息
        sendOneMessage(newMessage);
    }

    // 此为广播消息
//    public void sendAllMessage(String message) {
//        log.info("【websocket消息】广播消息:"+message);
//        for(WebSocket webSocket : webSockets) {
//            try {
//                if(webSocket.session.isOpen()) {
//                    webSocket.session.getAsyncRemote().sendText(JSON.toJSONString(message));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    // 此为单点消息
    public void sendOneMessage(Message message) {
        Session session = sessionPool.get(message.getReceiveId());
        if (session != null&&session.isOpen()) {
            message.setIsRead(1);
            iMessageService.save(message);
            try {
                log.info("【websocket消息】 单点消息:"+message);
                session.getAsyncRemote().sendText(JSON.toJSONString(message));
                System.out.println("消息发送成功");
                System.out.println(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            message.setIsRead(0);
            iMessageService.save(message);
        }
    }

    // 此为单点消息(多人)
//    public void sendMoreMessage(Integer[] uids, String message) {
//        for(Integer uid:uids) {
//            Session session = sessionPool.get(uid);
//            if (session != null&&session.isOpen()) {
//                try {
//                    log.info("【websocket消息】 单点消息:"+message);
//                    String jsonString = JSON.toJSONString(message);
//                    System.out.println(jsonString);
//                    session.getAsyncRemote().sendText(jsonString);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
}


