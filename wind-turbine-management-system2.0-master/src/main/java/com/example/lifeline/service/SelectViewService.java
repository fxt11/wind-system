package com.example.lifeline.service;

import com.example.lifeline.entity.SelectView;
import com.example.lifeline.service.baseservice.BaseService;
import com.example.lifeline.utils.result.Result;

public interface SelectViewService extends BaseService<SelectView> {

    // 获取所有故障查询信息
    Result getAllInfo(int pageNum);

    // 根据机组编号查询故障信息
    Result selectId(String turbineId);

    // 根据故障名称查询故障信息
    Result selectName(String faultName);

}
