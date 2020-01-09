package com.house365.collector.base.util;

import com.house365.collector.base.constant.FieldNameConstant;
import com.house365.collector.base.vo.*;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.ResultItems;

public class SellHouseVOBuilder {

    /**
     * 构造二手房VO
     *
     * @param resultItems
     * @return
     */
    public static SellHouseVO build(ResultItems resultItems) {

        SellHouseVO sellHouse = new SellHouseVO();
        // 城市代码
        sellHouse.setCityCode(resultItems.get(FieldNameConstant.CITY_CODE));
        // 平台id
        sellHouse.setPlatformId(resultItems.get(FieldNameConstant.PLATFORM_ID));
        // 房源id
        sellHouse.setHouseId(resultItems.get(FieldNameConstant.HOUSE_ID));
        // url
        sellHouse.setUrl(resultItems.get(FieldNameConstant.URL));
        // 房源标题
        sellHouse.setTitle(resultItems.get(FieldNameConstant.TITLE));
        // 区属
        sellHouse.setDistrict(resultItems.get(FieldNameConstant.DISTRICT));
        // 板块
        sellHouse.setSubDistrict(resultItems.get(FieldNameConstant.SUB_DISTRICT));
        // 所属小区
        sellHouse.setBlockName(resultItems.get(FieldNameConstant.BLOCK_NAME));
        // 所属小区id
        sellHouse.setBlockId(resultItems.get(FieldNameConstant.BLOCK_ID));
        // 房源总价
        sellHouse.setTotalPrice(resultItems.get(FieldNameConstant.TOTAL_PRICE));
        // 房源单价
        sellHouse.setUnitPrice(resultItems.get(FieldNameConstant.UNIT_PRICE));
        // 几室
        sellHouse.setRoomCount(resultItems.get(FieldNameConstant.ROOM_COUNT));
        // 几厅
        sellHouse.setHallCount(resultItems.get(FieldNameConstant.HALL_COUNT));
        // 几卫
        sellHouse.setToiletCount(resultItems.get(FieldNameConstant.TOILET_COUNT));
        // 房源总楼层
        sellHouse.setTotalFloor(resultItems.get(FieldNameConstant.TOTAL_FLOOR));
        // 当前楼层
        sellHouse.setFloorCode(resultItems.get(FieldNameConstant.FLOOR_CODE));
        // 房源朝向
        sellHouse.setForward(resultItems.get(FieldNameConstant.FORWARD));
        // 房源装修
        sellHouse.setDecoration(resultItems.get(FieldNameConstant.DECORATION));
        // 建筑面积
        sellHouse.setBuildArea(resultItems.get(FieldNameConstant.BUILD_AREA));
        // 房源年代
        sellHouse.setBuildYear(resultItems.get(FieldNameConstant.BUILD_YEAR));
        // 配备电梯
        sellHouse.setHasLift(resultItems.get(FieldNameConstant.HAS_LIFT));
        // 产权年限
        sellHouse.setPropertyRightYear(resultItems.get(FieldNameConstant.PROPERTY_RIGHT_YEAR));
        // 挂牌时间
        sellHouse.setListTime(resultItems.get(FieldNameConstant.LIST_TIME));

        return sellHouse;
    }
}
