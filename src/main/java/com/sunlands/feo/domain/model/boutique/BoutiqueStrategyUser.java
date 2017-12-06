package com.sunlands.feo.domain.model.boutique;

import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 屈登科 on 2017/12/3.
 */
@IdClass(BoutiqueStrategyPK.class)
@Table(name = "hufu_boutique_strategy_user")
@Entity
public class BoutiqueStrategyUser{
    @Id
    @Column(name = "strategy_id")
    private Long strategyId;

    @Id
    private Integer uid;

    @Column(name = "join_date")
    private Date joinDate;

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
}
