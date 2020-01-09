package com.house365.collector.repo.service.impl;

import com.house365.collector.base.entity.Sell;
import com.house365.collector.base.vo.BlockVO;
import com.house365.collector.base.vo.SellHouseVO;
import com.house365.collector.repo.producer.BlockCompareProducer;
import com.house365.collector.repo.producer.BlockProducer;
import com.house365.collector.repo.producer.HandlerProducer;
import com.house365.collector.repo.repo.SellRepo;
import com.house365.collector.repo.service.SellHouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellHouseServiceImpl implements SellHouseService {

    private static final Logger logger = LoggerFactory.getLogger(SellHouseServiceImpl.class);

    @Autowired
    private BlockProducer blockProducer;

    @Autowired
    private HandlerProducer handlerProducer;


    @Autowired
    private BlockCompareProducer blockCompareProducer;


    @Autowired
    private SellRepo sellRepo;

    @Override
    public void processSellHouse(SellHouseVO sellHouse) {

        // 提取小区VO
        BlockVO block = new BlockVO(sellHouse.getCityCode(), sellHouse.getPlatformId(), sellHouse.getBlockName(),
                sellHouse.getBlockId());
        // 发送小区VO到MQ
        blockProducer.produceBlock(block);

        // send the block body to the block_compare queue
        blockCompareProducer.produceBlock(block);

        // send the sell body to the handler queue
        handlerProducer.produceHandler(sellHouse);

        // 保存Sell
        saveSell(sellHouse.buildEntity());
    }

    private void saveSell(Sell sell) {

        try {
            sellRepo.save(sell);
        } catch (Exception e) {

            logger.error(sell.toString() + " already exists in database.");
        }
    }
}
