package com.example.lifeline.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.SelectView;
import com.example.lifeline.mapper.SelectViewMapper;
import com.example.lifeline.service.SelectViewService;
import com.example.lifeline.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SelectViewService")
public class SelectViewServiceImpl implements SelectViewService {

    @Autowired
    SelectViewMapper selectViewMapper;

    //获取所有故障查询信息
    public Result getAllInfo(int pageNum) {
        Page<SelectView> page = new Page<>(pageNum, 10);
        IPage<SelectView> iPage = selectViewMapper.selectPage(page, null);
        return Result.ok().data("page", iPage);
    }

    //根据机组编号查询故障信息
    public Result selectId(String turbineId) {
        QueryWrapper<SelectView> wrapper = new QueryWrapper<>();
        wrapper.eq("faultTurbine", turbineId);
        Page<SelectView> page = new Page<>(1, 10);
        IPage<SelectView> iPage = selectViewMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    //根据故障名称查询故障信息
    public Result selectName(String faultName) {
        QueryWrapper<SelectView> wrapper = new QueryWrapper<>();
        wrapper.like("faultName", faultName);
        Page<SelectView> page = new Page<>(1, 10);
        IPage<SelectView> iPage = selectViewMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    @Override
    public void batchInsert(List<SelectView> list) {
        selectViewMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Long selectCount(Wrapper<SelectView> queryWrapper) {
        return selectViewMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<SelectView>> P selectPage(P page, Wrapper<SelectView> queryWrapper) {
        return selectViewMapper.selectPage(page, queryWrapper);
    }

}
