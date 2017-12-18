package com.feo.domain.model.enums.strategy;

/**
 * 策略日志操作类型
 * Created by yangchao on 17/11/8.
 */
public enum StrategyLogOperationType {
    //0、创建  1、追加  2、审核 3、查阅 4、精品
    CREATED(0),
    ADDED(1),
    AUDIT(2),
    READ(3),
    BOUTIQUE(4);

    private Integer code;

    StrategyLogOperationType(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public StrategyLogOperationType setCode(Integer code) {
        this.code = code;
        return this;
    }
}
