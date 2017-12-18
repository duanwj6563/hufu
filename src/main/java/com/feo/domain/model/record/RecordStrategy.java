package com.feo.domain.model.record;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by huang on 2017/11/10.
 */
@Entity
@Table(name = "hufu_record_strategy")
public class RecordStrategy {

    @Id
    @ApiModelProperty("主键id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", length = 11, nullable = false)
    private Long id;

    @ApiModelProperty("类型总得分(0/1)")
    private Integer score;

    @ApiModelProperty("0、主推项目 1、探需问题 2、主推专业 3、主推院校 4、主推班型 5、截杀策略 6、解决首咨遗留问题 7、触发式开场 8、辅助工具")
    private Integer scoreType;

    @ApiModelProperty("0、主推项目 1、探需问题 2、主推专业 3、主推院校 4、主推班型 5、截杀策略 6、解决首咨遗留问题 7、触发式开场 8、辅助工具")
    private String scoreName;

    @ApiModelProperty("1(探需、触发式开场) 2（主推专业/主推院校/主推项目、解决首咨遗留问题、辅助咨询工具） 3（主推班型） 4，（截杀）")
    private Integer scoreNum;

    @ApiModelProperty("录音策略打分")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "record_strategy_id")
    private Set<RecordStrategyScore> recordStrategyScores = new HashSet<RecordStrategyScore>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScoreType() {
        return scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Set<RecordStrategyScore> getRecordStrategyScores() {
        return recordStrategyScores;
    }

    public void setRecordStrategyScores(Set<RecordStrategyScore> recordStrategyScores) {
        this.recordStrategyScores = recordStrategyScores;
    }

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    public Integer getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(Integer scoreNum) {
        this.scoreNum = scoreNum;
    }

    public RecordStrategy addForRecordStrategy(List<Long> ids, Integer scoreType, String scoreName, Integer scoreNum) {
        RecordStrategy recordStrategy = new RecordStrategy();
        recordStrategy.setScore(0);
        recordStrategy.setScoreType(scoreType);
        recordStrategy.setScoreName(scoreName);
        recordStrategy.setScoreNum(scoreNum);
        for (Long id : ids) {
            RecordStrategyScore recordStrategyScore = new RecordStrategyScore();
            recordStrategyScore.setScoreTypeId(id);
            recordStrategyScore.setIsSelect(0);
            recordStrategy.getRecordStrategyScores().add(recordStrategyScore);
        }
        return recordStrategy;
    }
}
