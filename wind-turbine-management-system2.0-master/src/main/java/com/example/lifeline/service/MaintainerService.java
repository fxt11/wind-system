package com.example.lifeline.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.lifeline.entity.Maintainer;
import com.example.lifeline.service.baseservice.BaseService;

public interface MaintainerService extends BaseService<Maintainer> {

    //根据姓名模糊查询维修人员
    IPage<Maintainer> select(String name);

    //根据工号模糊查询维修人员
    IPage<Maintainer> selectNum(String jobNumber);

    //全部信息分页显示
    IPage<Maintainer> page(int pageNum);

    //增加
    void save(Maintainer maintainer);

    //删除
    void del(String job_number);

    //更新
    void update(Maintainer maintainer);
}
