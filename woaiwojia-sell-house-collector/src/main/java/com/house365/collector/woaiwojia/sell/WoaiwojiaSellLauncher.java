package com.house365.collector.woaiwojia.sell;

import com.house365.collector.base.constant.CityCodeConstant;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.pipeline.ActiveMQPipeline;
import com.house365.collector.base.proxy.MyProxyProvider;
import com.house365.collector.woaiwojia.sell.downloader.WoaiwojiaDownloader;
import com.house365.collector.woaiwojia.sell.processor.WoaiwojiaSellProcessor;
import us.codecraft.webmagic.Spider;

public class WoaiwojiaSellLauncher {

    private static final String detailPageUrlPattern = "https://nj\\.5i5j\\.com/ershoufang/\\d{8}.html";

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

        String startPageUrl = "https://nj.5i5j.com/ershoufang/o8n1/";
        String listPageRootUrl = "https://nj.5i5j.com/ershoufang/";
        String listPageUrlPattern = "https://nj\\.5i5j\\.com/ershoufang/o8n\\d{1,2}/";

        // 自定义Downloader
        WoaiwojiaDownloader myDownloader = new WoaiwojiaDownloader(true);
        myDownloader.setProxyProvider(new MyProxyProvider());

        Spider.create(new WoaiwojiaSellProcessor(CityCodeConstant.NJ, listPageRootUrl, listPageUrlPattern, detailPageUrlPattern, false))
                .addUrl(startPageUrl)
                .addPipeline(new ActiveMQPipeline(MQConstant.SELL_HOUSE_QUEUE))
                .thread(5)
                .setDownloader(myDownloader)
                .run();
    }

    /**
     * 全量采集
     */
    private static void fullCollect() {

        String[] subDistricts = {
                "changfujie",
                "changlelu",
                "chaotiangong",
                "daguanglu",
                "daminglu",
                "fuzimiao",
                "guanghualu",
                "guanghuamen",
                "haifuxiang",
                "hongjiayuan",
                "jiankanglu",
                "jiqinglu",
                "laifeng",
                "mochoulu",
                "muxuyuan",
                "qinhong",
                "ruijinlu",
                "sanshanjie",
                "shengzhoulu",
                "shiyanglu",
                "shuiyoucheng",
                "wudingmen",
                "wulaocun",
                "xinjiekou",
                "yueyahu",
                "zhonghuamen",
                "zhongshanmen",
                "baijiahu",
                "binjiangkaifaqu",
                "chalukou",
                "chunhuajiedao",
                "dongjiaoxiaozhen",
                "dongshanzhen",
                "guli",
                "hedingqiao",
                "hushujiedao",
                "jiangjundadao",
                "jiangningdaxuecheng",
                "jiangningkaifaqu",
                "jiulonghu",
                "kexueyuan",
                "lukou",
                "molingjiedao",
                "qilinxincheng",
                "qilinzhen",
                "shangfang",
                "tangshan",
                "tianyindadao",
                "zhushanlu",
                "aonan",
                "aoti",
                "aotixincheng",
                "chanan",
                "huxijie",
                "jiangxinzhou",
                "jiqingmen",
                "mochouhu",
                "nanhu",
                "nanyuan",
                "shuiximen",
                "wandaguangchang",
                "xinglong",
                "yingtiandajie",
                "youfangqiao",
                "daqiaonanlu",
                "fenghuangxijie",
                "fujianlu",
                "guloubinjiang",
                "hanzhongmen",
                "huaqiaolu",
                "hunanlu",
                "jiangdong",
                "jianninglu",
                "jinlingxiaoqu",
                "longjiang",
                "mufuxilu",
                "ninghailu",
                "qingliangmen",
                "rehenanlu",
                "sanpailou",
                "shuizuogang",
                "wutangguangchang",
                "xiaoshi",
                "xinchengshiguangchang",
                "xinmofanmalu",
                "xufuxiang",
                "yancangqiao",
                "yijiangmen",
                "beijingdonglu",
                "changjianglu",
                "danfengjie",
                "hongshan",
                "houzaimen",
                "huayuanlu",
                "suojincun",
                "weigang",
                "xiaolingwei",
                "xinzhuang",
                "xuanwumen",
                "yinchengdongyuan",
                "yingtuohuayuan",
                "yueyuan",
                "zhujianglu",
                "baimazhen",
                "lishui",
                "maigaoqiao",
                "maqun",
                "moxiang",
                "qixiazhen",
                "wanshou",
                "xianhemen",
                "xianlin",
                "xianlinhu",
                "xiaozhuang",
                "yanziji",
                "yaohuamen",
                "andemen",
                "banqiao",
                "meishan",
                "nengrenli",
                "ningnan",
                "tiexinqiao",
                "xiaoxing",
                "yuhuaxincun",
                "dahuajinxiuhuacheng",
                "dingshan",
                "gaoxin",
                "hongyangguangchang",
                "jiangpu",
                "mingfabinjiangxincheng",
                "panchengjiedao",
                "qiaobei",
                "qiaolinjiaodao",
                "taishan",
                "tangquanjiedao",
                "tianruncheng",
                "weinisishuicheng",
                "xingdianzhen",
                "xuriaishangcheng",
                "yanjiang",
                "changlujiedao",
                "dachang",
                "getang",
                "hengliangjiedao",
                "longchijiedao",
                "shanpanjiedao",
                "xichangmenjiedao",
                "xiejiadian",
                "xiongzhoujiedao"
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

        String startPageUrl = "https://nj.5i5j.com/ershoufang/" + subDistrictName + "/n1/";
        String listPageRootUrl = "https://nj.5i5j.com/ershoufang/" + subDistrictName + "/";
        String listPageUrlPattern = "https://nj\\.5i5j\\.com/ershoufang/" + subDistrictName + "/n\\d{1,4}/";

        // 自定义Downloader
        WoaiwojiaDownloader myDownloader = new WoaiwojiaDownloader(true);
        myDownloader.setProxyProvider(new MyProxyProvider());

        Spider.create(new WoaiwojiaSellProcessor(CityCodeConstant.NJ, listPageRootUrl, listPageUrlPattern, detailPageUrlPattern, true))
                .addUrl(startPageUrl)
                .addPipeline(new ActiveMQPipeline(MQConstant.SELL_HOUSE_QUEUE))
                .thread(20)
                .setDownloader(myDownloader)
                .run();
    }
}
