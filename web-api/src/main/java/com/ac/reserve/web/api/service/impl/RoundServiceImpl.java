package com.ac.reserve.web.api.service.impl;

import com.ac.reserve.web.api.mapper.RoundMapper;
import com.ac.reserve.web.api.po.Round;
import com.ac.reserve.web.api.service.RoundService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoundServiceImpl extends ServiceImpl<RoundMapper, Round> implements RoundService {

    private static final Logger logger = LoggerFactory.getLogger(RoundServiceImpl.class);

    @Autowired
    private RoundMapper roundMapper;

}
