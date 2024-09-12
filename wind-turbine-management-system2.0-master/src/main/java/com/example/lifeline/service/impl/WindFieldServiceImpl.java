package com.example.lifeline.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.Turbine;
import com.example.lifeline.entity.WindField;
import com.example.lifeline.mapper.TurbineMapper;
import com.example.lifeline.mapper.WindFieldMapper;
import com.example.lifeline.service.WindFieldService;
import com.example.lifeline.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("WindFieldService")
public class WindFieldServiceImpl implements WindFieldService {

    @Autowired
    private WindFieldMapper windFieldMapper;

    @Autowired
    private TurbineMapper turbineMapper;

    @Override
    public Result getAllWindField(int pageNum) {
        Page<WindField> page = new Page<>(pageNum, 10);
        IPage<WindField> iPage = windFieldMapper.selectPage(page, null);
        return Result.ok().data("page", iPage);
    }

    @Override
    public Result addWindField(WindField windField, int pageNum) throws Exception {
        QueryWrapper<WindField> wrapper = new QueryWrapper<>();
        wrapper.eq("windFieldName", windField.getWindFieldName());
        WindField wind = windFieldMapper.selectOne(wrapper);
        if (wind != null) {
            throw new Exception("该风场已存在!");
        }
        windFieldMapper.insert(windField);
        return getAllWindField(pageNum);
    }

    @Override
    public Result deleteWindField(String windFieldName) {
        QueryWrapper<WindField> wrapper = new QueryWrapper<>();
        wrapper.eq("windFieldName", windFieldName);
        windFieldMapper.delete(wrapper);
        QueryWrapper<Turbine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("windFieldName", windFieldName);
        turbineMapper.delete(queryWrapper);
        return Result.ok();
    }

    @Override
    public Result editWindField(WindField windField, int pageNum) {
        QueryWrapper<WindField> wrapper = new QueryWrapper<>();
        wrapper.eq("windFieldName", windField.getWindFieldName());
        windFieldMapper.update(windField, wrapper);
        return getAllWindField(pageNum);
    }

    @Override
    public Result getWindField(String windFieldName) {
        QueryWrapper<WindField> wrapper = new QueryWrapper<>();
        wrapper.eq("windFieldName", windFieldName);
        Page<WindField> page = new Page<>(1, 10);
        IPage<WindField> iPage = windFieldMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    @Override
    public Result getAllTurbines(String windFieldName, int pageNum) {
        QueryWrapper<Turbine> wrapper = new QueryWrapper<>();
        wrapper.select("windFieldName,turbineId,manufacturer,turbineType,turbineStatus,turbineProduction")
                .eq("windFieldName", windFieldName);
        Page<Turbine> page = new Page<>(pageNum, 10);
        IPage<Turbine> iPage = turbineMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    @Override
    public Result getAllWindFieldName() {
        QueryWrapper<WindField> wrapper = new QueryWrapper<>();
        wrapper.select("windFieldName as label");
        List<Map<String, Object>> list = windFieldMapper.selectMaps(wrapper);
        for (Map<String, Object> map : list) {
            map.put("value", map.get("label"));
        }
        return Result.ok().data("list", list);
    }

    @Override
    public void batchInsert(List<WindField> list) {
        windFieldMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Long selectCount(Wrapper<WindField> queryWrapper) {
        return windFieldMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<WindField>> P selectPage(P page, Wrapper<WindField> queryWrapper) {
        return windFieldMapper.selectPage(page,queryWrapper);
    }
}
