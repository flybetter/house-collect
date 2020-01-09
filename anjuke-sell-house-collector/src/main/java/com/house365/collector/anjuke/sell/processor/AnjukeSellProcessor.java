package com.house365.collector.anjuke.sell.processor;

import com.house365.collector.anjuke.sell.util.AnjukeHouseInfoExtractUtil;
import com.house365.collector.base.constant.FieldNameConstant;
import com.house365.collector.base.enumeration.PlatformEnum;
import com.house365.collector.base.processor.AbstractSellProcessor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Selectable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 安居客二手房列表页详情页Processor
 */
public class AnjukeSellProcessor extends AbstractSellProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AnjukeSellProcessor.class);

    private Site site = Site.me().setRetryTimes(5).setSleepTime(3000).setTimeOut(20000).addHeader("User-Agent",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:67.0) Gecko/20100101 Firefox/67.0").setCharset("UTF-8");;

    private Set<String> listPageUrlSet = new HashSet<>();

    public AnjukeSellProcessor(String cityCode, String listPageRootUrl, String listPageUrlPattern, String
            detailPageUrlPattern) {
        this.cityCode = cityCode;
        this.listPageRootUrl = listPageRootUrl;
        this.listPageUrlPattern = listPageUrlPattern;
        this.detailPageUrlPattern = detailPageUrlPattern;
    }

    @Override
    public void process(Page page) {

        // 设置数据类型
        setDataType(page);

        // 房源卡片xpath
        String houseCardXpath = "//*[@id=\"houselist-mod-new\"]";

        // 处理列表页
        if (page.getUrl().regex(listPageUrlPattern).match()) {

            List<String> hrefs = page.getHtml().xpath(houseCardXpath).links().all();

            for (String href : hrefs) {

                Pattern pattern = Pattern.compile(detailPageUrlPattern);
                Matcher matcher = pattern.matcher(href);
                if (matcher.find()) {

                    String pcUrl = matcher.group(0);
                    String mUrl = pcUrl.replace("https://nanjing.anjuke.com/prop/view/","https://m.anjuke.com/nj/sale/");
                    // 添加详情页url
                    page.addTargetRequest(mUrl);
                }
            }

            // 列表页不传递给pipeline处理
            page.setSkip(true);
        } else {

            // 处理详情页
            processDetailPage(page);
        }
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
    private void processDetailPage(Page page) {

        // 城市代码
        page.putField(FieldNameConstant.CITY_CODE, cityCode);
        // 平台id
        page.putField(FieldNameConstant.PLATFORM_ID, PlatformEnum.ANJUKE.getId());
        // 房源id
        page.putField(FieldNameConstant.HOUSE_ID, StringUtils.getDigits(page.getUrl().toString()));
        // url
        page.putField(FieldNameConstant.URL, page.getUrl().toString());
        // 房源标题
        page.putField(FieldNameConstant.TITLE, page.getHtml().xpath
                ("/html/body/div[2]/div/div[1]/div[2]/span/text()").toString());

        // 初始化配备电梯
        page.putField(FieldNameConstant.HAS_LIFT, 0);
        // 初始化产权年限
        page.putField(FieldNameConstant.PROPERTY_RIGHT_YEAR, 0);

        List<Selectable> details = page.getHtml().xpath("/html/body/div[2]/div/div[1]/div[4]/label").nodes();
        for (Selectable detail : details) {

            String key = detail.xpath("/label/i/text()").toString();
            String value = detail.xpath("/label/text()").toString();

            if (StringUtils.contains(key, "小区")) {

                String blockName = StringUtils.trim(detail.xpath("/label/a/text()").toString()).replace(" ", " ");

                if (StringUtils.isNotBlank(blockName) && blockName.split(" ").length == 3) {

                    String[] locInfo = blockName.split(" ");
                    // 区属
                    page.putField(FieldNameConstant.DISTRICT, locInfo[0]);
                    // 板块
                    page.putField(FieldNameConstant.SUB_DISTRICT, locInfo[1]);
                    // 所属小区
                    page.putField(FieldNameConstant.BLOCK_NAME, locInfo[2]);
                } else {

                    // 所属小区
                    page.putField(FieldNameConstant.BLOCK_NAME, blockName);
                }
                String blockUrl = detail.xpath("/label/a").css("a","href").toString();

                // 所属小区id
                page.putField(FieldNameConstant.BLOCK_ID, StringUtils.getDigits(blockUrl));
            } else if (StringUtils.contains(key, "区域")) {

                String district = StringUtils.trim(value).replace(" ", " ");
                if (StringUtils.isNotBlank(district) && district.split(" ").length == 2) {

                    String[] locInfo = district.split(" ");
                    // 区属
                    page.putField(FieldNameConstant.DISTRICT, locInfo[0]);
                    // 板块
                    page.putField(FieldNameConstant.SUB_DISTRICT, locInfo[1]);
                }
            } else if (StringUtils.contains(key, "售价")) {

                String totalPrice = detail.xpath("/label/span/em/text()").toString();
                // 房源总价
                page.putField(FieldNameConstant.TOTAL_PRICE, AnjukeHouseInfoExtractUtil.getFloatValue(totalPrice));
            } else if (StringUtils.contains(key, "单价")) {

                // 房源单价
                page.putField(FieldNameConstant.UNIT_PRICE, AnjukeHouseInfoExtractUtil.getUnitPrice(value));
            } else if (StringUtils.contains(key, "楼层")) {

                // 房源总楼层
                page.putField(FieldNameConstant.TOTAL_FLOOR, AnjukeHouseInfoExtractUtil.getTotalFloor(value));
                // 当前楼层
                page.putField(FieldNameConstant.FLOOR_CODE, AnjukeHouseInfoExtractUtil.getFloorCode(value));
            } else if (StringUtils.contains(key, "朝向")) {

                // 房源朝向
                page.putField(FieldNameConstant.FORWARD, value);
            } else if (StringUtils.contains(key, "装修")) {

                // 房源装修
                page.putField(FieldNameConstant.DECORATION, value);
            } else if (StringUtils.contains(key, "面积")) {

                // 建筑面积
                page.putField(FieldNameConstant.BUILD_AREA, AnjukeHouseInfoExtractUtil.getBuildArea(value));
            } else if (StringUtils.contains(key, "年代")) {

                // 房源年代
                page.putField(FieldNameConstant.BUILD_YEAR, AnjukeHouseInfoExtractUtil.getBuildYear(value));
            } else if (StringUtils.contains(key, "产权")) {

                // 产权年限
                page.putField(FieldNameConstant.PROPERTY_RIGHT_YEAR, Integer.valueOf(StringUtils.getDigits(value)));
            } else if (StringUtils.contains(key, "房型")) {

                // 房屋户型
                // 几室
                page.putField(FieldNameConstant.ROOM_COUNT, AnjukeHouseInfoExtractUtil.getRoomCount(value));
                // 几厅
                page.putField(FieldNameConstant.HALL_COUNT, AnjukeHouseInfoExtractUtil.getHallCount(value));
                // 几卫
                page.putField(FieldNameConstant.TOILET_COUNT, AnjukeHouseInfoExtractUtil.getToiletCount(value));
            } else if (StringUtils.contains(key, "电梯")) {

                // 配备电梯
                page.putField(FieldNameConstant.HAS_LIFT, AnjukeHouseInfoExtractUtil.getHasLift(value));
            }
        }
    }
}
