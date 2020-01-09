package com.house365.collector.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 区属板块源表
 */
@Entity
@Table(name = "district")
public class District extends BaseEntity {

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
     * url
     */
    @Column(name = "url", nullable = false)
    private String url;

    public District(String cityCode, int platformId, String district, String subDistrict, String url) {
        this.cityCode = cityCode;
        this.platformId = platformId;
        this.district = district;
        this.subDistrict = subDistrict;
        this.url = url;
    }

    protected District() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
