package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.web.api.entity.BillEntity;
import com.ac.reserve.web.api.mapper.BillMapper;
import com.ac.reserve.web.api.service.IBillService;
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
public class BillServiceImpl extends ServiceImpl<BillMapper, BillEntity> implements IBillService {
	
	private static final Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);
	
	@Autowired
	private BillMapper billMapper;
	
}
