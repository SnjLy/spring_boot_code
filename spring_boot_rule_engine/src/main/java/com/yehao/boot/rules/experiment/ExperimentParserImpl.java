package com.yehao.boot.rules.experiment;


import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.yehao.boot.rules.drools.entity.QueryParam;
import com.yehao.boot.rules.experiment.confition.*;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author : LiuYong at 2020-09-17
 * @package: com.yehao.boot.rules.experiment
 */
public class ExperimentParserImpl extends ExperimentParser<QueryParam> {


    private static ExpressRunner runner = new ExpressRunner();

    /**
     * 实验组件解析方案
     *
     * @param experiment@return 解析结果
     */
    @Override
    public Boolean checkMatch(Experiment experiment, QueryParam queryParam) {
        Assert.isTrue(null != experiment, "实验必须配置正确");
        Assert.isTrue(experiment.getStatus() == 1, "实验状态必须启用");
        ExperimentCondition condition = experiment.getCondition();
        ExperimentCondition.ConditionMode mode = condition.getMode();
        String condExp = condition.getCondition();
        switch (mode) {
            case CONTAINS: {
                EqualCondition ec = (EqualCondition) condition;
                String key = ec.getField();
                Object value = this.getV(key, queryParam);
                if (null != value && condExp.contains(String.valueOf(value))){
                    return true;
                }
            }
            break;
            case MOD: {
                ModCondition mod = (ModCondition) condition;
                String key = mod.getField();
                Object value = this.getV(key, queryParam);
                if (null != value){
                    long v = Long.parseLong(String.valueOf(value));
                    long l = v % mod.getModNum();
                    if (mod.getCondition().equals(l+"")) {
                        return true;
                    }
                }
            }
            break;
            case HASH:
                HashCondition hash = (HashCondition) condition;
                List<String> list = hash.getFields();
                //todo 获取属性值 然后组装
                Object o = new Object();

                if (String.valueOf(o.hashCode()).equals(hash.getCondition())){
                    return true;
                }
                break;
            case EXPRESS:
                ExpressCondition ec = (ExpressCondition) condition;
                List<String> fields = ec.getFields();
                DefaultContext<String, Object> context = new DefaultContext<>();
                for (String field : fields) {
                    context.put(field, getV(field, queryParam));
                }
                try {
                    Boolean score = (Boolean) runner.execute(ec.getCondition(), context, null, true, false);
                    if (score){
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        return false;
    }

    private Object getV(String key, QueryParam queryParam) {
        Field[] declaredFields = queryParam.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (!field.getName().equals(key)){
                continue;
            }
            //todo field权限校验
            try {
                field.setAccessible(true);
                Object o = field.get(queryParam);
                return o;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 具体组件的执行
     *
     * @param c
     * @param experiment
     * @param param
     * @return
     */
    @Override
    public <Map> Object execute(Map c, QueryParam queryParam, Experiment experiment, Object... param) {
        ExperimentComponent component = experiment.getExperiment();
        if (component instanceof ExpressComponent) {
            ExpressComponent exc = (ExpressComponent) component;
            List<String> fields = exc.getFields();
            DefaultContext<String, Object> context = new DefaultContext<>();
            for (String field : fields) {
                context.put(field, getV(field, queryParam));
            }
            try {
                Object score = runner.execute(exc.getExpress(), context, null, true, false);
                return score;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (component instanceof ServiceComponent) {
            //反射执行类的方法,构造参数
        }
        if (component instanceof FlowComponent) {
            return ((FlowComponent) component).getName();
        }
        return null;
    }


}
