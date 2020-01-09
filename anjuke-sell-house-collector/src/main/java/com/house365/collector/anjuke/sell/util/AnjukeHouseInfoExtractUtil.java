package com.house365.collector.anjuke.sell.util;

import com.house365.collector.base.util.BaseHouseInfoExtractUtil;

/**
 * 房源信息提取工具类
 */
public class AnjukeHouseInfoExtractUtil extends BaseHouseInfoExtractUtil {

    public static float getBuildArea(String src) {

        return Float.valueOf(src.replace("平米", ""));
    }
}
