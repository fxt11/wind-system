package com.example.lifeline.service.baseservice;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseService<T> {

    /**
     * 批量插入数据
     *
     * @param list 插入的数据对象
     */
    void batchInsert(List<T> list);

    /**
     *
     * @param queryWrapper
     * @return
     */
    Long selectCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

    /**
     *
     * @param page
     * @param queryWrapper
     * @param <P>
     * @return
     */
    <P extends IPage<T>> P selectPage(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

}
