package com.example.lifeline.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.lifeline.entity.PreventiveManage;
import com.example.lifeline.mapper.basemapper.EasyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("preventivemanage")
public interface PreventiveManageMapper extends EasyBaseMapper<PreventiveManage> {

}
