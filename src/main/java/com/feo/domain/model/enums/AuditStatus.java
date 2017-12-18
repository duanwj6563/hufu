package com.feo.domain.model.enums;

/**
 * 审核状态
 */
public enum AuditStatus{
    CREATED(0),//创建
    UN_AUDIT(1),//待审核
    PASS(2),//通过
    REJECT(3);//驳回

    private Integer code;

    AuditStatus(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public AuditStatus setCode(Integer code) {
        this.code = code;
        return this;
    }
}