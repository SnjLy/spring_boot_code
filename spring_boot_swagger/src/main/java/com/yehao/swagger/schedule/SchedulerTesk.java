package com.yehao.swagger.schedule;

import io.swagger.annotations.ApiImplicitParam;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Package: com.yehao.swagger.schedule
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/8/2.
 */
@Component
public class SchedulerTesk {

    private int count = 0;

    @Scheduled(cron="*/5 * * * * ?")
    private void process(){
        System.out.println("this is scheduler task running " + ++count);
    }


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 6000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
    }
}
