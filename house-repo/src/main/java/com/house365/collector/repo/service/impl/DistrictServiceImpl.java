package com.house365.collector.repo.service.impl;

import com.house365.collector.base.entity.District;
import com.house365.collector.base.vo.DistrictVO;
import com.house365.collector.repo.repo.DistrictRepo;
import com.house365.collector.repo.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictRepo districtRepo;

    @Override
    public District saveDistrict(District district) {

        District rowDistrict = districtRepo.save(district);
        return rowDistrict;
    }

    @Override
    public List<District> saveDistricts(List<DistrictVO> districtVOs) {

        List<District> districts = new ArrayList<>(districtVOs.size());

        for (DistrictVO districtVO : districtVOs) {
            District district = saveDistrict(districtVO.buildEntity());
            districts.add(district);
        }
        return districts;
    }

}
