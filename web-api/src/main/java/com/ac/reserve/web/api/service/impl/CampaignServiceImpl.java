package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.common.exception.ServiceException;
import com.ac.reserve.web.api.dto.CampaignDTO;
import com.ac.reserve.web.api.mapper.RoundMapper;
import com.ac.reserve.web.api.po.Campaign;
import com.ac.reserve.web.api.mapper.CampaignMapper;
import com.ac.reserve.web.api.po.Round;
import com.ac.reserve.web.api.service.CampaignService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.ac.reserve.common.constant.DataSourceConstant.DATA_SOURCE_VALID;


@Service
public class CampaignServiceImpl extends ServiceImpl<CampaignMapper, Campaign> implements CampaignService {

    private static final Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);

    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private RoundMapper roundMapper;

    @Override
//    @Cacheable(value = "Campaign", key = "'effective_campaign'")
    public CampaignDTO getEffectiveCampaign() {
        QueryWrapper<Campaign> campaignQueryWrapper = new QueryWrapper<>();
        campaignQueryWrapper.eq("valid", DATA_SOURCE_VALID);
        List<Campaign> campaigns = campaignMapper.selectList(campaignQueryWrapper);
        if (campaigns != null && campaigns.size() != 0) {
            CampaignDTO campaignDTO = new CampaignDTO();
            BeanUtils.copyProperties(campaigns.get(0), campaignDTO);
            QueryWrapper<Round> roundQueryWrapper = new QueryWrapper<>();
            roundQueryWrapper.eq("campaign_id", campaignDTO.getId());
            List<Round> rounds = roundMapper.selectList(roundQueryWrapper);
            campaignDTO.setRoundList(rounds);
            return campaignDTO;
        }
        throw new ServiceException("活动不存在.");
    }

}
