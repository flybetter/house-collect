package com.house365.collector.base.enumeration;

/**
 * 楼层枚举类
 */
public enum FloorEnum {

    LOW("低",1),MIDDLE("中",2),HIGH("高",3);

    private String name;
    private int code;

    private FloorEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static FloorEnum getEnum(int code) {
        for (FloorEnum value : FloorEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
