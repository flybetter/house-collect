package com.house365.collector.base.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 房源源表
 */
@Entity
@Table(name = "crawl_sell")
public class Sell extends BaseEntity {

    /**
     * 城市代码
     */
    @Column(name = "city_code", nullable = false)
    private String cityCode;

    /**
     * 平台id
     */
    @Column(name = "platform_id", nullable = false)
    private int platformId;

    /**
     * 房源id
     */
    @Column(name = "house_id", nullable = false)
    private String houseId;

    /**
     * url
     */
    @Column(name = "url", nullable = false)
    private String url;

    /**
     * 房源标题
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 区属
     */
    @Column(name = "district", nullable = false)
    private String district;

    /**
     * 板块
     */
    @Column(name = "sub_district", nullable = false)
    private String subDistrict;

    /**
     * 所属小区
     */
    @Column(name = "block_name", nullable = false)
    private String blockName;

    /**
     * 所属小区id
     */
    @Column(name = "block_id", nullable = false)
    private String blockId;

    /**
     * 房源总价
     */
    @Column(name = "total_price", nullable = false)
    private float totalPrice;

    /**
     * 房源单价
     */
    @Column(name = "unit_price", nullable = false)
    private float unitPrice;

    /**
     * 几室
     */
    @Column(name = "room_count", nullable = false)
    private int roomCount;

    /**
     * 几厅
     */
    @Column(name = "hall_count", nullable = false)
    private int hallCount;

    /**
     * 几卫
     */
    @Column(name = "toilet_count")
    private int toiletCount;

    /**
     * 房源总楼层
     */
    @Column(name = "total_floor", nullable = false)
    private int totalFloor;

    /**
     * 当前楼层
     */
    @Column(name = "floor_code", nullable = false)
    private int floorCode;

    /**
     * 房源朝向
     */
    @Column(name = "forward")
    private String forward;

    /**
     * 房源装修
     */
    @Column(name = "decoration")
    private String decoration;

    /**
     * 建筑面积
     */
    @Column(name = "build_area", nullable = false)
    private float buildArea;

    /**
     * 房源年代
     */
    @Column(name = "build_year")
    private int buildYear;

    /**
     * 配备电梯
     */
    @Column(name = "has_lift")
    private int hasLift;

    /**
     * 产权年限
     */
    @Column(name = "property_right_year")
    private int propertyRightYear;

    /**
     * 挂牌时间
     */
    @Column(name = "list_time")
    private String listTime;

    public Sell(String cityCode, int platformId, String houseId, String url, String title, String district, String
            subDistrict, String blockName, String blockId, float totalPrice, float unitPrice, int roomCount, int
            hallCount, int toiletCount, int totalFloor, int floorCode, String forward, String decoration, float
            buildArea, int buildYear, int hasLift, int propertyRightYear, String listTime) {
        this.cityCode = cityCode;
        this.platformId = platformId;
        this.houseId = houseId;
        this.url = url;
        this.title = title;
        this.district = district;
        this.subDistrict = subDistrict;
        this.blockName = blockName;
        this.blockId = blockId;
        this.totalPrice = totalPrice;
        this.unitPrice = unitPrice;
        this.roomCount = roomCount;
        this.hallCount = hallCount;
        this.toiletCount = toiletCount;
        this.totalFloor = totalFloor;
        this.floorCode = floorCode;
        this.forward = forward;
        this.decoration = decoration;
        this.buildArea = buildArea;
        this.buildYear = buildYear;
        this.hasLift = hasLift;
        this.propertyRightYear = propertyRightYear;
        this.listTime = listTime;
    }

    protected Sell() {
    }

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

    @Override
    public String toString() {
        return "Sell{" +
                "cityCode='" + cityCode + '\'' +
                ", platformId=" + platformId +
                ", houseId='" + houseId + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", district='" + district + '\'' +
                ", subDistrict='" + subDistrict + '\'' +
                ", blockName='" + blockName + '\'' +
                ", blockId='" + blockId + '\'' +
                ", totalPrice=" + totalPrice +
                ", unitPrice=" + unitPrice +
                ", roomCount=" + roomCount +
                ", hallCount=" + hallCount +
                ", toiletCount=" + toiletCount +
                ", totalFloor=" + totalFloor +
                ", floorCode=" + floorCode +
                ", forward='" + forward + '\'' +
                ", decoration='" + decoration + '\'' +
                ", buildArea=" + buildArea +
                ", buildYear=" + buildYear +
                ", hasLift=" + hasLift +
                ", propertyRightYear=" + propertyRightYear +
                ", listTime='" + listTime + '\'' +
                '}';
    }
}
