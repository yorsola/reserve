package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.web.api.po.Campaign;
import com.ac.reserve.web.api.mapper.CampaignMapper;
import com.ac.reserve.web.api.service.CampaignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class CampaignServiceImpl extends ServiceImpl<CampaignMapper, Campaign> implements CampaignService {

    private static final Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);

    @Autowired
    private CampaignMapper campaignMapper;

}
