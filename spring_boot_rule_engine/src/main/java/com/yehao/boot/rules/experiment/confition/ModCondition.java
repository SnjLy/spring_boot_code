package com.yehao.boot.rules.experiment.confition;

import lombok.Data;

/**
 * @author : LiuYong at 2020-09-17
 * @package: com.yehao.boot.rules.experiment.confition
 */
@Data
public class ModCondition extends ExperimentCondition {

    public ModCondition() {
        super(ConditionMode.MOD);
    }

    private String field;
    private int modNum = 10;
}
