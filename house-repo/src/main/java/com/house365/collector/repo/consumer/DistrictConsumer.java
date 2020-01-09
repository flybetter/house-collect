package com.house365.collector.repo.consumer;

import com.alibaba.fastjson.JSON;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.vo.DistrictVO;
import com.house365.collector.repo.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DistrictConsumer {

    @Autowired
    private DistrictService districtService;

    @JmsListener(destination = MQConstant.DISTRICT_QUEUE)
    public void processMessage(String message) {

        List<DistrictVO> districtVOs = JSON.parseArray(message, DistrictVO.class);
        districtService.saveDistricts(districtVOs);
    }
}