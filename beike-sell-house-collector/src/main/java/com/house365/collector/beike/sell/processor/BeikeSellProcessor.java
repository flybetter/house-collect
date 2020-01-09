package com.house365.collector.beike.sell.processor;

import com.house365.collector.base.constant.FieldNameConstant;
import com.house365.collector.base.enumeration.PlatformEnum;
import com.house365.collector.base.processor.AbstractLianjiaBeikeSellProcessor;
import com.house365.collector.base.util.LianjiaBeikeHouseInfoExtractUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 贝壳二手房列表页详情页Processor
 */
public class BeikeSellProcessor extends AbstractLianjiaBeikeSellProcessor {

    private static final Logger logger = LoggerFactory.getLogger(BeikeSellProcessor.class);

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public BeikeSellProcessor(String cityCode, String listPageRootUrl, String listPageUrlPattern, String
            detailPageUrlPattern, boolean isFullCollect) {

        this.cityCode = cityCode;
        this.listPageRootUrl = listPageRootUrl;
        this.listPageUrlPattern = listPageUrlPattern;
        this.detailPageUrlPattern = detailPageUrlPattern;
        this.isFullCollect = isFullCollect;
    }

    @Override
    public void process(Page page) {

        // 设置数据类型
        setDataType(page);

        // 列表页房源总数xpath
        String houseTotalXpath = "/html/body/div[1]/div[4]/div[1]/div[2]/div[1]/h2/span/text()";
        // 房源卡片xpath
        String houseCardXpath = "/html/body/div[1]/div[4]/div[1]/div[4]/ul";

        // 处理页面
        processPage(page, houseTotalXpath, houseCardXpath, isFullCollect);
    }

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 处理详情页
     *
     * @param page
     */
    @Override
    public void processDetailPage(Page page) {

        // 城市代码
        page.putField(FieldNameConstant.CITY_CODE, cityCode);
        // 平台id
        page.putField(FieldNameConstant.PLATFORM_ID, PlatformEnum.BEIKE.getId());
        // 房源id
        page.putField(FieldNameConstant.HOUSE_ID, StringUtils.trim(page.getHtml().xpath
                ("/html/body/div[1]/div[4]/div/div[2]/div[3]/div[4]/span[2]/text()").toString()));
        // url
        page.putField(FieldNameConstant.URL, page.getUrl().toString());
        // 房源标题
        page.putField(FieldNameConstant.TITLE, StringUtils.trim(page.getHtml().xpath
                ("/html/body/div[1]/div[2]/div[2]/div/div/div[1]/h1/text()").toString()));
        // 区属
        page.putField(FieldNameConstant.DISTRICT, page.getHtml().xpath
                ("/html/body/div[1]/div[4]/div/div[2]/div[3]/div[2]/span[2]/a[1]/text()").toString());
        // 板块
        page.putField(FieldNameConstant.SUB_DISTRICT, page.getHtml().xpath
                ("/html/body/div[1]/div[4]/div/div[2]/div[3]/div[2]/span[2]/a[2]/text()").toString());
        // 所属小区
        page.putField(FieldNameConstant.BLOCK_NAME, page.getHtml().xpath
                ("/html/body/div[1]/div[4]/div/div[2]/div[3]/div[1]/a[1]/text()").toString());
        // 所属小区id
        page.putField(FieldNameConstant.BLOCK_ID, StringUtils.getDigits(page.getHtml().$("a.info", "href")
                .toString()));
        // 房源总价
        page.putField(FieldNameConstant.TOTAL_PRICE, LianjiaBeikeHouseInfoExtractUtil.getFloatValue(page.getHtml().xpath
                ("/html/body/div[1]/div[4]/div/div[2]/div[1]/span[1]/text()").toString()));
        // 房源单价
        page.putField(FieldNameConstant.UNIT_PRICE, LianjiaBeikeHouseInfoExtractUtil.getFloatValue(page.getHtml().xpath
                ("/html/body/div[1]/div[4]/div/div[2]/div[1]/div[1]/div[1]/span/text()").toString()));
        // 房源总楼层
        page.putField(FieldNameConstant.TOTAL_FLOOR, LianjiaBeikeHouseInfoExtractUtil.getTotalFloor(page.getHtml().xpath
                ("/html/body/div[1]/div[4]/div/div[2]/div[2]/div[1]/div[2]/text()").toString()));
        // 当前楼层
        page.putField(FieldNameConstant.FLOOR_CODE, LianjiaBeikeHouseInfoExtractUtil.getFloorCode(page.getHtml().xpath
                ("/html/body/div[1]/div[4]/div/div[2]/div[2]/div[1]/div[2]/text()").toString()));
        // 房源朝向
        page.putField(FieldNameConstant.FORWARD, StringUtils.replace(page.getHtml().xpath
                ("/html/body/div[1]/div[4]/div/div[2]/div[2]/div[2]/div[1]/text()").toString(), " ", ""));
        // 房源装修
        page.putField(FieldNameConstant.DECORATION, LianjiaBeikeHouseInfoExtractUtil.getDecoration(page.getHtml().xpath
                ("/html/body/div[1]/div[4]/div/div[2]/div[2]/div[2]/div[2]/text()").toString()));
        // 建筑面积
        page.putField(FieldNameConstant.BUILD_AREA, LianjiaBeikeHouseInfoExtractUtil.getBuildArea(page.getHtml().xpath
                ("/html/body/div[1]/div[4]/div/div[2]/div[2]/div[3]/div[1]/text()").toString()));
        // 房源年代
        page.putField(FieldNameConstant.BUILD_YEAR, LianjiaBeikeHouseInfoExtractUtil.getBuildYear(page.getHtml().xpath
                ("/html/body/div[1]/div[4]/div/div[2]/div[2]/div[3]/div[2]/text()").toString()));

        // 配备电梯初始化
        page.putField(FieldNameConstant.HAS_LIFT, 0);
        // 产权年限初始化
        page.putField(FieldNameConstant.PROPERTY_RIGHT_YEAR, 0);

        for (Selectable basicAttr : page.getHtml().xpath
                ("/html/body/div[1]/div[5]/div[1]/div[1]/div/div/div[1]/div[2]/ul/li").nodes()) {
            String key = basicAttr.xpath("/li/span[1]/text()").toString();
            String value = basicAttr.xpath("/li/text()").toString();

            if (StringUtils.equals(key, "房屋户型")) {
                // 几室
                page.putField(FieldNameConstant.ROOM_COUNT, LianjiaBeikeHouseInfoExtractUtil.getRoomCount(value));
                // 几厅
                page.putField(FieldNameConstant.HALL_COUNT, LianjiaBeikeHouseInfoExtractUtil.getHallCount(value));
                // 几卫
                page.putField(FieldNameConstant.TOILET_COUNT, LianjiaBeikeHouseInfoExtractUtil.getToiletCount(value));
            } else if (StringUtils.equals(key, "配备电梯")) {
                // 配备电梯
                page.putField(FieldNameConstant.HAS_LIFT, LianjiaBeikeHouseInfoExtractUtil.getHasLift(value));
            } else if (StringUtils.equals(key, "产权年限")) {
                if (!StringUtils.equals("未知", value)) {
                    // 产权年限
                    page.putField(FieldNameConstant.PROPERTY_RIGHT_YEAR, Integer.valueOf(StringUtils.getDigits(value)));
                }
            }
        }

        for (Selectable tradingAttr : page.getHtml().xpath
                ("/html/body/div[1]/div[5]/div[1]/div[1]/div/div/div[2]/div[2]/ul/li").nodes()) {
            String key = null;
            String value = null;
            if (tradingAttr.xpath("/li/span").nodes().size() == 2) {
                key = tradingAttr.xpath("/li/span[1]/text()").toString();
                value = StringUtils.trim(tradingAttr.xpath("/li/span[2]/text()").toString());
            } else {
                key = tradingAttr.xpath("/li/span/text()").toString();
                value = StringUtils.trim(tradingAttr.xpath("/li/text()").toString());
            }

            // 挂牌时间
            if (StringUtils.equals(key, "挂牌时间")) {
                page.putField(FieldNameConstant.LIST_TIME, StringUtils.getDigits(value));
            }
        }

        // 判断是否为车位
        String roomInfo = page.getHtml().xpath("/html/body/div[1]/div[4]/div/div[2]/div[2]/div[1]/div[1]/text()")
                .toString();
        if (StringUtils.equals("车位", roomInfo)) {
            page.setSkip(true);
        }
    }
}
