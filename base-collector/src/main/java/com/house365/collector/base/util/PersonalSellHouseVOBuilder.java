package com.house365.collector.base.util;

import com.house365.collector.base.constant.FieldNameConstant;
import com.house365.collector.base.vo.PersonalSellHouseVO;
import us.codecraft.webmagic.ResultItems;

public class PersonalSellHouseVOBuilder {

    /**
     * 构造个人房源二手房VO
     *
     * @param resultItems
     * @return
     */
    public static PersonalSellHouseVO build(ResultItems resultItems) {

        PersonalSellHouseVO personalSellHouse = new PersonalSellHouseVO();
        // 城市代码
        personalSellHouse.setCityCode(resultItems.get(FieldNameConstant.CITY_CODE));
        // 平台id
        personalSellHouse.setPlatformId(resultItems.get(FieldNameConstant.PLATFORM_ID));
        // 房源id
        personalSellHouse.setHouseId(resultItems.get(FieldNameConstant.HOUSE_ID));
        // url
        personalSellHouse.setUrl(resultItems.get(FieldNameConstant.URL));
        // 房源标题
        personalSellHouse.setTitle(resultItems.get(FieldNameConstant.TITLE));
        // 区属
        personalSellHouse.setDistrict(resultItems.get(FieldNameConstant.DISTRICT));
        // 板块
        personalSellHouse.setSubDistrict(resultItems.get(FieldNameConstant.SUB_DISTRICT));
        // 所属小区
        personalSellHouse.setBlockName(resultItems.get(FieldNameConstant.BLOCK_NAME));
        // 所属小区id
        personalSellHouse.setBlockId(resultItems.get(FieldNameConstant.BLOCK_ID));
        // 房源总价
        personalSellHouse.setTotalPrice(resultItems.get(FieldNameConstant.TOTAL_PRICE));
        // 房源单价
        personalSellHouse.setUnitPrice(resultItems.get(FieldNameConstant.UNIT_PRICE));
        // 几室
        personalSellHouse.setRoomCount(resultItems.get(FieldNameConstant.ROOM_COUNT));
        // 几厅
        personalSellHouse.setHallCount(resultItems.get(FieldNameConstant.HALL_COUNT));
        // 几卫
        personalSellHouse.setToiletCount(resultItems.get(FieldNameConstant.TOILET_COUNT));
        // 房源总楼层
        personalSellHouse.setTotalFloor(resultItems.get(FieldNameConstant.TOTAL_FLOOR));
        // 当前楼层
        personalSellHouse.setFloorCode(resultItems.get(FieldNameConstant.FLOOR_CODE));
        // 房源朝向
        personalSellHouse.setForward(resultItems.get(FieldNameConstant.FORWARD));
        // 房源装修
        personalSellHouse.setDecoration(resultItems.get(FieldNameConstant.DECORATION));
        // 建筑面积
        personalSellHouse.setBuildArea(resultItems.get(FieldNameConstant.BUILD_AREA));
        // 房源年代
        personalSellHouse.setBuildYear(resultItems.get(FieldNameConstant.BUILD_YEAR));
        // 产权年限
        personalSellHouse.setPropertyRightYear(resultItems.get(FieldNameConstant.PROPERTY_RIGHT_YEAR));
        // 电话
        personalSellHouse.setPhone(resultItems.get(FieldNameConstant.PHONE));
        // 房源描述
        personalSellHouse.setDesc(resultItems.get(FieldNameConstant.DESC));
        // 房源图片
        personalSellHouse.setImg(resultItems.get(FieldNameConstant.IMG));

        return personalSellHouse;
    }
}
