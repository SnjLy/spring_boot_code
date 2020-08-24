package com.yehao.boot.rules.drools;

import com.yehao.boot.rules.drools.entity.QueryParam;

/**
 * @author : LiuYong at 2020-08-24
 * @package: com.yehao.boot.rule.drools
 */
public interface RuleEngineService {
    void executeAddRule(QueryParam param);

    void executeRemoveRule(QueryParam param);
}
