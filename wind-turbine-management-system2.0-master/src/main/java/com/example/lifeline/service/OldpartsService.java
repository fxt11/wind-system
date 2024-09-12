package com.example.lifeline.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.lifeline.entity.Oldparts;
import com.example.lifeline.service.baseservice.BaseService;

public interface OldpartsService extends BaseService<Oldparts> {

    //展示返修件件信息
    IPage getAllOldParts(Integer pageNum);

    //查询返修件，可根据使用在同一风机进行查询
    IPage selectOldParts(int turbineId) throws Exception;

    //查询返修建，可根据中文名称进行查询
    IPage selectOldParts(String partName) throws Exception;

    //添加返修件
    void addOldparts(Oldparts oldparts) throws Exception;

    //删除返修备件信息
    void deleteOldparts(Integer oldpartsId) throws Exception;

    //修改备件信息
    void updateOldparts(Oldparts oldparts) throws Exception;
}
