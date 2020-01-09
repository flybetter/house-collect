package com.house365.collector.base.enumeration;

/**
 * 平台枚举类
 */
public enum PlatformEnum {

    BEIKE("贝壳", 1), LIANJIA("链家", 2), ANJUKE("安居客", 3), CENTURY21("21世纪不动产", 4), WOAIWOJIA("我爱我家", 5), HOUSE365
            ("三六五", 6), WUBA("58", 7), GANJI("赶集", 8);

    private String name;
    private int id;

    private PlatformEnum(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static PlatformEnum getEnum(int code) {
        for (PlatformEnum value : PlatformEnum.values()) {
            if (value.getId() == code) {
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
