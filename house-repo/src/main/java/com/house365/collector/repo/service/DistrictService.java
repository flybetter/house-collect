package com.house365.collector.repo.service;

import com.house365.collector.base.entity.District;
import com.house365.collector.base.vo.DistrictVO;

import java.util.List;

public interface DistrictService {

    District saveDistrict(District district);

    List<District> saveDistricts(List<DistrictVO> districtVOs);
}
