package com.example.lifeline.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.Maintainer;
import com.example.lifeline.mapper.MaintainerMapper;
import com.example.lifeline.mapper.Maintainer_select_Mapper;
import com.example.lifeline.service.MaintainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MaintainerService")
public class MaintainerServiceImpl implements MaintainerService {

    @Autowired
    private Maintainer_select_Mapper maintainer_select_mapper;

    @Autowired
    private MaintainerMapper maintainerMapper;

    //根据姓名模糊查询维修人员
    public IPage<Maintainer> select(String name) {
        QueryWrapper<Maintainer> QueryWrapper = new QueryWrapper();
        //根据姓名进行模糊查询
        QueryWrapper.like("uname", name);
        Page<Maintainer> page = new Page<>(1, 10);
        IPage<Maintainer> iPage = maintainer_select_mapper.selectPage(page, QueryWrapper);
        return iPage;
    }

    //根据工号模糊查询维修人员
    public IPage<Maintainer> selectNum(String jobNumber) {
        QueryWrapper<Maintainer> QueryWrapper = new QueryWrapper();
        QueryWrapper.eq("jobNumber", jobNumber);
        Page<Maintainer> page = new Page<>(1, 10);
        IPage<Maintainer> iPage = maintainer_select_mapper.selectPage(page, QueryWrapper);
        return iPage;
    }

    //全部信息分页显示
    public IPage<Maintainer> page(int pageNum) {
        Page<Maintainer> page = new Page<>(pageNum, 10);
        IPage<Maintainer> iPage = maintainer_select_mapper.selectPage(page, null);
        return iPage;
    }

    //增加
    public void save(Maintainer maintainer) {
        maintainerMapper.insert(maintainer);
    }

    //删除
    public void del(String job_number) {
        QueryWrapper<Maintainer> QueryWrapper = new QueryWrapper();
        QueryWrapper.eq("jobNumber", job_number);
        maintainerMapper.delete(QueryWrapper);
    }

    //更新
    public void update(Maintainer maintainer) {
        maintainerMapper.updateById(maintainer);
    }

    @Override
    public void batchInsert(List<Maintainer> list) {
        maintainerMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Long selectCount(Wrapper<Maintainer> queryWrapper) {
        return maintainerMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<Maintainer>> P selectPage(P page, Wrapper<Maintainer> queryWrapper) {
        return maintainerMapper.selectPage(page, queryWrapper);
    }
}
