package com.yehao.boot.rules.easyrules.jpo;

import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.support.UnitRuleGroup;

import javax.xml.ws.Action;

/**
 * @author : LiuYong at 2020-08-24
 * @Condition注解指定规则条件
 * @Fact注解指定参数
 * @Action注解指定规则执行的动作
 * @Rule 大多数业务规则可以用以下定义表示：
 * <p>
 *  Name : 一个命名空间下的唯一的规则名称
 *  Description : 规则的简要描述
 *  Priority : 相对于其他规则的优先级
 *  Facts : 事实，可立即为要处理的数据
 *  Conditions : 为了应用规则而必须满足的一组条件
 *  Actions : 当条件满足时执行的一组动作
 * @package: com.yehao.boot.rules.easyrules.jpo
 */
public class RuleClass {

    @Rule(priority = 1)
    public static class FizzRule {
        @Condition
        public boolean isFizz(@Fact("number") Integer number) {
            return number % 5 == 0;
        }

        @Action
        public void printFizz() {
            System.out.print("fizz");
        }
    }

    @Rule(priority = 2)
    public static class BuzzRule {
        @Condition
        public boolean isBuzz(@Fact("number") Integer number) {
            return number % 7 == 0;
        }

        @Action
        public void printBuzz() {
            System.out.print("buzz");
        }
    }

    public static class FizzBuzzRule extends UnitRuleGroup {

        public FizzBuzzRule(Object... rules) {
            for (Object rule : rules) {
                addRule(rule);
            }
        }

        @Override
        public int getPriority() {
            return 0;
        }
    }

    @Rule(priority = 3)
    public static class NonFizzBuzzRule {

        @Condition
        public boolean isNotFizzNorBuzz(@Fact("number") Integer number) {
            // can return true, because this is the latest rule to trigger according to
            // assigned priorities
            // and in which case, the number is not fizz nor buzz
            return number % 5 != 0 || number % 7 != 0;
        }

        @Action
        public void printInput(@Fact("number") Integer number) {
            System.out.print(number);
        }
    }

}