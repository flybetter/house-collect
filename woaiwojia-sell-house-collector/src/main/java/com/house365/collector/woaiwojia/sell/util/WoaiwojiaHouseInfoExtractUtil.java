package com.house365.collector.woaiwojia.sell.util;

import com.house365.collector.base.util.BaseHouseInfoExtractUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 房源信息提取工具类
 */
public class WoaiwojiaHouseInfoExtractUtil extends BaseHouseInfoExtractUtil {

    /**
     * 室
     *
     * @param src
     * @return
     */
    public static int getRoomCount(String src) {
        if (StringUtils.isNotBlank(src)) {
            int indexOfRoom = src.indexOf("室");
            String room = src.substring(indexOfRoom - 1, indexOfRoom);
            if (StringUtils.contains(room, "多")) {

                return 3;
            } else {

                return Integer.valueOf(room);
            }
        }
        return 0;
    }

    /**
     * 厅
     *
     * @param src
     * @return
     */
    public static int getHallCount(String src) {
        if (StringUtils.isNotBlank(src)) {
            int indexOfHall = src.indexOf("厅");
            String hall = src.substring(indexOfHall - 1, indexOfHall);
            if (StringUtils.contains(hall, "多")) {

                return 2;
            } else {

                return Integer.valueOf(hall);
            }
        }
        return 0;
    }

    /**
     * 卫
     *
     * @param src
     * @return
     */
    public static int getToiletCount(String src) {

        if (StringUtils.isNotBlank(src)) {

            int indexOfToilet = src.indexOf("卫");
            if (indexOfToilet != -1 && indexOfToilet >= 1) {
                String toilet = src.substring(indexOfToilet - 1, indexOfToilet);
                if (StringUtils.contains(toilet, "多")) {

                    return 2;
                } else {

                    return Integer.valueOf(toilet);
                }
            }
        }
        return 0;
    }
}
