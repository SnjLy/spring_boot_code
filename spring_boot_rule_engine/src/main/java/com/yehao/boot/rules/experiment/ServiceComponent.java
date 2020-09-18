package com.yehao.boot.rules.experiment;

import lombok.Data;

/**
 * @author : LiuYong at 2020-09-17
 * @package: com.yehao.boot.rules.experiment
 */
@Data
class ServiceComponent extends ExperimentComponent{
    /**
     * 实例类
     */
    private String clazz;

    private String name;
    /**
     * 依赖参数
     */
    private String[] attributes;

    private String method;

    private String[] args;
}
