package com.yehao.boot.rules.experiment.confition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author : LiuYong at 2020-09-17
 * @package: com.yehao.boot.rules.experiment
 */
@Data
public abstract class ExperimentCondition {

    @Getter
    @AllArgsConstructor
    public enum ConditionMode {
        /**
         * 实验流量命中方式
         */
        CONTAINS(1, "contains"),
        MOD(2, "mod"),
        HASH(3, "hash"),
        EXPRESS(4, "express");

        private int value;
        private String desc;
    }

    ExperimentCondition(ConditionMode mode) {
        this.mode = mode;
    }

    /**
     * 条件计算方式  0:contains  1:mod  2:hash  3:express
     * {@link ConditionMode}
     */
    protected ConditionMode mode;
    /**
     * 实验条件
     */
    protected String condition;


}
