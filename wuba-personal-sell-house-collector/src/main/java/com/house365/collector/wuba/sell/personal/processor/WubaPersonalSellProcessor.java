package com.house365.collector.wuba.sell.personal.processor;

import com.house365.collector.base.constant.FieldNameConstant;
import com.house365.collector.base.enumeration.PlatformEnum;
import com.house365.collector.base.processor.AbstractPersonalSellProcessor;
import com.house365.collector.wuba.sell.personal.util.WubaHouseInfoExtractUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.List;

/**
 * 58个人二手房列表页详情页Processor
 */
public class WubaPersonalSellProcessor extends AbstractPersonalSellProcessor {

    private static final Logger logger = LoggerFactory.getLogger(WubaPersonalSellProcessor.class);

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    public WubaPersonalSellProcessor(String cityCode, String listPageRootUrl, String listPageUrlPattern, String
            detailPageUrlPattern, int listPageNumLimit) {

        this.cityCode = cityCode;
        this.listPageRootUrl = listPageRootUrl;
        this.listPageUrlPattern = listPageUrlPattern;
        this.detailPageUrlPattern = detailPageUrlPattern;
        this.listPageNumLimit = listPageNumLimit;
    }

    @Override
    public void process(Page page) {

        // 设置数据类型
        setDataType(page);

        // 房源卡片xpath
        String houseCardXpath = "//ul[@class='house-list-wrap']";

        // 处理页面
        processPage(page, houseCardXpath);
    }

    /**
     * 处理页面
     *
     * @param page
     * @param houseCardXpath
     */
    private void processPage(Page page, String houseCardXpath) {

        // 处理列表页
        if (page.getUrl().regex(listPageUrlPattern).match()) {

            List<String> links =
                    page.getHtml().xpath(houseCardXpath).links().regex(detailPageUrlPattern).all();

            for (String link : links) {

                if (link.indexOf("?") != -1) {

                    link = link.substring(0, link.indexOf("?"));
                    // 加入详情页url
                    page.addTargetRequest(link);
                }
            }

            page.setSkip(true);
        } else if (page.getUrl().regex(detailPageUrlPattern).match()) {

            // 处理详情页
            processDetailPage(page);
        }
    }

