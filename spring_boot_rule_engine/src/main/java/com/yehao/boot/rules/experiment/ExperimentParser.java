package com.yehao.boot.rules.experiment;

/**
 * @author : LiuYong at 2020-09-17
 * @package: com.yehao.boot.rules.experiment
 */
public abstract class ExperimentParser<Req> {



    /**
     * 实验组件解析方案
     * @param <T>  实验方案
     * @return  解析结果
     */
    abstract <T> T checkMatch(Experiment experiment, Req req);

    /**
     * 具体组件的执行
     * @param c
     * @param param
     * @param <C>
     * @return
     */
    abstract <C> Object execute(C c, Req req, Experiment experiment, Object... param);
}
