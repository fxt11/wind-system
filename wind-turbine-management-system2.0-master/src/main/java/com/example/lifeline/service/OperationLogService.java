package com.example.lifeline.service;

import com.example.lifeline.entity.OperationLog;
import com.example.lifeline.service.baseservice.BaseService;

public interface OperationLogService extends BaseService<OperationLog> {

    void insertOperation(OperationLog operationlog);

}
