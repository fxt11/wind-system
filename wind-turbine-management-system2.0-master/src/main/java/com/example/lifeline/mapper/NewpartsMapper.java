package com.example.lifeline.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.lifeline.entity.Newparts;
import com.example.lifeline.mapper.basemapper.EasyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("newpartsmanage")
public interface NewpartsMapper extends EasyBaseMapper<Newparts> {

}
