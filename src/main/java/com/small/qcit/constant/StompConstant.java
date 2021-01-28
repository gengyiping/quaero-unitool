package com.small.qcit.constant;

/**
 * stomp相关常量
 *
 * @author yanpanyi
 * @date 2019/3/22
 */
public interface StompConstant {
    /**
     * STOMP的节点
     */
    String STOMP_ENDPOINT = "/webSocket";
    /**
     * 广播式
     */
    String STOMP_TOPIC = "/topic";
    /**
     * 一对一式
     */
    String STOMP_USER = "/user";
    /**
     * 单用户消息订阅地址
     */
    String SUB_USER = "/chat";
    /**
     * 单用户消息发布地址
     */
    String PUB_USER = "/chat";
    /**
     * 登入后消息发布地址
     */
    String PUB_CHAT_ROOM = "/chatRoom";

    /**
     * 登入后消息订阅地址
     */
    String SUB_CHAT_ROOM = "/topic/chatRoom";
    /**
     * 异常消息订阅地址
     */
    String SUB_ERROR = "/error";
    /**
     * 用户上下线状态消息订阅地址
     */
    String SUB_STATUS = "/topic/status";
    /**
     * 登入后消息撤消
     */
    String PUB_CHAT_ROOM_REVOKE = "/chatRoom/revoke";
}
