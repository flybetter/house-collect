package com.house365.collector.beike.sell.Thread;

import com.house365.collector.base.constant.CityCodeConstant;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.pipeline.ActiveMQPipeline;
import com.house365.collector.beike.sell.processor.BeikeSellProcessor;
import us.codecraft.webmagic.Spider;

public class BeikeCommand implements Runnable {

    private static final String detailPageUrlPattern = "https://nj\\.ke\\.com/ershoufang/\\d{20}\\.html";

    @Override
    public void run() {
        String startPageUrl = "https://nj.ke.com/ershoufang/pg1co32/";
        String listPageRootUrl = "https://nj.ke.com/ershoufang/";
        String listPageUrlPattern = "https://nj\\.ke\\.com/ershoufang/(pg\\d{1,3})?co32/";

        Spider.create(new BeikeSellProcessor(CityCodeConstant.NJ, listPageRootUrl, listPageUrlPattern, detailPageUrlPattern, false))
                .addUrl(startPageUrl)
                .addPipeline(new ActiveMQPipeline(MQConstant.SELL_HOUSE_QUEUE))
                .thread(20)
                .run();
    }
}
