package com.house365.collector.lianjia.sell.thread;

import com.house365.collector.base.constant.CityCodeConstant;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.pipeline.ActiveMQPipeline;
import com.house365.collector.lianjia.sell.processor.LianjiaSellProcessor;
import us.codecraft.webmagic.Spider;

public class LianjiaCommand implements Runnable {

    private static final String detailPageUrlPattern = "https://nj\\.lianjia\\.com/ershoufang/\\d{12}\\.html";

    @Override
    public void run() {

        String startPageUrl = "https://nj.lianjia.com/ershoufang/pg1co32/";
        String listPageRootUrl = "https://nj.lianjia.com/ershoufang/";
        String listPageUrlPattern = "https://nj\\.lianjia\\.com/ershoufang/(pg\\d{1,3})?co32/";

        Spider.create(new LianjiaSellProcessor(CityCodeConstant.NJ, listPageRootUrl, listPageUrlPattern, detailPageUrlPattern, false))
                .addUrl(startPageUrl)
                .addPipeline(new ActiveMQPipeline(MQConstant.SELL_HOUSE_QUEUE))
                .thread(20)
                .run();
    }
}
