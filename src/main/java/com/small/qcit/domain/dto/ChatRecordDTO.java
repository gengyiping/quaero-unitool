package com.small.qcit.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import com.small.qcit.domain.vo.MessageVO;
import com.small.qcit.enums.MessageTypeEnum;

/**
 * 聊天记录数据传输层
 *
 */
@Getter
@Setter
@ToString
public class ChatRecordDTO {

    /**
     * 用户信息
     */
    private User user;
    /**
     * 消息
     */
    private String message;
    /**
     * 图片
     */
    private String image;
    /**
     * 消息类型
     */
    private MessageTypeEnum type;
    /**
     * 发送时间
     */
    private String sendTime;

    public static ChatRecordDTO toChatRecordDTO(MessageVO messageVO) {
        if (null == messageVO) {
            return null;
        }

        ChatRecordDTO chatRecordDTO = new ChatRecordDTO();
        BeanUtils.copyProperties(messageVO, chatRecordDTO);

        return chatRecordDTO;
    }
}
