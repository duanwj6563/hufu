package com.sunlands.feo.domain.model.strategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 解决首咨遗留问题
 */

@Entity
@Table(name = "hufu_solve_first_problem")
public class SolveFirstProblem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;

    //解决内容
    @NotNull(message = "解决首咨遗留问题不能为空！")
    private String solve;

    // 序号
    @NotNull(message = "解决首咨遗留问题序号不能为空！")
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolve() {
        return solve;
    }

    public void setSolve(String solve) {
        this.solve = solve;
    }
}
