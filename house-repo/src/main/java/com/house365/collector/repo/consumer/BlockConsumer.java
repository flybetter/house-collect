package com.house365.collector.repo.consumer;

import com.alibaba.fastjson.JSON;
import com.house365.collector.base.constant.MQConstant;
import com.house365.collector.base.vo.BlockVO;
import com.house365.collector.repo.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class BlockConsumer {

    @Autowired
    private BlockService blockService;

    @JmsListener(destination = MQConstant.BLOCK_QUEUE)
    public void processMessage(String message) {

        BlockVO blockVO = JSON.parseObject(message, BlockVO.class);
        blockService.processBlock(blockVO);
    }
}