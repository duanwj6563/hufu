package com.feo.domain.repository.message;

import com.feo.domain.model.message.MessageReceive;
import com.feo.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 消息接收接口
 * Created by huang on 2017/12/1.
 */
public interface MessageReceiveRepository extends JpaRepository<MessageReceive, Long> {

    /**
     * 消息查询根据ids
     *
     * @param ids 消息ids
     * @return 消息集合
     */
    List<MessageReceive> findAllByIdIn(List<Long> ids);

    /**
     * 消息根据用户和ids查询
     *
     * @param user 用户实体
     * @param ids  消息ids
     * @return 消息集合
     */
    List<MessageReceive> findAllByUserAndIdIn(User user, Long[] ids);

    /**
     * 消息根据用户和消息状态
     *
     * @param user   用户实体
     * @param status 消息状态
     * @return 消息集合
     */
    List<MessageReceive> findAllByUserAndStatus(User user, Integer status);

    /**
     * 查询可推送的消息ids
     *
     * @param userId 用户id
     * @param status 消息状态
     * @param time   轮询时间
     * @return 可推送的消息ids
     */
    @Query(value = "SELECT hmr.id FROM hufu_message_receive hmr,hufu_message_send hms WHERE hmr.message_send_id=hms.id AND hmr.user_id=?1 AND hmr.`status`=?2 AND UNIX_TIMESTAMP(hms.send_date) -UNIX_TIMESTAMP(NOW()) >0 AND UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(hms.send_date)<?3 AND hms.exhibition=1", nativeQuery = true)
    List<Long> selectMsgPush(Integer userId, Integer status, Integer time);

    /**
     * 根据消息内容类型查询总条数
     *
     * @param contentType 消息内容类型
     * @return 消息总条数
     */
    @Query(value = "SELECT count(hmr.id) FROM hufu_message_receive hmr,hufu_message_send hms WHERE hmr.message_send_id=hms.id AND hmr.`status`=0 AND  hms.content_type = ?1", nativeQuery = true)
    Integer selectCountByContentType(Integer contentType);

}
