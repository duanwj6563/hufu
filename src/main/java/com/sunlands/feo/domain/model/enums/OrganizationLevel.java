package com.sunlands.feo.domain.model.enums;

/**
 * 机构级别
 * Created by yangchao on 17/11/9.
 */
public enum OrganizationLevel {
    BUSINESS_UNIT("事业部"),//事业部
    ARMY("军团"),//军团
    SALES_DEPARTMENT("销售部"),//销售部//sales department
    SALES_GROUP("销售组");//销售组

    private String name;
    OrganizationLevel(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public OrganizationLevel setName(String name) {
        this.name = name;
        return this;
    }
}
