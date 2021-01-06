package com.yehao.boot.trace.domain;


import com.google.common.collect.ImmutableMap;
import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.metrics.Metrics;
import io.jaegertracing.internal.metrics.NoopMetricsFactory;
import io.jaegertracing.internal.reporters.CompositeReporter;
import io.jaegertracing.internal.reporters.LoggingReporter;
import io.jaegertracing.internal.reporters.RemoteReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.thrift.internal.senders.HttpSender;
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

    public void init(String service){
        final String endPoint = "http://10.30.94.8:14268/api/traces";
        final CompositeReporter compositeReporter = new CompositeReporter(
                new RemoteReporter.Builder()
                        .withSender(new HttpSender.Builder(endPoint).build())
                        .build(),
                new LoggingReporter()
        );

        final Metrics metrics = new Metrics(new NoopMetricsFactory());

        JaegerTracer.Builder builder = new JaegerTracer.Builder(service)
                .withReporter(compositeReporter)
                .withMetrics(metrics)
                .withExpandExceptionLogs()
                .withSampler(new ConstSampler(true));
        JaegerTracer jaegerTracer = builder.build();
        jaegerTracer.buildSpan("Hello");


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