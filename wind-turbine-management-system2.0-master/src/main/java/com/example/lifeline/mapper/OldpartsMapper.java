package com.example.lifeline.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.lifeline.entity.Oldparts;
import com.example.lifeline.mapper.basemapper.EasyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("oldpartsmanage")
public interface OldpartsMapper extends EasyBaseMapper<Oldparts> {

}
