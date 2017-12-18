package com.feo.domain.model.strategy;

import com.feo.domain.model.user.User;
import com.feo.domain.event.EntityObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "hufu_strategy_log")
public class StrategyLog extends EntityObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;
    //    操作时间
    private Date date;
    //    操作内容
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @Column(name = "strategy_id")
    private Long strategyId;

    //    日志类型 0、创建/修改  1、追加  2、审核 3、审阅
    private Integer operationType;

    private String logContent;

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public StrategyLog setOperationType(Integer operationType) {
        this.operationType = operationType;
        return this;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }
}
