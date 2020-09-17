package com.yehao.boot.rules;

import com.yehao.boot.rules.function.model.FilterModel;
import com.yehao.boot.rules.function.model.Recall;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.assertj.core.util.Lists;
import org.codehaus.groovy.tools.shell.Shell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.internal.util.collections.Sets;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

/**
 * @author : LiuYong at 2020-08-25
 * @package: com.yehao.boot.rules
 */
@RunWith(JUnit4.class)
public class GroovyFilterTest {


    @Test
    public void testGroovy() throws Exception {
        //evalScript();
        testLoader();

    }

    public void testLoader() throws IOException, ResourceException, ScriptException {
        GroovyScriptEngine engine = new GroovyScriptEngine("script");
        Binding binding = new Binding();
        binding.setVariable("language","Groovy");
        Recall recall = new Recall().setId(110).setName("测试的例子").setUserId(11L);
        binding.setVariable("recall", recall);
        FilterModel filterModel = new FilterModel().setLogin(false).setWhiteBrands(Sets.newSet(11, 12, 110, 119)).setBlackCompanies(Lists.newArrayList(110L, 11L, 12L));
        binding.setVariable("filterModel", filterModel);
        engine.run("GFilterPredict.groovy",binding);
    }


    @Test
    public void evalScript() throws Exception{
        ScriptEngineManager factory = new ScriptEngineManager();
        //每次生成一个engine实例
        ScriptEngine engine = factory.getEngineByName("groovy");
        System.out.println(engine.toString());
        assert engine != null;
        //javax.script.Bindings
        Bindings binding = engine.createBindings();
        binding.put("date", new Date());
        //如果script文本来自文件,请首先获取文件内容
        engine.eval("def getTime(){return date.getTime();}",binding);
        engine.eval("def sayHello(name,age){return 'Hello,I am ' + name + ',age' + age;}");

        FileReader fileReader = new FileReader(new File("script/GFilterPredict.groovy"));
        BufferedReader reader = new BufferedReader(fileReader);
        String line = "";
        StringBuilder sb = new StringBuilder();
        while (null != (line = reader.readLine())){
            sb.append(line);
        }
        engine.eval(sb.toString());

        Recall recall = new Recall().setId(110).setName("测试的例子").setUserId(11L);
        binding.put("recall", recall);
        FilterModel filterModel = new FilterModel().setLogin(false).setWhiteBrands(Sets.newSet(11, 12, 110, 119)).setBlackCompanies(Lists.newArrayList(110L, 11L, 12L));
        binding.put("filterModel", filterModel);

        boolean message = (boolean) ((Invocable) engine).invokeFunction("apply", "recall", "filterModel");
        System.out.println(message);
    }
}
