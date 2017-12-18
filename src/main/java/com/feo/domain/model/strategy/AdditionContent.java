package com.feo.domain.model.strategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hufu_addcontent")
public class AdditionContent {
    //   AdditionContent
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;
    //    追加人id
    private Integer uid;
    //    提交时间
    private Date date;

    private String username;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "addcontent_id")
    private List<ContentDetail> contentDetails = new ArrayList<>();

    //策略id
    @Column(name = "strategy_id")
    private Long strategyId;

    //    策略审核状态 0-新建,1-待审核,2-审核通过,3-驳回,
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public List<ContentDetail> getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(List<ContentDetail> contentDetails) {
        this.contentDetails = contentDetails;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
