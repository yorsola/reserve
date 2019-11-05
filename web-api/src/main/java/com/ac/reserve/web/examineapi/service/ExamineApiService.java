package com.ac.reserve.web.examineapi.service;

import com.ac.reserve.web.api.dto.ReserveBillRequestDTO;
import com.alibaba.fastjson.JSONObject;

public interface ExamineApiService {

    /**
     * 请求备审
     * @param reserveBillRequestDTO
     * @return 备审 ID
     */
    JSONObject applyExamine(ReserveBillRequestDTO reserveBillRequestDTO);

    /**
     * 备审结果查询
     * @param examineId 备审 ID
     * @return
     */
    JSONObject checkExamineResult(String examineId);
}
