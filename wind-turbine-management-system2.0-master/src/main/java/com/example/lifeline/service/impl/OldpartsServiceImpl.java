package com.example.lifeline.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.Oldparts;
import com.example.lifeline.mapper.OldpartsMapper;
import com.example.lifeline.service.OldpartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("OldpartsService")
public class OldpartsServiceImpl implements OldpartsService {

    @Autowired
    private OldpartsMapper oldpartsMapper;

    //展示返修件件信息
    public IPage getAllOldParts(Integer pageNum) {
        Page<Oldparts> page = new Page<>(pageNum, 10);
        IPage<Oldparts> iPage = oldpartsMapper.selectPage(page, null);
        return iPage;
    }

    //查询返修件，可根据使用在同一风机进行查询
    public IPage selectOldParts(int turbineId) throws Exception {
        Page<Oldparts> page = new Page<>(1, 10);
        QueryWrapper<Oldparts> queryWrapper = new QueryWrapper();
        queryWrapper.eq("turbineId", turbineId);
        if (oldpartsMapper.selectCount(queryWrapper) == 0) {
            throw new Exception("该风机设备未更换过备件");
        }
        IPage<Oldparts> iPage = oldpartsMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    //查询返修建，可根据中文名称进行查询
    public IPage selectOldParts(String partName) throws Exception {
        Page<Oldparts> page = new Page<>(1, 10);
        QueryWrapper<Oldparts> queryWrapper = new QueryWrapper();
        queryWrapper.like("partName", partName);
        if (oldpartsMapper.selectCount(queryWrapper) == 0) {
            throw new Exception("未查询到该名称的备件");
        }
        IPage<Oldparts> iPage = oldpartsMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    //添加返修件
    public void addOldparts(Oldparts oldparts) throws Exception {
        QueryWrapper<Oldparts> queryWrapper = new QueryWrapper();
        queryWrapper.eq("oldpartsId", oldparts.getOldpartsId());
        if (oldpartsMapper.selectCount(queryWrapper) > 0) {
            UpdateWrapper<Oldparts> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("oldpartsId", oldparts.getOldpartsId());
            Oldparts oldparts1 = oldpartsMapper.selectOne(queryWrapper);
            oldparts1.setQuantity(oldparts1.getQuantity() + oldparts.getQuantity());
            oldpartsMapper.update(oldparts1, updateWrapper);
        } else {
            oldpartsMapper.insert(oldparts);
        }
    }

    //删除返修备件信息
    public void deleteOldparts(Integer oldpartsId) throws Exception {
        QueryWrapper<Oldparts> queryWrapper = new QueryWrapper();
        queryWrapper.eq("oldpartsId", oldpartsId);
        if (oldpartsMapper.selectCount(queryWrapper) == 0) {
            throw new Exception("该备件不存在");
        }
        oldpartsMapper.delete(queryWrapper);
    }

    //修改备件信息
    public void updateOldparts(Oldparts oldparts) throws Exception {
        UpdateWrapper<Oldparts> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("oldpartsId", oldparts.getOldpartsId());
        oldpartsMapper.update(oldparts, updateWrapper);
    }

    @Override
    public void batchInsert(List<Oldparts> list) {
        oldpartsMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Long selectCount(Wrapper<Oldparts> queryWrapper) {
        return oldpartsMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<Oldparts>> P selectPage(P page, Wrapper<Oldparts> queryWrapper) {
        return oldpartsMapper.selectPage(page, queryWrapper);
    }

}
