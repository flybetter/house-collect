package com.house365.collector.base.vo;

import com.house365.collector.base.entity.Sell;

/**
 * 二手房源VO
 */
public class SellHouseVO extends BaseVO {

    private static final long serialVersionUID = -2255985470041615439L;

    /**
     * 城市代码
     */
    private String cityCode;

    /**
     * 平台id
     */
    private int platformId;

    /**
     * 房源id
     */
    private String houseId;

    /**
     * url
     */
    private String url;

    /**
     * 房源标题
     */
    private String title;

    /**
     * 区属
     */
    private String district;

    /**
     * 板块
     */
    private String subDistrict;

    /**
     * 所属小区
     */
    private String blockName;

    /**
     * 所属小区id
     */
    private String blockId;

    /**
     * 房源总价
     */
    private float totalPrice;

    /**
     * 房源单价
     */
    private float unitPrice;

    /**
     * 几室
     */
    private int roomCount;

    /**
     * 几厅
     */
    private int hallCount;

    /**
     * 几卫
     */
    private int toiletCount;

    /**
     * 房源总楼层
     */
    private int totalFloor;

    /**
     * 当前楼层
     */
    private int floorCode;

    /**
     * 房源朝向
     */
    private String forward;

    /**
     * 房源装修
     */
    private String decoration;

    /**
     * 建筑面积
     */
    private float buildArea;

    /**
     * 房源年代
     */
    private int buildYear;

    /**
     * 配备电梯
     */
    private int hasLift;

    /**
     * 产权年限
     */
    private int propertyRightYear;

    /**
     * 挂牌时间
     */
    private String listTime;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getHallCount() {
        return hallCount;
    }

    public void setHallCount(int hallCount) {
        this.hallCount = hallCount;
    }

    public int getToiletCount() {
        return toiletCount;
    }

    public void setToiletCount(int toiletCount) {
        this.toiletCount = toiletCount;
    }

    public int getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(int totalFloor) {
        this.totalFloor = totalFloor;
    }

    public int getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(int floorCode) {
        this.floorCode = floorCode;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public float getBuildArea() {
        return buildArea;
    }

    public void setBuildArea(float buildArea) {
        this.buildArea = buildArea;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    public int getHasLift() {
        return hasLift;
    }

    public void setHasLift(int hasLift) {
        this.hasLift = hasLift;
    }

    public int getPropertyRightYear() {
        return propertyRightYear;
    }

    public void setPropertyRightYear(int propertyRightYear) {
        this.propertyRightYear = propertyRightYear;
    }

    public String getListTime() {
        return listTime;
    }

    public void setListTime(String listTime) {
        this.listTime = listTime;
    }

    public Sell buildEntity() {

        Sell sell = new Sell(cityCode, platformId, houseId, url, title, district,
                subDistrict, blockName, blockId, totalPrice, unitPrice, roomCount,
                hallCount, toiletCount, totalFloor, floorCode, forward, decoration,
                buildArea, buildYear, hasLift, propertyRightYear, listTime);
        return sell;
    }
}
