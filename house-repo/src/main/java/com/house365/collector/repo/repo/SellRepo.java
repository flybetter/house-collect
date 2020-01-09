package com.house365.collector.repo.repo;

import com.house365.collector.base.entity.Sell;
import org.springframework.data.repository.CrudRepository;

public interface SellRepo extends CrudRepository<Sell, Long> {

    Sell findByCityCodeAndPlatformIdAndHouseId(String cityCode, int platformId, String houseId);
}
