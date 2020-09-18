package com.yehao.boot.rules.experiment;

import lombok.Data;

import java.util.List;

/**
 * @author : LiuYong at 2020-09-17
 * @package: com.yehao.boot.rules.experiment
 */
@Data
class ExpressComponent extends ExperimentComponent{
    /**
     * 依赖属性
     */
    private List<String> fields;
    /**
     * 执行表达式语句
     */
    private String express;
}
