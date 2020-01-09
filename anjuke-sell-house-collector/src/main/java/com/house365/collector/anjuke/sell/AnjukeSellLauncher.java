package com.house365.collector.anjuke.sell;

import com.house365.collector.anjuke.sell.downloader.AnjukeDownloader;
import com.house365.collector.anjuke.sell.processor.AnjukeSellProcessor;
import com.house365.collector.base.constant.CityCodeConstant;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.pipeline.ActiveMQPipeline;
import com.house365.collector.base.proxy.MyProxyProvider;
import us.codecraft.webmagic.Spider;

public class AnjukeSellLauncher {
    private static int PAGE_NUM_LIMIT = 10;
    private static final String[] PRICE_CODE_ARRAY = {
//            "m5074",
//            "m65",
//            "m66",
            "m67",
//            "m68",
//            "m5037",
//            "m5038",
//            "m5039",
//            "m5075",
//            "m5076"
    };

    public static void main(String[] args) {
        String[] subDistricts = {
//                "pukou-q-dingshan",
//                "pukou-q-njgaoxin",
//                "pukou-q-njjiangpu",
//                "pukou-q-tangquan",
//                "pukou-q-pukouzhoubian",
//                "pukou-q-qiaobei",
//                "pukou-q-qiaolin",
                "jiangninga-q-bjhnj",
//                "jiangninga-q-chunhua",
//                "jiangninga-q-chalukou",
//                "jiangninga-q-njdongshan",
//                "jiangninga-q-njhs",
//                "jiangninga-q-jiangjundadao",
//                "jiangninga-q-jndxcnj",
//                "jiangninga-q-njkaifaqu",
//                "jiangninga-q-kexueyuan",
//                "jiangninga-q-njlukou",
//                "jiangninga-q-moling",
//                "jiangninga-q-qilinmen",
//                "jiangninga-q-shangfang",
//                "jiangninga-q-sfnj",
//                "jiangninga-q-njtangshan",
//                "jiangninga-q-tqnj",
//                "jiangninga-q-zgnj",
//                "yuhuatai-q-andemen",
//                "yuhuatai-q-banqiao",
//                "yuhuatai-q-hfsnj",
//                "yuhuatai-q-njmeishan",
//                "yuhuatai-q-nznj",
//                "yuhuatai-q-nengrenli",
//                "yuhuatai-q-tbnj",
//                "yuhuatai-q-tiexinqiao",
//                "yuhuatai-q-xishanqiao",
//                "jianye-q-annj",
//                "jianye-q-shuangzha",
//                "jianye-q-shazhou",
//                "jianye-q-jianyequ",
//                "jianye-q-njnanhu",
//                "jianye-q-njnanyuan",
//                "jianye-q-wdgcnj",
//                "jianye-q-xinglong",
//                "qixia-q-baguazhou",
//                "qixia-q-janj",
//                "qixia-q-njqixia",
//                "qixia-q-njlongtan",
//                "qixia-q-maigaoqiao",
//                "qixia-q-maqun",
//                "qixia-q-qxsnj",
//                "qixia-q-qixiaqita",
//                "qixia-q-xianlin",
//                "qixia-q-xiaozhuang",
//                "qixia-q-yaohua",
//                "qixia-q-yanziji",
//                "qinhuai-q-chaotiangong",
//                "qinhuai-q-changfujie",
//                "qinhuai-q-changbaijie",
//                "qinhuai-q-shuangtang",
//                "qinhuai-q-daguanglu",
//                "qinhuai-q-daxinggong",
//                "qinhuai-q-qinhuaiqu",
//                "qinhuai-q-fuzimiao",
//                "qinhuai-q-njbxq",
//                "qinhuai-q-huaihailu",
//                "qinhuai-q-hongwulu",
//                "qinhuai-q-jiqingmen",
//                "qinhuai-q-hongjiayuan",
//                "qinhuai-q-qinhong",
//                "qinhuai-q-ruijinlu",
//                "qinhuai-q-baixiaqu",
//                "qinhuai-q-wulaocun",
//                "qinhuai-q-yueyahu",
//                "qinhuai-q-zhonghuamen",
//                "xuanwub-q-beijingdonglu",
//                "xuanwub-q-xinjiekou",
//                "xuanwub-q-danfengjie",
//                "xuanwub-q-houzaimen",
//                "xuanwub-q-njhongshan",
//                "xuanwub-q-suojincun",
//                "xuanwub-q-weigang",
//                "xuanwub-q-njxuanwumen",
//                "xuanwub-q-xiaolingwei",
//                "xuanwub-q-xuanwuquqita",
//                "xuanwub-q-yingtuohuayuan",
//                "xuanwub-q-yue",
//                "xuanwub-q-xuanwuhu",
//                "xuanwub-q-zjsnj",
//                "liuhe-q-xiejiadian",
//                "liuhe-q-getang",
//                "liuhe-q-longchi",
//                "liuhe-q-liuhezhoubian",
//                "liuhe-q-xiongzhou",
//                "binjiangkaifaqu-q-binjiangkfq",
//                "binjiangkaifaqu-q-gulinj",
//                "binjiangkaifaqu-q-hengxi",
//                "guloua-q-gulouqita",
//                "guloua-q-fenghuangxijie",
//                "guloua-q-fujianlu",
//                "guloua-q-njhuaqiaolu",
//                "guloua-q-shanxilu",
//                "guloua-q-jianninglu",
//                "guloua-q-jinlingxiaoqu",
//                "guloua-q-njjiangdong",
//                "guloua-q-njlongjiang",
//                "guloua-q-ninghailu",
//                "guloua-q-rehenanlu",
//                "guloua-q-njsanpailou",
//                "guloua-q-shuizuogang",
//                "guloua-q-wutang",
//                "guloua-q-xiaoshi",
//                "guloua-q-xufuxiang",
//                "guloua-q-yijiangmen",
//                "guloua-q-zhongyangmen",
//                "gaochun-q-chunchengzhen",
//                "lishuia-q-lishuikaifaqu",
//                "nanjingzhoubian-q-chuzhounj",
//                "nanjingzhoubian-q-zhengjiangnj"
        };
        for (String subDistrict : subDistricts) {
            startBySubDistrict(subDistrict);
        }

    }

