package com.house365.collector.base.util;

import com.house365.collector.base.enumeration.FloorEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.*;

/**
 * 房源信息提取工具类
 */
public class BaseHouseInfoExtractUtil {

    public static Pattern number_pattern = Pattern.compile("\\d+");


    /**
     * 获取float数值
     *
     * @param src
     * @return
     */
    public static float getFloatValue(String src) {
        return Float.valueOf(src);
    }

    /**
     * 获取单价
     *
     * @param src
     * @return
     */
    public static float getUnitPrice(String src) {
        return Float.valueOf(StringUtils.trim(src.replace("元/m²", "").replace("元/平米", "").replace(" 元/平", "")));
    }

    /**
     * 获取楼层编码
     *
     * @param src
     * @return
     */
    public static int getFloorCode(String src) {

        int floorCode = 0;
        if (StringUtils.isNotBlank(src)) {

            if (StringUtils.isNotBlank(src)) {

                if (StringUtils.contains(src, FloorEnum.LOW.getName())) {
                    floorCode = FloorEnum.LOW.getCode();
                } else if (StringUtils.contains(src, FloorEnum.MIDDLE.getName())) {
                    floorCode = FloorEnum.MIDDLE.getCode();
                } else if (StringUtils.contains(src, FloorEnum.HIGH.getName())) {
                    floorCode = FloorEnum.HIGH.getCode();
                }
            }
            if (StringUtils.isNotBlank(src)) {

                if (StringUtils.contains(src, "底")) {
                    floorCode = FloorEnum.LOW.getCode();
                } else if (StringUtils.contains(src, "顶")) {
                    floorCode = FloorEnum.HIGH.getCode();
                }
            }
        }
        return floorCode;
    }

    /**
     * 获取总楼层
     *
     * @param src
     * @return
     */
    public static int getTotalFloor(String src) {


        int totalFloor = 0;
        try {

            Matcher m = number_pattern.matcher(src);
            if (m.find()) {
                totalFloor = Integer.valueOf(StringUtils.getDigits(src));
            }
        } catch (Exception e) {

        }
        return totalFloor;
    }

    /**
     * 获取建筑年代
     *
     * @param src
     * @return
     */
    public static int getBuildYear(String src) {
        int buildYear = 0;
        try {
            buildYear = Integer.valueOf(StringUtils.getDigits(src));
        } catch (Exception e) {

        }
        return buildYear;
    }

    /**
     * 获取配备电梯
     *
     * @param src
     * @return
     */
    public static int getHasLift(String src) {
        int hasLift = 0;
        try {
            if (StringUtils.equals("有", src)) {
                hasLift = 1;
            }
        } catch (Exception e) {

        }
        return hasLift;
    }

    /**
     * 室
     *
     * @param src
     * @return
     */
    public static int getRoomCount(String src) {
        if (StringUtils.isNotBlank(src)) {
            int indexOfRoom = src.indexOf("室");
            if (indexOfRoom != -1 && indexOfRoom >= 1) {
                return Integer.valueOf(src.substring(indexOfRoom - 1, indexOfRoom));
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
            if (indexOfHall != -1 && indexOfHall >= 1) {
                return Integer.valueOf(src.substring(indexOfHall - 1, indexOfHall));
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
                return Integer.valueOf(src.substring(indexOfToilet - 1, indexOfToilet));
            }
        }
        return 0;
    }


    public static float getPatternFloatValue(String src) {
        float value = 0;
        try {
            Matcher m = number_pattern.matcher(src);
            if (m.find()) {
                value = Float.valueOf(StringUtils.getDigits(m.group(1)));
            }
        } catch (Exception e) {

        }
        return value;
    }


    public static void main(String[] args) {
        Pattern number_pattern = Pattern.compile("\\d+");

        String src = "28079元/平米";

        Matcher m = number_pattern.matcher(src);
        if (m.find()) {
            System.out.println(Integer.valueOf(StringUtils.getDigits(src)));
        }
    }
}
