package com.house365.collector.base.util;

import com.house365.collector.base.constant.FieldNameConstant;
import com.house365.collector.base.vo.DistrictVO;
import us.codecraft.webmagic.ResultItems;

import java.util.List;

public class DistrictVOBuilder {

    /**
     * 构造区域VO
     *
     * @param resultItems
     * @return
     */
    public static List<DistrictVO> build(ResultItems resultItems) {

        List<DistrictVO> districts = resultItems.get(FieldNameConstant.DISTRICTS);
        return districts;
    }
}
