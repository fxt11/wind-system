package com.example.lifeline.mapper;

import com.example.lifeline.entity.User;
import com.example.lifeline.mapper.basemapper.EasyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends EasyBaseMapper<User> {

}
