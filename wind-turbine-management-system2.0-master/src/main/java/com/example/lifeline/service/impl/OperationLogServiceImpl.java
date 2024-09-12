package com.example.lifeline.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.lifeline.entity.OperationLog;
import com.example.lifeline.mapper.OperationLogMapper;
import com.example.lifeline.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("OperationLogService")
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    //插入一条操作记录
    public void insertOperation(OperationLog operationlog) {
        operationLogMapper.insert(operationlog);
    }

    @Override
    public void batchInsert(List<OperationLog> list) {
        operationLogMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Long selectCount(Wrapper<OperationLog> queryWrapper) {
        return operationLogMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<OperationLog>> P selectPage(P page, Wrapper<OperationLog> queryWrapper) {
        return operationLogMapper.selectPage(page, queryWrapper);
    }

}
