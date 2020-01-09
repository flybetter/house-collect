package com.house365.collector.century21.sell;

import com.house365.collector.base.constant.CityCodeConstant;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.pipeline.ActiveMQPipeline;
import com.house365.collector.century21.sell.Thread.Century21Command;
import com.house365.collector.century21.sell.pageprocessor.Century21SellProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.*;

public class Century21SellLauncher {

    public static void main(String[] args) throws Exception {

        Spider.create(new Century21SellProcessor()).addUrl("https://nj.koofang.com/ershoufang/esf/pg1rs").
                addPipeline(new ActiveMQPipeline(MQConstant.SELL_HOUSE_QUEUE)).
                addPipeline(new ConsolePipeline())
                .thread(20).
                run();

    }
}
