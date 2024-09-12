package com.example.lifeline.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.Maintenance;
import com.example.lifeline.mapper.MaintenanceMapper;
import com.example.lifeline.mapper.Maintenance_select_Mapper;
import com.example.lifeline.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MaintenanceService")
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private Maintenance_select_Mapper maintenance_select_mapper;

    @Autowired
    private MaintenanceMapper maintenanceMapper;

    //展示维修所有任务
    public IPage<Maintenance> show(int pageNum) {
        Page<Maintenance> page = new Page<>(pageNum, 10);
        IPage<Maintenance> iPage = maintenance_select_mapper.selectPage(page, null);
        return iPage;
    }

    //新建维修工作
    public void add(Maintenance maintenance) {
        maintenanceMapper.insert(maintenance);
    }

    //删除维修工作（根据机组号查询）
    public void delete(String maintenanceTurbine) {
        QueryWrapper<Maintenance> queryWrapper = new QueryWrapper();
        queryWrapper.eq("maintenanceTurbine", maintenanceTurbine);
        maintenanceMapper.delete(queryWrapper);
    }

    //修改维修工作
    public void update(Maintenance maintenance) {
        delete(maintenance.getMaintenanceTurbine());
        add(maintenance);
    }

    //根据机组号查询
    public IPage<Maintenance> select(String maintenanceTurbine, int pageNum) {
        QueryWrapper<Maintenance> queryWrapper = new QueryWrapper();
        queryWrapper.eq("maintenanceTurbine", maintenanceTurbine);
        Page<Maintenance> page = new Page<>(pageNum, 10);
        IPage<Maintenance> iPage = maintenance_select_mapper.selectPage(page, queryWrapper);
        return iPage;
    }

    @Override
    public void batchInsert(List<Maintenance> list) {
        maintenanceMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Long selectCount(Wrapper<Maintenance> queryWrapper) {
        return maintenanceMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<Maintenance>> P selectPage(P page, Wrapper<Maintenance> queryWrapper) {
        return maintenanceMapper.selectPage(page, queryWrapper);
    }

}
