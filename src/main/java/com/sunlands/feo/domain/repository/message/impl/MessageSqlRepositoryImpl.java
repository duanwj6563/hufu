package com.sunlands.feo.domain.repository.message.impl;

import com.sunlands.feo.common.EntityManagerUtil;
import com.sunlands.feo.common.StrUtil;
import com.sunlands.feo.domain.repository.message.MessageSqlRepository;
import com.sunlands.feo.port.rest.dto.message.MessageCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 消息原生sql
 * Created by huang on 2017/12/1.
 */
@Repository
public class MessageSqlRepositoryImpl implements MessageSqlRepository {

    //持久化工具
    private EntityManagerUtil entityManagerUtil;

    //通用分页查询方法
    private static int setIndeByPage(int page, int pageCount) {
        return (page - 1) * pageCount;
    }

    @Override
    public List<Map<String, Object>> selelctMessageList(MessageCondition messageCondition) {
        //获取参数
        Integer status = messageCondition.getStatus();
        Integer contentType = messageCondition.getContentType();
        //获取分页
        Integer page = messageCondition.getPage();
        Integer size = messageCondition.getSize();
        page = setIndeByPage(page, size);
        StringBuilder temp = new StringBuilder();
        if (StrUtil.isNotNull(status)) {
            temp.append(" AND hmr.`status`= ").append(status);
        }
        if (StrUtil.isNotNull(contentType)) {
            temp.append(" AND hms.content_type= ").append(contentType);
        }
        //sql
        String sql = "SELECT hmr.id,hmr.`status`,hms.`subject`,hms.content_type contentType,hms.url,hms.send_date sendDate\n" +
                "FROM hufu_message_send hms,hufu_message_receive hmr\n" +
                "WHERE hms.id=hmr.message_send_id";
        sql += temp.toString() + "\nORDER BY hmr.receive_date DESC" + "\nLIMIT " + page + "," + size;
        //执行结果
        return entityManagerUtil.getList(sql);
    }

    @Override
    public Integer selelctMessageListCount(MessageCondition messageCondition) {
        //获取参数
        Integer status = messageCondition.getStatus();
        Integer contentType = messageCondition.getContentType();
        StringBuilder temp = new StringBuilder();
        if (StrUtil.isNotNull(status)) {
            temp.append(" AND hmr.`status`= ").append(status);
        }
        if (StrUtil.isNotNull(contentType)) {
            temp.append(" AND hms.content_type= ").append(contentType);
        }
        //sql
        String sql = "SELECT count(hmr.id) count\n" +
                "FROM hufu_message_send hms,hufu_message_receive hmr\n" +
                "WHERE hms.id=hmr.message_send_id";
        sql += temp;
        //执行结果
        BigInteger count = (BigInteger) entityManagerUtil.getMap(sql).get("count");
        return count.intValue();
    }

    @Autowired
    public void setEntityManagerUtil(EntityManagerUtil entityManagerUtil) {
        this.entityManagerUtil = entityManagerUtil;
    }
}
