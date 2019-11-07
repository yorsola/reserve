package com.ac.reserve.web.api.service;

import com.ac.reserve.web.api.po.Bill;

import java.util.Map;

public interface PdfService {

    /**
     * 获取生成的PDF比特数据
     * @param contents  内容
     * @param idCare    身份证
     * @param data      键值对
     * @return
     */
    public byte[] getPdfByte(String contents, String idCare, Map<String, String> data);
}
