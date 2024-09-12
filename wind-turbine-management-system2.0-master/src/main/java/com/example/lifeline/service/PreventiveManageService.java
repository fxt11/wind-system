package com.example.lifeline.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.lifeline.entity.PreventiveManage;
import com.example.lifeline.service.baseservice.BaseService;

public interface PreventiveManageService extends BaseService<PreventiveManage> {

    //展示预防性维修所有任务
    IPage<PreventiveManage> show(int pageNum);

    //新建添加预防性检修工作
    void addPrevent(PreventiveManage preventiveManage) throws Exception;

    //删除预防性检修工作
    void deletePrevent(String maintenanceTurbine);

    //修改预防性检修工作
    void updatePrevent(PreventiveManage preventiveManage);

}
