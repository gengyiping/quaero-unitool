package com.small.qcit.constant;

/**
 * 消息模板
 */
public interface MessageConstant {
    /**
     * 登入界面登入
     */
    String ONLINE_MESSAGE = "%s登入界面登入进入";
    /**
     * 登入界面登出
     */
    String OFFLINE_MESSAGE = "%s离开了界面";
    /**
     * 机器人欢迎消息
     */
    String ROBOT_WELCOME_MESSAGE = "@%s 欢迎到来！消息内容以'#'开头的我就能收到哦，" +
            "随时来撩我呀！";
}
