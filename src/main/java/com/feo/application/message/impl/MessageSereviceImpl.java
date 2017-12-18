package com.feo.application.message.impl;

import com.feo.application.message.MessageSerevice;
import com.feo.domain.exception.UserDefinedException;
import com.feo.domain.model.message.MessageReceive;
import com.feo.domain.model.message.MessageTemplate;
import com.feo.domain.model.user.User;
import com.feo.domain.repository.message.MessageSendRepository;
import com.feo.port.rest.dto.message.MessageCondition;
import com.feo.application.UserService;
import com.feo.common.StrUtil;
import com.feo.domain.exception.ServerStatus;
import com.feo.domain.model.enums.message.ContentTypeEnums;
import com.feo.domain.model.enums.message.MessageStatusEnums;
import com.feo.domain.model.message.MessageSend;
import com.feo.domain.repository.message.MessageReceiveRepository;
import com.feo.domain.repository.message.MessageSqlRepository;
import com.feo.domain.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * 消息实现层
 * Created by huang on 2017/11/8.
 */
@Service
public class MessageSereviceImpl implements MessageSerevice {

    //消息原生sqlRepository
    private MessageSqlRepository messageSqlRepository;

    //消息推送Repository
    private MessageSendRepository messageSendRepository;

    //消息接收Repository
    private MessageReceiveRepository messageReceiveRepository;

    //用户Repository
    private UserRepository userRepository;

    //用户Service
    private UserService userService;

