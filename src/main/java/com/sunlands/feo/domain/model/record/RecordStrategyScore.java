package com.sunlands.feo.domain.model.record;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by huang on 2017/11/4.
 */
@Entity
@Table(name = "hufu_record_strategy_score")
public class RecordStrategyScore {

    @Id
    @ApiModelProperty("主键id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", length = 11, nullable = false)
    private Long id;

    @ApiModelProperty("对应打分具体问题id")
    private Long scoreTypeId;

    @ApiModelProperty("是否选中 0,未选中 1，选中")
    private Integer isSelect;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }

    public Long getScoreTypeId() {
        return scoreTypeId;
    }

    public void setScoreTypeId(Long scoreTypeId) {
        this.scoreTypeId = scoreTypeId;
    }

    public List<Map<String, Object>> selectRecordStrategy() {

        return null;
    }
}
