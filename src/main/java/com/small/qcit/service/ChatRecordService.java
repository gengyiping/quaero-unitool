package com.small.qcit.service;


import java.util.HashMap;
import java.util.List;

import com.small.qcit.domain.dto.ChatRecordDTO;

/**
 * 记录
 *
 */
public interface ChatRecordService {

    /**
     * 添加记录
     *
     * @param chatRecordDTO 记录对象
     */
    void addRecord(ChatRecordDTO chatRecordDTO);

    /**
     * 记录列表
     *
     * @param directoryName 目录名
     * @return 记录列表
     */
    List<HashMap<String, Object>> listRecord(String directoryName);
}
