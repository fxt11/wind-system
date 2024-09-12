package com.example.lifeline.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.lifeline.entity.WindField;
import com.example.lifeline.mapper.basemapper.EasyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("windfield")
public interface WindFieldMapper extends EasyBaseMapper<WindField> {
}
