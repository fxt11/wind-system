package com.example.lifeline.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.lifeline.entity.Maintenance;
import com.example.lifeline.service.baseservice.BaseService;
// 定义接口，实际函数在impl文件中
public interface MaintenanceService extends BaseService<Maintenance> {

    //展示维修所有任务
    IPage<Maintenance> show(int pageNum);

    //新建维修工作
    void add(Maintenance maintenance);

    //删除维修工作（根据机组号查询）
    void delete(String maintenanceTurbine);

    //修改维修工作
    void update(Maintenance maintenance);

    //根据机组号查询
    IPage<Maintenance> select(String maintenanceTurbine, int pageNum);

}
