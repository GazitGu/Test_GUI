package com.em.service;

import com.em.model.RiskSetting;
import org.springframework.stereotype.Component;

/**
 * Created by guchong on 5/22/2018.
 */
@Component
public class RiskSettingServiceImpl implements RiskSettingService {

    private final RiskRepository riskRepository;

    public RiskSettingServiceImpl(RiskRepository riskRepository){
        this.riskRepository = riskRepository;
    }

    @Override
    public RiskSetting save(RiskSetting riskSetting) {
        return riskRepository.save(riskSetting);
    }

    @Override
    public RiskSetting findByRiskId(int riskId) {
        return riskRepository.findByRiskId(riskId);
    }


}
