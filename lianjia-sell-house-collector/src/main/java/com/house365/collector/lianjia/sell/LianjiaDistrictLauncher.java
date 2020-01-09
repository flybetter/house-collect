package com.house365.collector.lianjia.sell;

        import com.house365.collector.base.constant.CityCodeConstant;
        import com.house365.collector.base.constant.MQConstant;
        import com.house365.collector.base.pipeline.ActiveMQPipeline;
        import com.house365.collector.lianjia.sell.processor.LianjiaDistrictProcessor;
        import us.codecraft.webmagic.Spider;

public class LianjiaDistrictLauncher {

    public static void main(String[] args) {

        String cityRootUrl = "https://nj.lianjia.com";
        Spider.create(new LianjiaDistrictProcessor(CityCodeConstant.NJ, cityRootUrl)).addPipeline(new
                ActiveMQPipeline(MQConstant.DISTRICT_QUEUE)).addUrl(cityRootUrl + "/ershoufang/").thread(5).run();
    }
}
