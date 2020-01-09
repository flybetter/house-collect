package com.house365.collector.century21.sell;

import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.pipeline.ActiveMQPipeline;
import com.house365.collector.century21.sell.Thread.Century21Command;
import com.house365.collector.century21.sell.pageprocessor.Century21SellLatestProcessor;
import com.house365.collector.century21.sell.pageprocessor.Century21SellProcessor;
import org.springframework.scheduling.annotation.Scheduled;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Century21SellLatestLauncher {

    public static void main(String[] args) throws Exception {

        Spider.create(new Century21SellLatestProcessor()).
                addUrl("https://nj.c21.com.cn/ershoufang/esf/pg1").
                addPipeline(new ActiveMQPipeline(MQConstant.SELL_HOUSE_QUEUE)).
                addPipeline(new ConsolePipeline()).
                thread(20).
                run();

//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
//        long oneDay = 24 * 60 * 60 * 1000;
//        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
//        DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//        dayFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//        Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " 23:30:00");//
//        long initDelay = curDate.getTime() - System.currentTimeMillis();
//        initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
//        scheduledExecutorService.scheduleAtFixedRate(new Century21Command(), initDelay, oneDay, TimeUnit.MILLISECONDS);

    }
}
