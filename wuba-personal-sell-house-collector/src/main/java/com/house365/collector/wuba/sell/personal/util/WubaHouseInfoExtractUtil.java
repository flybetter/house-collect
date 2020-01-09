package com.house365.collector.wuba.sell.personal.util;

import com.house365.collector.base.util.BaseHouseInfoExtractUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.fontbox.ttf.CmapSubtable;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import us.codecraft.webmagic.Page;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 房源信息提取工具类
 */
public class WubaHouseInfoExtractUtil extends BaseHouseInfoExtractUtil {

    private static final Logger logger = LoggerFactory.getLogger(WubaHouseInfoExtractUtil.class);

    /**
     * 获取建筑面积
     *
     * @param src
     * @return
     */
    public static float getBuildArea(String src) {
        if (StringUtils.isNotBlank(src)) {
            int indexOfSquareMeter = src.indexOf("平");
            if (indexOfSquareMeter != -1) {
                return Float.valueOf(src.substring(0, indexOfSquareMeter));
            }
        }
        return 0;
    }

    /**
     * 获取解码后的字符串
     *
     * @param page
     * @param encodedStr
     * @return
     */
    public static String getDecodedStr(Page page, String encodedStr) {

        String html = page.toString();
        String[] htmlArr = html.split("base64,");
        String base64 = "";
        if (htmlArr.length > 1) {
            htmlArr = htmlArr[1].split("'");
            if (htmlArr.length > 1) {
                base64 = htmlArr[0];
                if (StringUtils.isNotBlank(base64)) {
                    List<String> dic = getDic(base64);
                    char[] encodedCharArray = encodedStr.toCharArray();
                    for (int index = 0; index < encodedCharArray.length; index++) {
                        for (int dicIndex = 0; dicIndex < dic.size(); dicIndex++) {
                            if (encodedCharArray[index] == dic.get(dicIndex).toCharArray()[0]) {
                                encodedCharArray[index] = (char) (dicIndex + 48);
                            }
                        }
                    }
                    String decodedStr = new String(encodedCharArray);
                    return decodedStr;
                }
            }
        }
        return encodedStr;
    }

    private static List<String> getDic(String base64) {

        List<String> dic = new ArrayList<>();
        BASE64Decoder decoder = new BASE64Decoder();
        TTFParser parser = new TTFParser();
        try {

            byte[] bytes = decoder.decodeBuffer(base64);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            TrueTypeFont ttf = parser.parse(inputStream);
            if (ttf != null && ttf.getCmap() != null && ttf.getCmap().getCmaps() != null
                    && ttf.getCmap().getCmaps().length > 0) {
                CmapSubtable[] tables = ttf.getCmap().getCmaps();
                CmapSubtable table = tables[0];
                for (int i = 1; i <= 10; i++) {

                    dic.add(String.valueOf((char) (int) (table.getCharacterCode(i))));
                }
            }
        } catch (IOException e) {

            logger.error(e.getMessage(), e);
        }
        return dic;
    }

    /**
     * 获取建筑年代
     *
     * @param src
     * @return
     */
    public static int getBuildYear(String src) {
        int buildYear = 0;
        if (src.contains("暂无信息")) {
            return buildYear;
        }
        try {
            buildYear = Integer.valueOf(StringUtils.getDigits(src));
        } catch (Exception e) {

        }
        return buildYear;
    }
}
