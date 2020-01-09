package com.house365.collector.wuba.sell.personal;

import com.house365.collector.base.constant.CityCodeConstant;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.pipeline.ActiveMQPipeline;
import com.house365.collector.base.proxy.MyProxyProvider;
import com.house365.collector.wuba.sell.personal.downloader.WubaDownloader;
import com.house365.collector.wuba.sell.personal.processor.WubaPersonalSellProcessor;
import us.codecraft.webmagic.Spider;

public class WubaPersonalSellLauncher {

    public static void main(String[] args) {

        String startPageUrl = "https://nj.58.com/ershoufang/0/pn1/";
        String listPageRootUrl = "https://nj.58.com/ershoufang/0/";
        String listPageUrlPattern = "https://nj\\.58\\.com/ershoufang/0/pn\\d{1,2}/";
        String detailPageUrlPattern = "https://nj\\.58\\.com/ershoufang/\\d{14}x.shtml.*";

        WubaDownloader myDownloader = new WubaDownloader(false);
        myDownloader.setProxyProvider(new MyProxyProvider());

        Spider.create(new WubaPersonalSellProcessor(CityCodeConstant.NJ, listPageRootUrl, listPageUrlPattern,
                detailPageUrlPattern, 5))
                .addUrl(startPageUrl)
                .addPipeline(new ActiveMQPipeline(MQConstant.PERSONAL_SELL_HOUSE_QUEUE))
                .setDownloader(myDownloader)
                .thread(5)
                .run();
    }
}
