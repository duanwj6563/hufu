package com.sunlands.feo.application.message;

import com.sunlands.feo.domain.model.user.User;
import com.sunlands.feo.port.rest.dto.message.MessageCondition;

import java.util.List;
import java.util.Map;

/**
 * 消息Serevice
 * Created by huang on 2017/11/8.
 */
public interface MessageSerevice {

    /**
     * 未读消息首页铃铛
     *
     * @return 未读消息首页铃铛详情
     */
    Map<String, Object> selectMsgBell();

    /**
     * 未读消息首页推送
     *
     * @return 未读消息首页推送详情
     */
    List<Map<String, Object>> pushPullBell();

    /**
     * 消息中心列表
     *
     * @param messageCondition 消息查询条件
     * @return 消息中心列表
     */
    Map<String, Object> selectMsgList(MessageCondition messageCondition);

    /**
     * 消息批量标记已读
     *
     * @param ids 消息ids
     * @return 是否标记成功
     */
    boolean updateMsgStatus(Long[] ids);

    /**
     * 消息推送
     *
     * @param subject     消息主题
     * @param url         消息url
     * @param contentType 消息内容类型
     * @param sendType    消息发送类型
     * @param exhibition  消息是否展示
     * @param user        推送创建者
     * @param userIds     接收用户userIds
     */
    void pushMessage(String subject, String url, Integer contentType, Integer sendType, Integer exhibition, User user, Integer[] userIds);
}
