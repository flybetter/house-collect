package com.house365.collector.woaiwojia.sell.processor;

import com.house365.collector.base.constant.FieldNameConstant;
import com.house365.collector.base.enumeration.PlatformEnum;
import com.house365.collector.base.processor.AbstractSellProcessor;
import com.house365.collector.woaiwojia.sell.util.WoaiwojiaHouseInfoExtractUtil;
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
 * 我爱我家二手房列表页详情页Processor
 */
public class WoaiwojiaSellProcessor extends AbstractSellProcessor {

    private static final Logger logger = LoggerFactory.getLogger(WoaiwojiaSellProcessor.class);

    private static int PAGE_SIZE = 30;

    private Set<String> listPageUrlSet = new HashSet<>();

    private Site site = Site.me()
            .setRetryTimes(20)
            .setSleepTime(10000)
            .setTimeOut(20000)
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .setCharset("UTF-8")
            .addHeader("Accept-Encoding", "gzip, deflate, br")
            .addHeader("Host", "nj.5i5j.com")
            .addHeader("Referer", "https://nj.5i5j.com/ershoufang/")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:67.0) Gecko/20100101 Firefox/67.0")
            .addHeader("Connection", "keep-alive");

    public WoaiwojiaSellProcessor(String cityCode, String listPageRootUrl, String listPageUrlPattern, String
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
        String houseTotalXpath = "/html/body/div[4]/div[1]/div[1]/span/text()";
        // 房源卡片xpath
        String houseCardXpath = "/html/body/div[4]/div[1]/div[2]/ul";

        // 处理页面
        processPage(page, houseTotalXpath, houseCardXpath);
    }

    private void processPage(Page page, String houseTotalXpath, String houseCardXpath) {

        // 处理列表页
        if (page.getUrl().regex(listPageUrlPattern).match()) {

            listPageUrlSet.add(page.getUrl().toString());

            if (isFullCollect) {
                // 全量采集

                int total = Integer.valueOf(StringUtils.trim(page.getHtml().xpath
                        (houseTotalXpath).toString()));
                if (total > 0) {

                    int maxPageNum = total % PAGE_SIZE == 0 ? (total / PAGE_SIZE) : (total / PAGE_SIZE + 1);

                    // 加入详情页url
                    page.addTargetRequests(page.getHtml().xpath(houseCardXpath).links()
                            .regex(detailPageUrlPattern).all());

                    for (int pageNum = 2; pageNum <= maxPageNum; pageNum++) {

                        String listPageUrl = listPageRootUrl + "n" + pageNum + "/";
                        if (!listPageUrlSet.contains(listPageUrl)) {
                            // 加入列表页url
                            page.addTargetRequest(listPageUrl);
                            listPageUrlSet.add(listPageUrl);
                        }
                    }
                }
            } else {
                // 增量采集
                List<Selectable> liTags = page.getHtml().xpath(houseCardXpath + "/li").nodes();

                // 当前列表页是否存在最新发布房源
                boolean hasLatestSell = false;

                for (Selectable liTag : liTags) {

                    String publishInfo = liTag.xpath("/li/div[2]/div[1]/p[3]/text()").toString();
                    if (StringUtils.contains(publishInfo, "今天发布")) {

                        // 当前liTag为新发布房源
                        hasLatestSell = true;
                        page.addTargetRequests(liTag.links().regex(detailPageUrlPattern).all());
                    }
                }

                // 当前列表页存在最新发布房源的话,就再往后翻一页
                if (hasLatestSell) {

                    int currPageNum = getCurrPageNum(page.getUrl().toString(), listPageRootUrl, "(o8)(n\\d{1,2})");
                    String listPageUrl = listPageRootUrl + "o8n" + (currPageNum + 1) + "/";
                    if (!listPageUrlSet.contains(listPageUrl)) {
                        // 加入列表页url
                        page.addTargetRequest(listPageUrl);
                        listPageUrlSet.add(listPageUrl);
                    }
                }
            }

            page.setSkip(true);
        } else if (page.getUrl().regex(detailPageUrlPattern).match()) {

            // 处理详情页
            processDetailPage(page);
        }
    }

    private int getCurrPageNum(String url, String listPageRootUrl, String pagePattern) {

        String pageStr = StringUtils.remove(StringUtils.substring(url, listPageRootUrl.length()), "/");
        Pattern pattern = Pattern.compile(pagePattern);
        Matcher matcher = pattern.matcher(pageStr);
        String page = "";

        while (matcher.find()) {
            page = matcher.group(2);
        }

        if (StringUtils.isEmpty(page)) {
            return 1;
        } else {
            return Integer.valueOf(StringUtils.remove(page, "n"));
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
        page.putField(FieldNameConstant.PLATFORM_ID, PlatformEnum.WOAIWOJIA.getId());
        // url
        String url = page.getUrl().toString();
        page.putField(FieldNameConstant.URL, url);
        // 房源id
        String houseId = url.substring(url.lastIndexOf("/") + 1).replace(".html", "");
        page.putField(FieldNameConstant.HOUSE_ID, houseId);
        // 房源标题
        page.putField(FieldNameConstant.TITLE, StringUtils.trim(page.getHtml().xpath
                ("/html/body/div[3]/div[1]/div[1]/h1/text()").toString()));
        // 区属
        page.putField(FieldNameConstant.DISTRICT, page.getHtml().xpath
                ("/html/body/div[2]/div/div[1]/a[3]/text()").toString().replace("二手房", ""));
        // 板块
        page.putField(FieldNameConstant.SUB_DISTRICT, page.getHtml().xpath
                ("/html/body/div[2]/div/div[1]/a[4]/text()").toString().replace("二手房", ""));
        // 所属小区
        page.putField(FieldNameConstant.BLOCK_NAME, page.getHtml().xpath
                ("/html/body/div[3]/div[2]/div[2]/div[2]/ul/li[1]/a/text()").toString());
        // 所属小区id
        page.putField(FieldNameConstant.BLOCK_ID, StringUtils.getDigits(page.getHtml().xpath
                ("/html/body/div[3]/div[2]/div[2]/div[2]/ul/li[1]/a").$("a", "href").toString()));
        // 房源总价
        page.putField(FieldNameConstant.TOTAL_PRICE, WoaiwojiaHouseInfoExtractUtil.getFloatValue(page.getHtml().xpath
                ("/html/body/div[3]/div[2]/div[2]/div[1]/div[1]/div/p[1]/text()").toString()));
        // 房源单价(我爱我家单价是万/m2 需要乘以10000)
        page.putField(FieldNameConstant.UNIT_PRICE, WoaiwojiaHouseInfoExtractUtil.getFloatValue(page.getHtml().xpath
                ("/html/body/div[3]/div[2]/div[2]/div[1]/div[2]/div/p[1]/text()").toString()) * 10000);
        // 房源总楼层
        page.putField(FieldNameConstant.TOTAL_FLOOR, WoaiwojiaHouseInfoExtractUtil.getTotalFloor(page.getHtml().xpath
                ("/html/body/div[3]/div[2]/div[2]/div[2]/ul/li[2]/text()").toString()));
        // 当前楼层
        page.putField(FieldNameConstant.FLOOR_CODE, WoaiwojiaHouseInfoExtractUtil.getFloorCode(page.getHtml().xpath
                ("/html/body/div[3]/div[2]/div[2]/div[2]/ul/li[2]/text()").toString()));
        // 房源朝向
        page.putField(FieldNameConstant.FORWARD, StringUtils.replace(page.getHtml().xpath
                ("/html/body/div[3]/div[2]/div[2]/div[2]/ul/li[3]/text()").toString(), " ", ""));
        // 房源装修
        page.putField(FieldNameConstant.DECORATION, page.getHtml().xpath
                ("/html/body/div[3]/div[2]/div[2]/div[2]/ul/li[4]/text()").toString());
        // 建筑面积
        page.putField(FieldNameConstant.BUILD_AREA, Float.valueOf(page.getHtml().xpath
                ("/html/body/div[3]/div[2]/div[2]/div[1]/div[4]/div/p[1]/text()").toString()));
        // 房源年代
        page.putField(FieldNameConstant.BUILD_YEAR, WoaiwojiaHouseInfoExtractUtil.getBuildYear(page.getHtml().xpath
                ("/html/body/div[3]/div[2]/div[2]/div[2]/ul/li[6]/text()").toString()));

        // 配备电梯初始化
        page.putField(FieldNameConstant.HAS_LIFT, 0);
        // 产权年限初始化
        page.putField(FieldNameConstant.PROPERTY_RIGHT_YEAR, 0);

        // 户型
        String houseStruct = page.getHtml().xpath
                ("/html/body/div[3]/div[3]/div[3]/div[1]/div/div[2]/ul/li[1]/span/text()").toString();
        // 几室
        page.putField(FieldNameConstant.ROOM_COUNT, WoaiwojiaHouseInfoExtractUtil.getRoomCount(houseStruct));
        // 几厅
        page.putField(FieldNameConstant.HALL_COUNT, WoaiwojiaHouseInfoExtractUtil.getHallCount(houseStruct));
        // 几卫
        page.putField(FieldNameConstant.TOILET_COUNT, WoaiwojiaHouseInfoExtractUtil.getToiletCount(houseStruct));

        // 挂牌时间
        page.putField(FieldNameConstant.LIST_TIME, page.getHtml().xpath
                ("/html/body/div[3]/div[3]/div[3]/div[1]/div/div[2]/ul/li[5]/span/text()").toString()
                .replace("-", ""));
    }
}