    /**
     * 处理详情页
     *
     * @param page
     */
    private void processDetailPage(Page page) {

        // 城市代码
        page.putField(FieldNameConstant.CITY_CODE, cityCode);
        // 平台id
        page.putField(FieldNameConstant.PLATFORM_ID, PlatformEnum.WUBA.getId());
        String url = page.getUrl().toString();
        String houseId = "";
        if (url.indexOf("/") != -1 && url.indexOf(".shtml") != -1) {

            houseId = url.substring(url.lastIndexOf("/") + 1, url.indexOf(".shtml"));
        }
        // 房源id
        page.putField(FieldNameConstant.HOUSE_ID, houseId);
        // url
        page.putField(FieldNameConstant.URL, url);
        // 房源标题
        page.putField(FieldNameConstant.TITLE, page.getHtml().xpath("//div[@class='house-title']/h1/text()").toString());
        int locationLength = page.getHtml().xpath("//div[@class='nav-top-bar']/a").nodes().size();
        page.putField(FieldNameConstant.DISTRICT, "");
        page.putField(FieldNameConstant.SUB_DISTRICT, "");
        if (locationLength >= 2) {
            // 区属
            page.putField(FieldNameConstant.DISTRICT,
                    StringUtils.trim(page.getHtml().xpath("//div[@class='nav-top-bar']/a[2]/text()").toString().replace("二手房",
                            "")));
        }
        if (locationLength == 3) {
            // 板块
            page.putField(FieldNameConstant.SUB_DISTRICT, StringUtils.trim(page.getHtml().xpath("//div[@class='nav" +
                    "-top-bar']/a[3]/text()").toString().replace("二手房", "")));
        }
        // 所属小区
        // todo 小区名 https://nj.58.com/ershoufang/38057319566470x.shtml
        page.putField(FieldNameConstant.BLOCK_NAME, StringUtils.trim(page.getHtml().xpath
                ("//ul[@class='house-basic-item3']/li[1]/span[2]/text()").toString()));
        // 所属小区id(58暂无小区id)
        page.putField(FieldNameConstant.BLOCK_ID, "");
        // 房源总价
        String totalPrice = page.getHtml().xpath("//p[@class='house-basic-item1']/span[1]/text()").toString();
        totalPrice = WubaHouseInfoExtractUtil.getDecodedStr(page, totalPrice);
        page.putField(FieldNameConstant.TOTAL_PRICE, WubaHouseInfoExtractUtil.getFloatValue(totalPrice));
        // 房源单价
        String unitPrice = page.getHtml().xpath("//p[@class='house-basic-item1']/span[2]/text()").toString();
        unitPrice = WubaHouseInfoExtractUtil.getDecodedStr(page, unitPrice);
        page.putField(FieldNameConstant.UNIT_PRICE, WubaHouseInfoExtractUtil.getUnitPrice(unitPrice));
        // 户型
        String houseStruct = page.getHtml().xpath("//div[@class='house-basic-item2']/p[@class='room']/span[@class" +
                "='main']/text()").toString();
        // 几室
        page.putField(FieldNameConstant.ROOM_COUNT, WubaHouseInfoExtractUtil.getRoomCount(houseStruct));
        // 几厅
        page.putField(FieldNameConstant.HALL_COUNT, WubaHouseInfoExtractUtil.getHallCount(houseStruct));
        // 几卫
        page.putField(FieldNameConstant.TOILET_COUNT, WubaHouseInfoExtractUtil.getToiletCount(houseStruct));
        String floor =
                page.getHtml().xpath("//div[@class='house-basic-item2']/p[@class='room']/span[@class='sub']/text()").toString();
        // 房源总楼层
        // todo 房源总楼层 https://nj.58.com/ershoufang/38057319566470x.shtml
        page.putField(FieldNameConstant.TOTAL_FLOOR, WubaHouseInfoExtractUtil.getTotalFloor(floor));
        // 当前楼层
        page.putField(FieldNameConstant.FLOOR_CODE, WubaHouseInfoExtractUtil.getFloorCode(floor));
        // 房源朝向
        page.putField(FieldNameConstant.FORWARD, StringUtils.trim(page.getHtml().xpath("//div[@class='house-basic" +
                "-item2']/p[@class='toward']/span[@class='main']/text()").toString()));
        // 房源装修
        page.putField(FieldNameConstant.DECORATION, StringUtils.trim(page.getHtml().xpath("//div[@class='house-basic-item2']/p[@class" +
                "='area']/span[@class='sub']/text()").toString()));
        // 建筑面积
        page.putField(FieldNameConstant.BUILD_AREA,
                WubaHouseInfoExtractUtil.getBuildArea(StringUtils.trim(page.getHtml().xpath(
                        "//div[@class='house-basic-item2']/p[@class='area']/span[@class='main']/text()").toString())));
        // 房源年代
        page.putField(FieldNameConstant.BUILD_YEAR, WubaHouseInfoExtractUtil.getBuildYear(page.getHtml().xpath("//div[@class" +
                "='house-basic-item2']/p[@class='toward']/span[@class='sub']/text()").toString()));
        // 产权年限
        page.putField(FieldNameConstant.PROPERTY_RIGHT_YEAR, 0);
        // 电话
        page.putField(FieldNameConstant.PHONE, page.getHtml().xpath("//p[@class='phone-num']/text()").toString());
        // 房源描述
        page.putField(FieldNameConstant.DESC, page.getHtml().xpath("//p[@class='pic-desc-word']/text()").toString());
        // 房源图片
        List<String> imgSrcList = page.getHtml().xpath("//ul[@class='general-pic-list']/li/img/@data-src").all();
        for (int index = 0; index < imgSrcList.size(); index++) {

            String src = imgSrcList.get(index);
            src = src.split("\\?")[0];
            imgSrcList.set(index, src);
        }
        page.putField(FieldNameConstant.IMG, imgSrcList);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
