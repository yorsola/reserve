package com.ac.reserve.web.api.mapper;

import com.ac.reserve.web.api.dto.BillPdfInfoDTO;
import com.ac.reserve.web.api.po.Bill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface BillMapper extends BaseMapper<Bill> {

    BillPdfInfoDTO selectBillPdfInfo(@Param("code") String code);
}