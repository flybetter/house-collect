package com.house365.collector.anjuke.sell;

import com.house365.collector.anjuke.sell.processor.AnjukeDistrictProcessor;
import com.house365.collector.base.constant.CityCodeConstant;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.pipeline.ActiveMQPipeline;
import us.codecraft.webmagic.Spider;

public class AnjukeDistrictLauncher {

    public static void main(String[] args) {

        String cityRootUrl = "https://nanjing.anjuke.com";
        Spider.create(new AnjukeDistrictProcessor(CityCodeConstant.NJ, cityRootUrl)).addPipeline(new
                ActiveMQPipeline(MQConstant.DISTRICT_QUEUE)).addUrl(cityRootUrl + "/sale/").thread(5).run();
    }
}
