package com.house365.collector.base.enumeration;

/**
 * 数据类型枚举类
 */
public enum DataTypeEnum {

    DISTRICT("区属", 1), SELL("二手房真房源", 2), PERSONAL_SELL("二手房个人房源", 3);

    private String name;
    private int code;

    private DataTypeEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static DataTypeEnum getEnum(int code) {
        for (DataTypeEnum value : DataTypeEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
