package com.feo.domain.model.record;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "hufu_unmatch_record")
public class UnMatchRecord extends Record implements Serializable{
//    咨询师名称
    private String counselorName;
//    军团名称
    private String groupName;
//    组名加部门名
    private String branchName;

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
