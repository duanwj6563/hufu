package com.sunlands.feo.domain.repository.message;

import com.sunlands.feo.domain.model.message.MessageSend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 消息推送接口
 * Created by huang on 2017/12/1.
 */
public interface MessageSendRepository extends JpaRepository<MessageSend, Long> {
}
