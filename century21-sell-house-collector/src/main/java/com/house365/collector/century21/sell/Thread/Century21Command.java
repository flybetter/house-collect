package com.house365.collector.century21.sell.Thread;

import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.pipeline.ActiveMQPipeline;
import com.house365.collector.century21.sell.pageprocessor.Century21SellLatestProcessor;
import us.codecraft.webmagic.Spider;

public class Century21Command implements Runnable {

    @Override
    public void run() {
//        Spider.create(new Century21SellLatestProcessor()).addUrl("https://nj.koofang.com/ershoufang/esf/pg1P1").
////                addPipeline(new ActiveMQPipeline(MQConstant.SELL_HOUSE_QUEUE)).Thread(20).run();
        Spider.create(new Century21SellLatestProcessor()).addUrl("https://nj.c21.com.cn/ershoufang/esf/pg1").
                addPipeline(new ActiveMQPipeline(MQConstant.SELL_HOUSE_QUEUE)).thread(20).run();

    }
}
