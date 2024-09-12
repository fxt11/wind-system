package com.example.lifeline.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.lifeline.entity.Role;
import com.example.lifeline.service.baseservice.BaseService;
import com.example.lifeline.utils.result.Result;

public interface RoleManageService extends BaseService<Role> {
    // 获取全部角色权限信息
    public IPage getAllRole(Integer pageNum);
    // 修改角色权限
    public void updateRole(Role role);
    // 添加角色权限
    public void save(Role role);
    // 删除角色权限
    public void del(String role);
    // 动态查询角色权限信息
    public Result getRoles(String role);
}
