package com.yehao.boot.rules.experiment;

import com.yehao.boot.rules.experiment.confition.ExperimentCondition;
import lombok.Data;

/**
 * app-scene-layer唯一确定当前位置的实验
 *
 * @author : LiuYong at 2020-09-17
 * @package: com.yehao.boot.rules.route
 */
@Data
public class Experiment {

    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 实验配置名称
     */
    private String name;
    /**
     * 路由描述
     */
    private String desc;

    /**
     * 作用于那个应用 eg:search
     */
    private String app;
    /**
     * 作用于应用的那个场景 eg:app_login_job
     */
    private String scene;
    /**
     * 作用于应用的某个场景的层级 eg:route /recall /model /fusion
     * 接口、方法、语句分支
     */
    private String layer;

    /**
     * 流量占比
     */
    private float rate;
    /**
     * 标志key
     */
    private String abKey;
    /**
     * 状态  0:禁用  1:启用
     */
    private int status = 0;
    /**
     * 版本配置
     */
    private int version;

    /**
     * 实验条件
     */
    private ExperimentCondition condition;

    /**
     * 满足条件执行实验
     */
    private ExperimentComponent experiment;

    /**
     * 是否需要配置默认
     */
    //private ExperimentComponent def;
}






