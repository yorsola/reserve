package com.ac.reserve.web.api.service;

import com.ac.reserve.web.api.dto.CampaignDTO;
import com.ac.reserve.web.api.po.Campaign;
import com.baomidou.mybatisplus.extension.service.IService;


public interface CampaignService extends IService<Campaign> {
    CampaignDTO getEffectiveCampaign();

}
