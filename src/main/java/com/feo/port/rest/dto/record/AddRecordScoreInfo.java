package com.feo.port.rest.dto.record;

import com.feo.domain.model.enums.record.ScoreStatusEnums;
import com.feo.domain.model.record.Record;
import com.feo.domain.model.record.Chance;
import com.feo.domain.model.record.Question;
import com.feo.domain.model.record.RecordStrategy;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 质检打分项
 * Created by huang on 2017/11/4.
 */
public class AddRecordScoreInfo {

    @NotNull(message = "录音类型不能为空")
    @ApiModelProperty("录音类型")
    private Integer recordType;

    @NotNull(message = "录音性质不能为空")
    @ApiModelProperty("录音性质")
    private Integer recType;

    @NotNull(message = "学员情况不能为空")
    @ApiModelProperty("学员情况 0、企业普通员工 1、政府事业单位 3、...")
    private Integer state;

    @NotNull(message = "取证目的不能为空")
    @ApiModelProperty("取证目的 0、个人提升 1、评职称 2、找工作...")
    private Integer aim;

    @ApiModelProperty("流程完成度")
    private Integer finishNum;

    @NotNull(message = "是否优秀录音不能为空")
    @ApiModelProperty("是否优秀录音 0,不优秀 1，优秀")
    private Integer isGood;

    @ApiModelProperty("优秀类型 0，历程完整 1，推班类型明确 2，推专业灵活")
    private Integer goodType;

    @ApiModelProperty("录音评价（本人）")
    private String comment;

    @NotNull(message = "策略打分集合不能为空")
    @ApiModelProperty("策略打分集合")
    private Set<RecordStrategy> recordStrategies;

    @NotNull(message = "问题集合不能为空")
    @ApiModelProperty("问题集合")
    private Set<Question> questions;

    //封装质检打分数据模型
    public Record addRecordScoreInfo() {

        Chance chance = new Chance();
        chance.setAim(aim);
        chance.setState(state);

        Record record = new Record();
        record.setRecordType(recordType);
        record.setRecType(recType);
        record.setChance(chance);
        record.setQuestions(questions);
        record.setFinishNum(finishNum);
        record.setIsGood(isGood);
        if (isGood == 1) {
            record.setGoodType(goodType);
        }
        record.setComment(comment);

        record.setRecordStrategys(recordStrategies);

        return record;
    }

    public Record changeRecordScore(Record newer, Record older) {
        Chance chance = older.getChance();

        chance.setAim(newer.getChance().getAim());
        chance.setState(newer.getChance().getState());

        older.setRecordType(newer.getRecordType());
        older.setRecType(newer.getRecType());
        older.setChance(chance);
        older.setQuestions(newer.getQuestions());
        older.setStatus(Integer.parseInt(ScoreStatusEnums.SAVE_NOSUBMIT.getCode()));
        older.setFinishNum(newer.getFinishNum());
        if (older.getIsViolation() == 0) {
            older.setIsGood(newer.getIsGood());
            older.setGoodType(newer.getGoodType());
        } else {
            older.setIsGood(0);
        }
        older.setComment(newer.getComment());
        older.setSelectStatus(Integer.parseInt(ScoreStatusEnums.SAVE_NOSUBMIT.getCode()));
        older.setRecordStrategys(newer.getRecordStrategys());

        return older;
    }

    public List<Record> deleteRecordScore(List<Record> ListOlder) {

        for (Record older : ListOlder) {
            Chance chance = older.getChance();

            chance.setAim(null);
            chance.setState(null);

            older.setRecordType(null);
            older.setSelectStatus(0);
            older.setStatus(Integer.parseInt(ScoreStatusEnums.NOSAVE_NOSUBMIT.getCode()));
            older.setSuser(null);
            older.setTestDate(null);
            older.setChance(chance);
            //清空问题集合
            Set<Question> questions = older.getQuestions();
            if (questions != null) {
                if (questions.size() == 3) {
                    for (Question question : questions) {
                        question.setDescription(null);
                        question.setQuestionTypeOneId(null);
                        question.setQuestionTypeOneName(null);
                        question.setQuestionTypeTwoId(null);
                        question.setQuestionTypeTwoName(null);
                        question.setType(null);
                    }
                    older.setQuestions(questions);
                }
            }
            older.setIsGood(null);
            older.setFinishNum(null);
            older.setGoodType(null);
            older.setComment(null);
            older.setSumScore(null);
            Set<RecordStrategy> recordStrategys = new HashSet<>();
            older.setRecordStrategys(recordStrategys);
        }

        return ListOlder;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getRecType() {
        return recType;
    }

    public void setRecType(Integer recType) {
        this.recType = recType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAim() {
        return aim;
    }

    public void setAim(Integer aim) {
        this.aim = aim;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public Integer getGoodType() {
        return goodType;
    }

    public void setGoodType(Integer goodType) {
        this.goodType = goodType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<RecordStrategy> getRecordStrategies() {
        return recordStrategies;
    }

    public void setRecordStrategies(Set<RecordStrategy> recordStrategies) {
        this.recordStrategies = recordStrategies;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Integer getIsGood() {
        return isGood;
    }

    public void setIsGood(Integer isGood) {
        this.isGood = isGood;
    }
}
