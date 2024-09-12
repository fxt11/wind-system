package com.example.lifeline.service;

import com.example.lifeline.entity.WindField;
import com.example.lifeline.service.baseservice.BaseService;
import com.example.lifeline.utils.result.Result;

public interface WindFieldService extends BaseService<WindField> {
    // 获取所有风场信息
    Result getAllWindField(int pageNum);

    // 新增风场
    Result addWindField(WindField windField, int pageNum) throws Exception;

    // 删除风场
    Result deleteWindField(String windFieldName);

    // 修改风场信息
    Result editWindField(WindField windField, int pageNum);

    // 查询某一个风场
    Result getWindField(String windFieldName);

    // 查询一个风场里的全部机组
    Result getAllTurbines(String windFieldName, int pageNum);

    Result getAllWindFieldName();

}
