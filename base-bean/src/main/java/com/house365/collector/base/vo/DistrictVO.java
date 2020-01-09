package com.house365.collector.base.vo;

import com.house365.collector.base.entity.District;

/**
 * 区域VO
 */
public class DistrictVO extends BaseVO {

    private static final long serialVersionUID = 6719265978289441304L;

    private String cityCode;

    private int platformId;

    private String district;

    private String subDistrict;

    private String url;

    public DistrictVO(String cityCode, int platformId, String district, String subDistrict, String url) {
        this.cityCode = cityCode;
        this.platformId = platformId;
        this.district = district;
        this.subDistrict = subDistrict;
        this.url = url;
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

    public District buildEntity() {
        District district = new District(cityCode, platformId, this.district, subDistrict, url);
        return district;
    }
}
