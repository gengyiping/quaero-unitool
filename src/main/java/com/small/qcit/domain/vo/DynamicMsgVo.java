package com.small.qcit.domain.vo;

import lombok.*;

import java.util.List;

import com.small.qcit.domain.dto.User;
import com.small.qcit.enums.MessageTypeEnum;

/**
 * 聊天室动态消息
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DynamicMsgVo extends MessageVO {

    /**
     * 在线人数
     */
    private int onlineCount;

    /**
     * 在线用户列表
     */
    private List<User> onlineUserList;

    @Override
    public MessageTypeEnum getType() {
        return MessageTypeEnum.SYSTEM;
    }
}
