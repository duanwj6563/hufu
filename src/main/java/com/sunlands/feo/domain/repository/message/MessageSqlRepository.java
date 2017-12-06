package com.sunlands.feo.domain.repository.message;

import com.sunlands.feo.port.rest.dto.message.MessageCondition;

import java.util.List;
import java.util.Map;

/**
 * 消息原生sql接口
 * Created by huang on 2017/12/1.
 */
public interface MessageSqlRepository {

    /**
     * 消息中心列表条件查询
     *
     * @param messageCondition 消息查询条件
     * @return 消息集合
     */
    List<Map<String, Object>> selelctMessageList(MessageCondition messageCondition);

    /**
     * 消息中心列表条件查询总数量
     *
     * @param messageConditions 消息查询条件
     * @return 消息集合总数
     */
    Integer selelctMessageListCount(MessageCondition messageConditions);

}
