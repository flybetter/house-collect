package com.house365.collector.beike.sell;

import com.house365.collector.base.constant.CityCodeConstant;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.pipeline.ActiveMQPipeline;
import com.house365.collector.beike.sell.processor.BeikeDistrictProcessor;
import us.codecraft.webmagic.Spider;

public class BeikeDistrictLauncher {

    public static void main(String[] args) {

        String cityRootUrl = "https://nj.ke.com";
        Spider.create(new BeikeDistrictProcessor(CityCodeConstant.NJ, cityRootUrl)).addPipeline(new
                ActiveMQPipeline(MQConstant.DISTRICT_QUEUE)).addUrl(cityRootUrl + "/ershoufang/").thread(5).run();
    }
}
