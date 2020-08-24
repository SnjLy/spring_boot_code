package com.yehao.boot.rules;

import com.yehao.boot.rules.drools.RuleEngineService;
import com.yehao.boot.rules.drools.entity.QueryParam;
import com.yehao.boot.rules.drools.entity.RuleResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author : LiuYong at 2020-08-24
 * @package: com.yehao.boot.rules
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RuleEngineApp.class)
public class DroolsTest {

    @Resource
    private KieSession kieSession;
    @Resource
    private RuleEngineService ruleEngineService;

    @Test
    public void testParam() {
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParamId("1");
        queryParam1.setParamSign("+");
        QueryParam queryParam2 = new QueryParam();
        queryParam2.setParamId("2");
        queryParam2.setParamSign("-");
        // 入参
        kieSession.insert(queryParam1);
        kieSession.insert(queryParam2);
        kieSession.insert(this.ruleEngineService);
        // 返参
        RuleResult resultParam = new RuleResult();
        kieSession.insert(resultParam);
        kieSession.fireAllRules();
    }

}