    @Override
    public Map<String, Object> selectMsgBell() {
        Map<String, Object> resultMap = new HashMap<>();
        //查询所有未读信息
        List<MessageReceive> allByUserAndStatus = messageReceiveRepository.findAllByUserAndStatus(getUser(), MessageStatusEnums.NO_READ.getCode());
        Integer groupId = 0;
        Integer sopId = 0;
        Integer systemId = 0;
        String temp = "个未读";
        //判断不同类型未读消息数量
        for (MessageReceive messageReceive : allByUserAndStatus) {
            MessageSend messageSend = messageReceive.getMessageSend();
            if (messageSend.getContentType().equals(ContentTypeEnums.GROUP_DYNAMIC.getCode())) {
                groupId += 1;
            }
            if (messageSend.getContentType().equals(ContentTypeEnums.SOP_ADVICE.getCode())) {
                sopId += 1;
            }
            if (messageSend.getContentType().equals(ContentTypeEnums.SYSTEM_MESSAGE.getCode())) {
                systemId += 1;
            }
        }
        //根据所得数据拼接返回参数
        Integer totleNum = groupId + sopId + systemId;
        List<Map<String, Object>> contents = new ArrayList<>();
        if (totleNum > 0) {
            if (groupId > 0) {
                contents.add(selectMsgBellUtil(groupId, temp, ContentTypeEnums.GROUP_DYNAMIC.getCode(), ContentTypeEnums.GROUP_DYNAMIC.getName()));
            }
            if (sopId > 0) {
                contents.add(selectMsgBellUtil(sopId, temp, ContentTypeEnums.SOP_ADVICE.getCode(), ContentTypeEnums.SOP_ADVICE.getName()));
            }
            if (systemId > 0) {
                contents.add(selectMsgBellUtil(systemId, temp, ContentTypeEnums.SYSTEM_MESSAGE.getCode(), ContentTypeEnums.SYSTEM_MESSAGE.getName()));
            }
            resultMap.put("contents", contents);
            resultMap.put("totleNum", totleNum);
        }
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> pushPullBell() {
        List<Map<String, Object>> list = new ArrayList<>();
        //显示推送时间差
        Integer pushTime = MessageTemplate.PUSH_TIME;
        //查询所有未读信息
        List<Long> ids = messageReceiveRepository.selectMsgPush(getUser().getId(), MessageStatusEnums.NO_READ.getCode(), pushTime);
        List<MessageReceive> msgrs = messageReceiveRepository.findAllByIdIn(ids);
        //封装所需要的信息
        for (MessageReceive messageReceive : msgrs) {
            MessageSend messageSend = messageReceive.getMessageSend();
            Map<String, Object> map = new HashMap<>();
            map.put("subject", messageSend.getSubject());
            map.put("url", messageSend.getUrl());
            map.put("content", messageSend.getContent());
            list.add(map);
        }
        return list;
    }

    @Override
    public Map<String, Object> selectMsgList(MessageCondition messageConditions) {
        Map<String, Object> countMap = new HashMap<>();
        //查询基本信息
        List<Map<String, Object>> list = messageSqlRepository.selelctMessageList(messageConditions);
        Integer totalRecord = messageSqlRepository.selelctMessageListCount(messageConditions);
        //查询未读信息数量
        Integer system = 0;
        Integer sop = 0;
        Integer group = 0;
        if (messageConditions.getContentType() != null) {
            group = messageReceiveRepository.selectCountByContentType(ContentTypeEnums.GROUP_DYNAMIC.getCode());
            sop = messageReceiveRepository.selectCountByContentType(ContentTypeEnums.SOP_ADVICE.getCode());
            system = messageReceiveRepository.selectCountByContentType(ContentTypeEnums.SYSTEM_MESSAGE.getCode());
        }
        countMap.put("system", system);
        countMap.put("sop", sop);
        countMap.put("group", group);
        countMap.put("totle", system + sop + group);
        //返回分页所需要数据
        return totalPages(list, countMap, totalRecord, messageConditions.getPage(), messageConditions.getSize());
    }

    @Transactional
    @Override
    public boolean updateMsgStatus(Long[] ids) {
        //查询需要修改的消息列表
        List<MessageReceive> msgs = messageReceiveRepository.findAllByUserAndIdIn(getUser(), ids);
        for (MessageReceive messageReceive : msgs) {
            messageReceive.setStatus(1);
        }
        //修改消息已读状态
        List<MessageReceive> save = messageReceiveRepository.save(msgs);
        if (save.size() > 0) {
            return true;
        }
        //数据修改失败，服务器繁忙
        throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
    }

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    @Override
    public void pushMessage(String subject, String url, Integer contentType, Integer sendType, Integer exhibition, User user, Integer[] userIds) {
        //通用推送方法，封装参数数据
        MessageSend messageSend = new MessageSend();
        messageSend.setContentType(contentType);
        messageSend.setSendDate(new Date());
        messageSend.setSubject(subject);
        messageSend.setUrl(url);
        messageSend.setSendType(sendType);
        messageSend.setExhibition(exhibition);
        messageSend.setUser(user);
        //查询推送人
        List<User> users = userRepository.findAllByIdIn(userIds);
        for (User u : users) {
            MessageReceive msgr = new MessageReceive();
            msgr.setStatus(MessageStatusEnums.NO_READ.getCode());
            msgr.setUser(u);
            messageSend.getMessageReceives().add(msgr);
            msgr.setMessageSend(messageSend);
        }
        //保存推送消息
        MessageSend save = messageSendRepository.saveAndFlush(messageSend);
        if (StrUtil.isNull(save)) {
            //数据修改失败，服务器繁忙
            throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION, "消息推送失败");
        }
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户实体类
     */
    public User getUser() {
        return userService.getUser();
    }

    /**
     * 封装分页数据
     *
     * @param list     数据1
     * @param countMap 数据2
     * @param page     当前页
     * @param size     每页多少数据
     * @return 分页数据
     */
    private Map<String, Object> totalPages(List<Map<String, Object>> list, Map<String, Object> countMap, Integer totalRecord, Integer page, Integer size) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> totleMap = new HashMap<>();
        //封装所需属性
        map.put("content", list);
        map.put("contentNum", countMap);
        //封装其他数值
        totleMap.put("content", map);
        Integer totalPage = totalRecord % size == 0 ? totalRecord / size : totalRecord / size + 1;
        totleMap.put("totalElements", totalRecord);
        totleMap.put("size", size);
        totleMap.put("number", page);
        totleMap.put("totalPages", totalPage);
        return totleMap;
    }

    /**
     * 拼接首页push数据参数
     *
     * @param num  未读条数
     * @param temp 未读模板
     * @param code 未读类型
     * @param name 未读类型名字
     * @return 首页push数据参数详情
     */
    private Map<String, Object> selectMsgBellUtil(Integer num, String temp, Integer code, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", code);
        map.put("name", num + temp + name);
        return map;
    }

    @Autowired
    public void setMessageSqlRepository(MessageSqlRepository messageSqlRepository) {
        this.messageSqlRepository = messageSqlRepository;
    }

    @Autowired
    public void setMessageSendRepository(MessageSendRepository messageSendRepository) {
        this.messageSendRepository = messageSendRepository;
    }

    @Autowired
    public void setMessageReceiveRepository(MessageReceiveRepository messageReceiveRepository) {
        this.messageReceiveRepository = messageReceiveRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
