package com.yehao.boot.rules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.jeasy.rules.core.RulesEngineParameters;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : LiuYong at 2020-08-24
 * @package: com.yehao.boot.rules
 */
@RunWith(JUnit4.class)
public class EasyRulesTest {

    @Test
    public void testEasy() throws Exception {
        // create a rules engine
        RulesEngineParameters parameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);
        RulesEngine fizzBuzzEngine = new DefaultRulesEngine(parameters);

        // create rules
        Rules rules = MVELRuleFactory.createRulesFrom(new FileReader("fizzbuzz.yml"));

        // fire rules
        Facts facts = new Facts();
        for (int i = 1; i <= 100; i++) {
            facts.put("number", i);
            fizzBuzzEngine.fire(rules, facts);
            System.out.println();
        }

        Rule weatherRule = new RuleBuilder()
                .name("weather rule")
                .description("if it rains then take an umbrella")
                .when(f -> f.get("rain").equals(true))
                .then(f -> System.out.println("It rains, take an umbrella!"))
                .build();

        Rule rule1 = new MVELRule()
                .name("weather rule")
                .description("if it rains then take an umbrella")
                .when("rain == true")
                .then("System.out.println(\"It rains, take an umbrella!\");");
    }
}
