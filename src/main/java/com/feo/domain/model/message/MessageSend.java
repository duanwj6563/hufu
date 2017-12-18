package com.feo.domain.model.message;

import com.feo.domain.model.user.User;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by huang on 2017/11/7.
 */
@Entity
@Table(name = "hufu_message_send")
public class MessageSend implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false)
    private Long id;

    @ApiModelProperty("消息主题")
    private String subject;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("消息跳转地址")
    private String url;

    @ApiModelProperty("消息类型 0，军团动态 1，SOP反馈 2，系统通知")
    private Integer contentType;

    @ApiModelProperty("消息发送类型 0,虎符  1,天网")
    private Integer sendType;

    @ApiModelProperty("信息推送时间")
    private Date sendDate;

    @ApiModelProperty("是否展示")
    private Integer exhibition;

    @ApiModelProperty("发件人")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ApiModelProperty("收件信息集合")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "message_send_id")
    private Set<MessageReceive> messageReceives = new HashSet<MessageReceive>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<MessageReceive> getMessageReceives() {
        return messageReceives;
    }

    public void setMessageReceives(Set<MessageReceive> messageReceives) {
        this.messageReceives = messageReceives;
    }

    public Integer getExhibition() {
        return exhibition;
    }

    public void setExhibition(Integer exhibition) {
        this.exhibition = exhibition;
    }
}
