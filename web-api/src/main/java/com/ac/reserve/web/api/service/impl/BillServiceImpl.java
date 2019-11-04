package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.web.api.po.Bill;
import com.ac.reserve.web.api.mapper.BillMapper;
import com.ac.reserve.web.api.service.BillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

    private static final Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    private BillMapper billMapper;

}
