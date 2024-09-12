package com.example.lifeline.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.Newparts;
import com.example.lifeline.mapper.NewpartsMapper;
import com.example.lifeline.service.NewpartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("NewpartsService")
public class NewpartsServiceImpl implements NewpartsService {

    @Autowired
    private NewpartsMapper newpartsMapper;

    //展示备件信息
    public IPage getAllNewParts(Integer pageNum) {
        Page<Newparts> page = new Page<>(pageNum, 10);
        IPage<Newparts> iPage = newpartsMapper.selectPage(page, null);
        return iPage;
    }

    //查询新件，可根据使用在同一风机进行查询
    public IPage selectNewParts(int turbineId) throws Exception {
        Page<Newparts> page = new Page<>(1, 10);
        QueryWrapper<Newparts> queryWrapper = new QueryWrapper();
        queryWrapper.eq("turbineId", turbineId);
        if (newpartsMapper.selectCount(queryWrapper) == 0) {
            throw new Exception("该风机设备未更换过新件");
        }
        IPage<Newparts> iPage = newpartsMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    //查询新建，可根据中文名称进行查询
    public IPage selectNewParts(String partName) throws Exception {
        Page<Newparts> page = new Page<>(1, 10);
        QueryWrapper<Newparts> queryWrapper = new QueryWrapper();
        queryWrapper.like("partName", partName);
        if (newpartsMapper.selectCount(queryWrapper) == 0) {
            throw new Exception("未查询到该名称的备件");
        }
        IPage<Newparts> iPage = newpartsMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    //新建、导入一批新建
    public void addNewparts(Newparts newparts) throws Exception {
        QueryWrapper<Newparts> queryWrapper = new QueryWrapper();
        queryWrapper.eq("newpartsId", newparts.getNewpartsId());
        if (newpartsMapper.selectCount(queryWrapper) > 0) {
            UpdateWrapper<Newparts> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("newpartsId", newparts.getNewpartsId());
            Newparts newparts1 = newpartsMapper.selectOne(queryWrapper);
            newparts1.setQuantity(newparts1.getQuantity() + newparts.getQuantity());
            newpartsMapper.update(newparts1, updateWrapper);
        } else {
            newpartsMapper.insert(newparts);
        }
    }

    //删除、消耗新备件信息
    public void deleteNewparts(Newparts newparts) throws Exception {
        QueryWrapper<Newparts> queryWrapper = new QueryWrapper();
        queryWrapper.eq("newpartsId", newparts.getNewpartsId());
        if (newpartsMapper.selectCount(queryWrapper) == 0) {
            throw new Exception("该备件不存在");
        } else {
            Newparts newparts1 = newpartsMapper.selectOne(queryWrapper);
            if (newparts1.getQuantity() < newparts.getQuantity()) {
                throw new Exception("新件不足");
            } else if (newparts1.getQuantity().equals(newparts.getQuantity())) {
                newpartsMapper.delete(queryWrapper);
            } else {
                UpdateWrapper<Newparts> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("newpartsId", newparts.getNewpartsId());
                newparts1.setQuantity(newparts1.getQuantity() - newparts.getQuantity());
                newpartsMapper.update(newparts1, updateWrapper);
            }
        }

    }

    //修改备件信息
    public void updateNewparts(Newparts newparts) throws Exception {
        UpdateWrapper<Newparts> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("newpartsId", newparts.getNewpartsId());
        newpartsMapper.update(newparts, updateWrapper);
    }

    @Override
    public void batchInsert(List<Newparts> list) {
        newpartsMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Long selectCount(Wrapper<Newparts> queryWrapper) {
        return newpartsMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<Newparts>> P selectPage(P page, Wrapper<Newparts> queryWrapper) {
        return newpartsMapper.selectPage(page, queryWrapper);
    }

}
