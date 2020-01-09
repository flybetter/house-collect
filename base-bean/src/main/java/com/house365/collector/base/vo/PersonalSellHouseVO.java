package com.house365.collector.base.vo;

import java.util.List;

/**
 * 个人房源二手房源VO
 */
public class PersonalSellHouseVO extends SellHouseVO {

    private static final long serialVersionUID = -7979810077754231826L;

    private String phone;

    private String desc;

    private List<String> img;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}
