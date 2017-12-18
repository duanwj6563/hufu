package com.feo.domain.model.message;

import com.feo.domain.model.user.User;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by huang on 2017/11/8.
 */
@Entity
@Table(name = "hufu_message_receive")
public class MessageReceive {

    @Id
    @ApiModelProperty("主键id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false)
    private Long id;

    @ApiModelProperty("信息状态：0，未读 1，已读")
    private Integer status;

    @ApiModelProperty("信息收取时间")
    private Date receiveDate;

    @ApiModelProperty("收件人")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ApiModelProperty("发件信息")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "message_send_id")
    private MessageSend messageSend = new MessageSend();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MessageSend getMessageSend() {
        return messageSend;
    }

    public void setMessageSend(MessageSend messageSend) {
        this.messageSend = messageSend;
    }
}