    public static void startBySubDistrict(String subDistrictName) {

        String startPageUrl = "https://nanjing.anjuke.com/sale/" + subDistrictName + "/g1-m5074-p1/";
        String listPageRootUrl = "https://nanjing.anjuke.com/sale/" + subDistrictName +"/";
        String listPageUrlPattern = "https://nanjing\\.anjuke\\.com/sale/" + subDistrictName + "/g1-m\\d{2,4}-p\\d{1," +
                "2}";
        String detailPageUrlPattern = "https://nanjing\\.anjuke\\.com/prop/view/A\\d{10}";

        // 自定义Downloader
        AnjukeDownloader myDownloader = new AnjukeDownloader(false);
        myDownloader.setProxyProvider(new MyProxyProvider());

        Spider spider = Spider.create(new AnjukeSellProcessor(CityCodeConstant.NJ, listPageRootUrl, listPageUrlPattern,
                detailPageUrlPattern));

        // 添加列表页url
        for (int priceIndex = 0; priceIndex < PRICE_CODE_ARRAY.length; priceIndex++) {

            // 遍历价格
            for (int pageNum = 1; pageNum <= PAGE_NUM_LIMIT; pageNum++) {

                // 遍历页码
                String url = listPageRootUrl + "g1-" + PRICE_CODE_ARRAY[priceIndex] + "-p" + pageNum + "/";
                spider.addUrl(url);
            }
        }

        spider.addPipeline(new ActiveMQPipeline(MQConstant.SELL_HOUSE_QUEUE))
                .setDownloader(myDownloader)
                .thread(20).run();
    }
}
