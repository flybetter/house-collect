package com.house365.collector.repo.consumer;

import com.alibaba.fastjson.JSON;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.vo.SellHouseVO;
import com.house365.collector.repo.service.SellHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SellHouseConsumer {

    @Autowired
    private SellHouseService sellHouseService;

    @JmsListener(destination = MQConstant.SELL_HOUSE_QUEUE)
    public void processMessage(String message) {

        SellHouseVO sellHouse = JSON.parseObject(message, SellHouseVO.class);
        sellHouseService.processSellHouse(sellHouse);
    }
}