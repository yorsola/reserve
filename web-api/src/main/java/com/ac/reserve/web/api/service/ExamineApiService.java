package com.ac.reserve.web.api.service;

import com.ac.reserve.web.api.dto.BillRequestDTO;
import com.alibaba.fastjson.JSONObject;

public interface ExamineApiService {

    /**
     * 请求备审
     * @param billRequestDTO
     * @return 备审 ID
     */
    JSONObject applyExamine(BillRequestDTO billRequestDTO);

    /**
     * 备审结果查询
     * @param examineId 备审 ID
     * @return
     */
    JSONObject checkExamineResult(String examineId);
}
