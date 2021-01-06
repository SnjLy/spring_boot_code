package com.yehao.boot.trace.domain;


import com.google.common.collect.ImmutableMap;
import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

import java.util.Arrays;

/**
 * @author : LiuYong at 1/6/21
 * @package: com.yehao.boot.trace.domain
 */
public class Hello {

    private final Tracer tracer;

    public static JaegerTracer initTracer(String service) {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1);
        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv().withLogSpans(true);
        Configuration config = new Configuration(service).withSampler(samplerConfig).withReporter(reporterConfig);
        return config.getTracer();
    }

    private Hello(Tracer tracer) {
        this.tracer = initTracer("hello-world");
    }

    private void sayHello(String helloTo) {
        Span span = tracer.buildSpan("say-hello").start();

        String helloStr = String.format("Hello, %s!", helloTo);
        System.out.println(helloStr);

        span.finish();
    }


    private void sayHello(String helloTo, String... words) {
        Span span = tracer.buildSpan("say-hello").start();
        // 增加Tags信息
        span.setTag("hello-to", helloTo);

        String helloStr = String.format("Hello, %s!, %s", helloTo, Arrays.toString(words));
        // 增加Logs信息
        span.log(ImmutableMap.of("event", "string-format", "value", helloStr));

        System.out.println(helloStr);
        // 增加Logs信息
        span.log(ImmutableMap.of("event", "println"));

        span.finish();
    }

    public static void main(String[] args) {
        String helloTo = "world";
        new Hello(GlobalTracer.get()).sayHello(helloTo);
    }
}