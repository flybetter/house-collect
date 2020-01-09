package com.house365.collector.woaiwojia.sell;

import com.house365.collector.base.constant.CityCodeConstant;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.pipeline.ActiveMQPipeline;
import com.house365.collector.woaiwojia.sell.processor.WoaiwojiaDistrictProcessor;
import us.codecraft.webmagic.Spider;

public class WoaiwojiaDistrictLauncher {

    public static void main(String[] args) {

        String cityRootUrl = "https://nj.5i5j.com";
        Spider.create(new WoaiwojiaDistrictProcessor(CityCodeConstant.NJ, cityRootUrl)).addPipeline(new
                ActiveMQPipeline(MQConstant.DISTRICT_QUEUE)).addUrl(cityRootUrl + "/ershoufang/").thread(5).run();
    }
}
