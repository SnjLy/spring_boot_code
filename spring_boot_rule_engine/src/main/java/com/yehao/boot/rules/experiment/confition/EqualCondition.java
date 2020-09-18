package com.yehao.boot.rules.experiment.confition;

import lombok.Data;

/**
 * @author : LiuYong at 2020-09-17
 * @package: com.yehao.boot.rules.experiment.confition
 */
@Data
public class EqualCondition extends ExperimentCondition {

    public EqualCondition() {
        super(ConditionMode.CONTAINS);
    }

    private String field;

}
