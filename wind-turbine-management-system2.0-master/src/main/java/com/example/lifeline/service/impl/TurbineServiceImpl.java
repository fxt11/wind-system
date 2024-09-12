package com.example.lifeline.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.Turbine;
import com.example.lifeline.entity.WindField;
import com.example.lifeline.mapper.TurbineMapper;
import com.example.lifeline.mapper.WindFieldMapper;
import com.example.lifeline.service.TurbineService;
import com.example.lifeline.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service("TurbineService")
public class TurbineServiceImpl implements TurbineService {

    @Autowired
    private TurbineMapper turbineMapper;

    @Autowired
    private WindFieldMapper windFieldMapper;

    @Override
    public Result getAllTurbines(int pageNum) {
        Page<Turbine> page = new Page<>(pageNum, 10);
        IPage<Turbine> iPage = turbineMapper.selectPage(page, null);
        return Result.ok().data("page", iPage);
    }

    @Override
    public Result getTurbine(String windFieldName, String turbineId) {
        QueryWrapper<Turbine> wrapper = new QueryWrapper<>();
        wrapper.eq("turbineId", turbineId);
        if (!Objects.equals(windFieldName, "")) {
            wrapper.eq("windFieldName", windFieldName);
        }
        Page<Turbine> page = new Page<>(1, 10);
        IPage<Turbine> iPage = turbineMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    @Override
    public Result addTurbine(Turbine turbine, int pageNum) throws Exception {
        QueryWrapper<WindField> wrapper = new QueryWrapper<>();
        wrapper.eq("windFieldName", turbine.getWindFieldName());
        if (windFieldMapper.selectOne(wrapper) == null) {
            throw new Exception("该风场不存在!");
        }
        turbineMapper.insert(turbine);
        WindField windField = new WindField();
        windField.setWindFieldName(turbine.getWindFieldName());
        windField.setTotalNum(windFieldMapper.selectOne(wrapper).getTotalNum() + 1);
        windField.setTotalProduction(windFieldMapper.selectOne(wrapper).getTotalProduction() + turbine.getTurbineProduction());
        windFieldMapper.updateById(windField);
        return getAllTurbines(pageNum);
    }

    @Override
    public Result deleteTurbine(String windFieldName, String turbineId) {
        QueryWrapper<Turbine> wrapper = new QueryWrapper<>();
        wrapper.eq("windFieldName", windFieldName).eq("turbineId", turbineId);
        Turbine turbine = turbineMapper.selectOne(wrapper);
        QueryWrapper<WindField> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("windFieldName", windFieldName);
        WindField windField = new WindField();
        windField.setWindFieldName(windFieldName);
        windField.setTotalNum(windFieldMapper.selectOne(queryWrapper).getTotalNum() - 1);
        windField.setTotalProduction(windFieldMapper.selectOne(queryWrapper).getTotalProduction() - turbine.getTurbineProduction());
        windFieldMapper.updateById(windField);
        turbineMapper.delete(wrapper);
        return Result.ok();
    }

    @Override
    public Result editTurbine(Turbine turbine, int pageNum) {
        QueryWrapper<Turbine> wrapper = new QueryWrapper<>();
        wrapper.eq("windFieldName", turbine.getWindFieldName()).eq("turbineId", turbine.getTurbineId());
        String windFieldName = turbine.getWindFieldName();
        Double pro_start = turbineMapper.selectOne(wrapper).getTurbineProduction();
        Double pro_end = turbine.getTurbineProduction();
        turbineMapper.update(turbine, wrapper);
        QueryWrapper<WindField> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("windFieldName", turbine.getWindFieldName());
        WindField windField = new WindField();
        windField.setWindFieldName(windFieldName);
        windField.setTotalProduction(windFieldMapper.selectOne(queryWrapper).getTotalProduction() - pro_start + pro_end);
        windFieldMapper.updateById(windField);
        return getAllTurbines(pageNum);
    }

    @Override
    public Result getAllNormalTurbines(int pageNum) {
        QueryWrapper<Turbine> wrapper = new QueryWrapper<>();
        wrapper.eq("turbineStatus", "正常运转");
        Page<Turbine> page = new Page<>(pageNum, 10);
        IPage<Turbine> iPage = turbineMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    @Override
    public Result getAllFaultTurbines(int pageNum) {
        QueryWrapper<Turbine> wrapper = new QueryWrapper<>();
        wrapper.eq("turbineStatus", "出现故障");
        Page<Turbine> page = new Page<>(pageNum, 10);
        IPage<Turbine> iPage = turbineMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    @Override
    public void batchInsert(List<Turbine> list) {
        turbineMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Long selectCount(Wrapper<Turbine> queryWrapper) {
        return turbineMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<Turbine>> P selectPage(P page, Wrapper<Turbine> queryWrapper) {
        return turbineMapper.selectPage(page, queryWrapper);
    }
}
