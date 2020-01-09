package com.house365.collector.base.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 房源信息提取工具类
 */
public class LianjiaBeikeHouseInfoExtractUtil extends BaseHouseInfoExtractUtil {

    /**
     * 获取户型结构
     *
     * @param src
     * @return
     */
    public static String getHouseStructure(String src) {
        if (StringUtils.isNotBlank(src)) {
            int indexOfSlash = src.indexOf("/");
            if (indexOfSlash != -1) {
                return src.substring(0, indexOfSlash);
            } else {
                return src;
            }
        }
        return null;
    }

    /**
     * 获取装修情况
     *
     * @param src
     * @return
     */
    public static String getDecoration(String src) {
        if (StringUtils.isNotBlank(src)) {
            int indexOfSlash = src.indexOf("/");
            if (indexOfSlash != -1) {
                return src.substring(indexOfSlash + 1);
            } else {
                return src;
            }
        }
        return null;
    }

    /**
     * 获取建筑面积
     *
     * @param src
     * @return
     */
    public static float getBuildArea(String src) {
        if (StringUtils.isNotBlank(src)) {
            int indexOfSquareMeter = src.indexOf("平米");
            if (indexOfSquareMeter != -1) {
                return Float.valueOf(src.substring(0, indexOfSquareMeter));
            }
        }
        return 0;
    }
}
