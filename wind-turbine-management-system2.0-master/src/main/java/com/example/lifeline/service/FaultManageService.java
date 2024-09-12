package com.example.lifeline.service;

import com.example.lifeline.entity.FaultManage;
import com.example.lifeline.service.baseservice.BaseService;
import com.example.lifeline.utils.result.Result;

public interface FaultManageService extends BaseService<FaultManage> {

    // 获取所有故障信息
    Result getAllInfo(int pageNum);

    // 获取某一个故障的信息(根据机组编号)
    Result getInfo(String turbineId);

    // 获取所有故障位置一级的故障信息
    Result getAllFaultOne();

    // 获取所有故障位置二级的故障信息
    Result getAllFaultTwo(String faultLocationOne);

    // 获取所有排查项目(数量越多表示越需要重点排查)
    Result getAllCheckItems();

    // 统计故障类型
    Result getAllFaultType();

    // 获取某个故障导致的停机维护时间（为维修人员所用）
    Result getMaintenanceTime(int faultId);

    // 添加故障
    Result addFault(FaultManage faultManage);

    // 查询所有未被维修的设备
    Result getOneInfo(int pageNum);

    // 获取单个未安排维修设备的信息
    Result getOneNoInfo(String turbineId);

    // 查询所有未完成维修的设备
    Result getTwoInfo(int pageNum);

    // 获取单个未完成维修设备的信息
    Result getOneNonInfo(String turbineId);

    // 更新维修时间
    Result updateFaultInfo(FaultManage faultManage);

    // 更新维修完成时间
    Result updateOkFaultInfo(FaultManage faultManage);

    // 获取备件消耗
    Result getAllParts();

    // 获取经济消耗
    Result getEconomyConsume() throws Exception;

    // 获取时间戳(毫秒级)
    double getTime(String TIME) throws Exception;
}
