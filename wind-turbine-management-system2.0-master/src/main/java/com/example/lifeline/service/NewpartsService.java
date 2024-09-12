package com.example.lifeline.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.lifeline.entity.Newparts;
import com.example.lifeline.service.baseservice.BaseService;

public interface NewpartsService extends BaseService<Newparts> {

    //展示备件信息
    IPage getAllNewParts(Integer pageNum);

    //查询新件，可根据使用在同一风机进行查询
    IPage selectNewParts(int turbineId) throws Exception;

    //查询新建，可根据中文名称进行查询
    IPage selectNewParts(String partName) throws Exception;

    //新建、导入一批新建
    void addNewparts(Newparts newparts) throws Exception;

    //删除、消耗新备件信息
    void deleteNewparts(Newparts newparts) throws Exception;

    //修改备件信息
    void updateNewparts(Newparts newparts) throws Exception;
}
