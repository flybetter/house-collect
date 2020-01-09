package com.house365.collector.repo.service.impl;

import com.house365.collector.base.entity.Block;
import com.house365.collector.base.vo.BlockVO;
import com.house365.collector.repo.repo.BlockRepo;
import com.house365.collector.repo.service.BlockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockServiceImpl implements BlockService {

    private static final Logger logger = LoggerFactory.getLogger(BlockServiceImpl.class);

    @Autowired
    private BlockRepo blockRepo;

    @Override
    public void processBlock(BlockVO blockVO) {


        Block block = blockVO.buildEntity();
        saveBlock(block);
    }

    private void saveBlock(Block block) {

        try {

            blockRepo.save(block);
        } catch (Exception e) {

            logger.error(block.toString() + " already exists in database.");
        }
    }
}
