package com.example.lifeline.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.lifeline.entity.Maintenance;
import com.example.lifeline.mapper.basemapper.EasyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@TableName("maintenanceview")
public interface Maintenance_select_Mapper extends EasyBaseMapper<Maintenance> {

}
