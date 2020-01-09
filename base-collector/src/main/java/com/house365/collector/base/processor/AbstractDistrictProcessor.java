package com.house365.collector.base.processor;

import com.house365.collector.base.constant.FieldNameConstant;
import com.house365.collector.base.enumeration.DataTypeEnum;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.processor.PageProcessor;

public abstract class AbstractDistrictProcessor implements PageProcessor {

    public String cityCode;

    public String cityRootUrl;

    public void setDataType(Page page) {

        // 数据类型
        page.putField(FieldNameConstant.DATA_TYPE, DataTypeEnum.DISTRICT.getCode());
    }
}
