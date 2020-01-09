package com.house365.collector.repo.repo;

import com.house365.collector.base.entity.Block;
import org.springframework.data.repository.CrudRepository;

public interface BlockRepo extends CrudRepository<Block, Long> {

    Block findByCityCodeAndPlatformIdAndBlockId(String cityCode, int platformId, String blockId);
}
