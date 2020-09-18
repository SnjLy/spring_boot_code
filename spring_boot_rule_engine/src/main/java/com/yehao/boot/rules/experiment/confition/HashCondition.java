package com.yehao.boot.rules.experiment.confition;

import lombok.Data;

import java.util.List;

/**
 * @author : LiuYong at 2020-09-17
 * @package: com.yehao.boot.rules.experiment.confition
 */
@Data
public class HashCondition extends ExperimentCondition {

    public HashCondition() {
        super(ConditionMode.HASH);
    }

    private List<String> fields;
    private String combiner;
}
