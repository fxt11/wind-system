package com.example.lifeline.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.PreventiveManage;
import com.example.lifeline.mapper.PreventiveManageMapper;
import com.example.lifeline.service.PreventiveManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PreventiveManageService")
public class PreventiveManageServiceImpl implements PreventiveManageService {

    @Autowired
    private PreventiveManageMapper preventiveManageMapper;

    //展示预防性维修所有任务
    public IPage<PreventiveManage> show(int pageNum) {
        Page<PreventiveManage> page = new Page<>(pageNum, 10);
        IPage<PreventiveManage> iPage = preventiveManageMapper.selectPage(page, null);
        return iPage;
    }

    //新建添加预防性检修工作
    public void addPrevent(PreventiveManage preventiveManage) throws Exception {
        QueryWrapper<PreventiveManage> queryWrapper = new QueryWrapper();
        queryWrapper.eq("maintenanceTurbine", preventiveManage.getMaintenanceTurbine());
        if (preventiveManageMapper.selectCount(queryWrapper) > 0) {
            throw new Exception("该设备已有检修安排");
        }
        preventiveManageMapper.insert(preventiveManage);
    }

    //删除预防性检修工作
    public void deletePrevent(String maintenanceTurbine) {
        QueryWrapper<PreventiveManage> queryWrapper = new QueryWrapper();
        queryWrapper.eq("maintenanceTurbine", maintenanceTurbine);
        preventiveManageMapper.delete(queryWrapper);
    }

    //修改预防性检修工作
    public void updatePrevent(PreventiveManage preventiveManage) {
        UpdateWrapper<PreventiveManage> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("maintenanceTurbine", preventiveManage.getMaintenanceTurbine());
        preventiveManageMapper.update(preventiveManage, updateWrapper);
    }

    @Override
    public void batchInsert(List<PreventiveManage> list) {
        preventiveManageMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Long selectCount(Wrapper<PreventiveManage> queryWrapper) {
        return preventiveManageMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<PreventiveManage>> P selectPage(P page, Wrapper<PreventiveManage> queryWrapper) {
        return preventiveManageMapper.selectPage(page, queryWrapper);
    }

}
