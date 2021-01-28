package com.small.qcit.annotation;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.small.qcit.domain.dto.ChatRecordDTO;
import com.small.qcit.domain.vo.MessageVO;
import com.small.qcit.enums.MessageTypeEnum;
import com.small.qcit.service.ChatRecordService;
import com.small.qcit.utils.SensitiveWordUtils;

import javax.annotation.Resource;

/**
 * 记录切面类
 *
 */
@Aspect
@Component
@Slf4j
public class ChatRecordAspect {

    @Resource
    private ChatRecordService chatRecordService;

    @Pointcut("@annotation(com.small.qcit.annotation.ChatRecord)")
    public void chatRecordPointcut() {
    }

    @Before("chatRecordPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        log.debug("before -> {}", joinPoint);

        MessageVO messageVO = null;
        Object[] args = joinPoint.getArgs();
        for (Object obj : args) {
            if (obj instanceof MessageVO) {
                messageVO = (MessageVO) obj;
                break;
            }
        }

        Assert.notNull(messageVO, "方法必需以MessageVO类或该类的子类作为参数");

        if (messageVO.getType() == MessageTypeEnum.USER) {
            // 对于User类型的消息做敏感词处理
            messageVO.setMessage(SensitiveWordUtils.loveChina(messageVO.getMessage()));
        }

        log.debug("添加记录 -> {}", messageVO);
        chatRecordService.addRecord(ChatRecordDTO.toChatRecordDTO(messageVO));
    }

}
