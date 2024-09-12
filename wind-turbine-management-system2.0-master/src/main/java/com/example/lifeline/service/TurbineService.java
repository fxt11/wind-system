package com.example.lifeline.service;

import com.example.lifeline.entity.Turbine;
import com.example.lifeline.service.baseservice.BaseService;
import com.example.lifeline.utils.result.Result;

public interface TurbineService extends BaseService<Turbine> {
    // 获取所有机组信息
    Result getAllTurbines(int pageNum);

    // 查询某个机组信息
    Result getTurbine(String windFieldName, String turbineId);

    // 新增一个机组
    Result addTurbine(Turbine turbine, int pageNum) throws Exception;

    // 删除一个机组
    Result deleteTurbine(String windFieldName, String turbineId);

    // 修改某一个机组
    Result editTurbine(Turbine turbine, int pageNum);

    // 获取所有正常机组
    Result getAllNormalTurbines(int pageNum);

    // 获取所有故障机组
    Result getAllFaultTurbines(int pageNum);
}
