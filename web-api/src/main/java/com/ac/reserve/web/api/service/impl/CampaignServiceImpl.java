package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.web.api.entity.CampaignEntity;
import com.ac.reserve.web.api.mapper.CampaignMapper;
import com.ac.reserve.web.api.service.ICampaignService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2019-11-04
 */
@Service
public class CampaignServiceImpl extends ServiceImpl<CampaignMapper, CampaignEntity> implements ICampaignService {
	
	private static final Logger logger = LoggerFactory.getLogger(CampaignServiceImpl.class);
	
	@Autowired
	private CampaignMapper campaignMapper;
	
}
