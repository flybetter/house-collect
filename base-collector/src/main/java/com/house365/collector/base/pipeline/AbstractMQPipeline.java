package com.house365.collector.base.pipeline;

import com.alibaba.fastjson.JSON;
import com.house365.collector.base.constant.FieldNameConstant;
import com.house365.collector.base.enumeration.DataTypeEnum;
import com.house365.collector.base.util.DistrictVOBuilder;
import com.house365.collector.base.util.PersonalSellHouseVOBuilder;
import com.house365.collector.base.util.SellHouseVOBuilder;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.Closeable;
import java.io.IOException;

import static com.house365.collector.base.enumeration.DataTypeEnum.*;

public abstract class AbstractMQPipeline implements Pipeline, Closeable {

    /**
     * Process extracted results.
     *
     * @param resultItems resultItems
     * @param task        task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {

        if (resultItems.get(FieldNameConstant.DATA_TYPE) != null) {

            int dataTypeCode = resultItems.get(FieldNameConstant.DATA_TYPE);
            DataTypeEnum dataType = getEnum(dataTypeCode);
            Object pageDataVo = null;

            // 根据数据类型分别构造VO
            switch (dataType) {
                case DISTRICT:
                    pageDataVo = DistrictVOBuilder.build(resultItems);
                    break;
                case SELL:
                    pageDataVo = SellHouseVOBuilder.build(resultItems);
                    break;
                case PERSONAL_SELL:
                    pageDataVo = PersonalSellHouseVOBuilder.build(resultItems);
                    break;
            }

            if (pageDataVo != null) {
                String msg = JSON.toJSONString(pageDataVo);
                sendToMQ(msg);
            }
        }
    }

    protected abstract void sendToMQ(String msg);


}
