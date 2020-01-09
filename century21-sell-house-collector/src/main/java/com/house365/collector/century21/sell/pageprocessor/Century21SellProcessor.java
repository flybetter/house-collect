package com.house365.collector.century21.sell.pageprocessor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.house365.collector.base.constant.FieldNameConstant;
import com.house365.collector.base.enumeration.PlatformEnum;
import com.house365.collector.base.processor.AbstractSellProcessor;
import com.house365.collector.base.util.BaseHouseInfoExtractUtil;
import com.house365.collector.base.util.LianjiaBeikeHouseInfoExtractUtil;
import com.house365.collector.base.vo.DistrictVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.font.TrueTypeFont;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.example.PatternProcessorExample;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.Selectable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Century21SellProcessor extends AbstractSellProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    Pattern numPattern = Pattern.compile("\\d+");

    Pattern infoPattern = Pattern.compile("\"DetailInfo\":(\\{.*?\\})");

    private String cityCode = "nj";

    public Century21SellProcessor() {
    }

    public Century21SellProcessor(String cityCode) {
        this.cityCode = cityCode;
    }

    @Override
    public void process(Page page) {

        // 设置数据类型
        setDataType(page);
        String url = page.getRequest().getUrl();
        if (page.getHtml().css("span").regex("下一页").match() & !page.getHtml().xpath("//div[@class='no_data_title']").regex(".*未找到符合搜索条件的二手房房源，建议您调整搜索条件再试试.*").match()) {
            Matcher matcher = numPattern.matcher(url);
            if (matcher.find()) {
                Integer num = Integer.parseInt(matcher.group()) + 1;
                page.addTargetRequest(url.replaceFirst("\\d+", num.toString()));
            }
        }

        if (page.getRequest().getUrl().contains("esf")) {
            page.setSkip(true);
        }

        List<String> requests = page.getHtml().css("a.avail_cont", "href").all().stream().map(x -> "https://nj.koofang.com" + x).collect(Collectors.toList());
        page.addTargetRequests(requests);

        Body(page);

    }


    public void Body(Page page) {

        String html = page.getHtml().xpath("//script[@type='text/javascript']").get();

        Matcher info = infoPattern.matcher(html);
        if (info.find()) {

            JSONObject jsonObject = JSONObject.parseObject(info.group(1));

            // 城市代码
            page.putField(FieldNameConstant.CITY_CODE, cityCode);
            // 平台id
            page.putField(FieldNameConstant.PLATFORM_ID, PlatformEnum.CENTURY21.getId());
            // 房源id
            page.putField(FieldNameConstant.HOUSE_ID, jsonObject.get("RealtyId").toString());
            // url
            page.putField(FieldNameConstant.URL, page.getUrl().toString());
            // 房源标题
            page.putField(FieldNameConstant.TITLE, jsonObject.get("RealtyTitle"));
            // 区属
            page.putField(FieldNameConstant.DISTRICT, jsonObject.get("DistrictName"));
            // 板块
            page.putField(FieldNameConstant.SUB_DISTRICT, jsonObject.get("CommunityIndexName"));
            // 所属小区
            page.putField(FieldNameConstant.BLOCK_NAME, jsonObject.get("CommunityName"));
            // 所属小区id
            page.putField(FieldNameConstant.BLOCK_ID, jsonObject.get("CommunityId").toString());
            // 房源总价
            page.putField(FieldNameConstant.TOTAL_PRICE, Float.valueOf(jsonObject.get("SalePrice").toString()));
            // 房源单价
            page.putField(FieldNameConstant.UNIT_PRICE, Float.valueOf(jsonObject.get("SaleUnitPrice").toString()));
            // 房源总楼层
            page.putField(FieldNameConstant.TOTAL_FLOOR, jsonObject.get("FloorsTotal"));
            // 当前楼层
            page.putField(FieldNameConstant.FLOOR_CODE, BaseHouseInfoExtractUtil.getFloorCode(jsonObject.get("FloorTagName").toString()));
            // 房源朝向
            page.putField(FieldNameConstant.FORWARD, jsonObject.get("FaceOrientationName"));
            // 房源装修
            page.putField(FieldNameConstant.DECORATION, jsonObject.get("DecorationLevelName"));
            // 建筑面积
            page.putField(FieldNameConstant.BUILD_AREA, Float.valueOf(jsonObject.get("ConstructionArea").toString()));
            // 房源年代
            page.putField(FieldNameConstant.BUILD_YEAR, jsonObject.get("BuildingYear"));

            // 配备电梯初始化
            page.putField(FieldNameConstant.HAS_LIFT, 0);
            // 产权年限初始化
            page.putField(FieldNameConstant.PROPERTY_RIGHT_YEAR, 0);

            // 挂牌时间
            page.putField(FieldNameConstant.LIST_TIME, jsonObject.get("CommissionDate").toString().replaceAll("-", ""));

            // 几室
            page.putField(FieldNameConstant.ROOM_COUNT, jsonObject.get("RoomNum"));
            // 几厅
            page.putField(FieldNameConstant.HALL_COUNT, jsonObject.get("LivingRoomNum"));
            // 几卫
            page.putField(FieldNameConstant.TOILET_COUNT, 1);

        }

    }

    @Override
    public Site getSite() {
        return site;
    }
}
