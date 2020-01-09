package com.house365.collector.beike.sell;

import com.house365.collector.base.constant.CityCodeConstant;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.pipeline.ActiveMQPipeline;
import com.house365.collector.beike.sell.processor.BeikeSellProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

public class BeikeSellLauncher {

    private static final String detailPageUrlPattern = "https://nj\\.ke\\.com/ershoufang/\\d{20}\\.html";

    public static void main(String[] args) {

        // 是否全量采集
        boolean isFullCollect = false;
        if (args.length == 1) {
            isFullCollect = Boolean.valueOf(args[0]);
        }
        if (isFullCollect) {
            fullCollect();
        } else {
            increaseCollect();
        }
    }

    /**
     * 增量采集
     */
    private static void increaseCollect() {

        String startPageUrl = "https://nj.ke.com/ershoufang/pg1co32/";
        String listPageRootUrl = "https://nj.ke.com/ershoufang/";
        String listPageUrlPattern = "https://nj\\.ke\\.com/ershoufang/(pg\\d{1,3})?co32/";

        Spider.create(new BeikeSellProcessor(CityCodeConstant.NJ, listPageRootUrl, listPageUrlPattern, detailPageUrlPattern, false))
                .addUrl(startPageUrl)
                .addPipeline(new ConsolePipeline())
                .addPipeline(new ActiveMQPipeline(MQConstant.SELL_HOUSE_QUEUE))
                .thread(20)
                .run();
    }

    /**
     * 全量采集
     */
    private static void fullCollect() {

        String[] subDistricts = {
                "andemen",
                "banqiao",
                "chalukou1",
                "daishanxincheng",
                "hongjiayuan1",
                "jiangjundadao11",
                "meishan",
                "nanjingnanzhan1",
                "nengrenli",
                "tiexinqiao",
                "xiaoxing",
                "yuhuaxincun",
                "andemen",
                "aoti3",
                "chanan",
                "hanzhongmendajie",
                "huxijie",
                "jiangdong2",
                "jiangxinzhou",
                "nanhu4",
                "nanyuan2",
                "shuiximen1",
                "wandaguangchang1",
                "xiaoxing",
                "xinglongdajie",
                "yingtianxilu1",
                "caochangmendajie",
                "dinghuaimendajie",
                "fenghuangxijie1",
                "fujianlu",
                "hanzhongmendajie",
                "huaqiaolu1",
                "hunanlu",
                "jianninglu",
                "jinlingxiaoqu",
                "longjiang",
                "ninghailu",
                "rehenanlu",
                "sanpailou",
                "shuizuogang",
                "wutangguangchang",
                "xiaoshi1",
                "xiaozhuang",
                "xufuxiang",
                "yijiangmen",
                "changfujie2",
                "changlelu11",
                "chaotiangong",
                "daguanglu",
                "fuzimiao",
                "guanghualu",
                "hongjiayuan1",
                "jiankanglu",
                "laifengli",
                "qinhong",
                "ruijinlu1",
                "shengzhoulu",
                "wulaocun",
                "xinjiekou1",
                "yueyahu",
                "beijingdonglu",
                "changjianglu2",
                "danfengjie1",
                "hongshan1",
                "houzaimen",
                "maigaoqiao1",
                "maqun1",
                "suojincun",
                "weigang1",
                "xianhemen",
                "xiaolingwei1",
                "xuanwumen11",
                "yingtuohuayuan",
                "yueyuan",
                "zhujianglu",
                "baijiahu",
                "chalukou1",
                "dongshanzhen",
                "jiangjundadao11",
                "jiangningbinjiang",
                "jiangningdaxuecheng",
                "jiangningqita11",
                "jiulonghu",
                "kexueyuan",
                "lukou",
                "qilinzhen",
                "tangshanzhen",
                "zhengfangxincheng",
                "dingshanjiedao",
                "gaoxinqu2",
                "jiangpujiedao",
                "pukouqita11",
                "qiaobei",
                "taishanjiedao",
                "maigaoqiao1",
                "maqun1",
                "qilinzhen",
                "qixiaqita1",
                "wanshou1",
                "wutangguangchang",
                "xianhemen",
                "xianlin2",
                "xiaozhuang",
                "yanziji",
                "yaohuamen",
                "yueyuan",
                "dachang12",
                "liuheqita1",
                "longchi",
                "luhezhucheng",
                "nanmenxincheng",
                "lishuichengbei",
                "lishuichengnan",
                "lishuichengxi",
                "lishuichengzhong",
                "lishuihengda",
                "lishuiqita",
                "gaochun11"
        };
        for (String subDistrict : subDistricts) {
            collectBySubDistrict(subDistrict);
        }
    }

    /**
     * 按板块采集
     *
     * @param subDistrictName
     */
    private static void collectBySubDistrict(String subDistrictName) {

        String startPageUrl = "https://nj.ke.com/ershoufang/" + subDistrictName + "/pg1p1/";
        String listPageRootUrl = "https://nj.ke.com/ershoufang/" + subDistrictName + "/";
        String listPageUrlPattern = "https://nj\\.ke\\.com/ershoufang/" + subDistrictName + "/(pg\\d{1,3})?p\\d{1}/";

        Spider.create(new BeikeSellProcessor(CityCodeConstant.NJ, listPageRootUrl, listPageUrlPattern, detailPageUrlPattern, true))
                .addUrl(startPageUrl)
                .addPipeline(new ActiveMQPipeline(MQConstant.SELL_HOUSE_QUEUE))
                .thread(20)
                .run();
    }
}
