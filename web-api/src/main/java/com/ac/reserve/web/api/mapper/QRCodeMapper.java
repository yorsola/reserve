package com.ac.reserve.web.api.mapper;

import com.ac.reserve.web.api.po.QRCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface QRCodeMapper extends BaseMapper<QRCode> {

    QRCode findByTempKeyAndValid(@Param("key") String key,@Param("valid") Integer valid);
}