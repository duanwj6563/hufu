package com.sunlands.feo.domain.model.boutique;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by 屈登科 on 2017/12/3.
 */
public class BoutiqueStrategyPK implements Serializable {
    private Long strategyId;
    private Integer uid;

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
}
