package com.example.lifeline.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.Role;
import com.example.lifeline.mapper.RoleMapper;
import com.example.lifeline.service.RoleManageService;
import com.example.lifeline.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("RoleManageService")
public class RoleManageServiceImpl implements RoleManageService {
    @Autowired
    private RoleMapper roleMapper;

    // 查询全部角色信息
    @Override
    public IPage getAllRole(Integer pageNum) {
        Page<Role> page = new Page<>(pageNum, 10);
        IPage<Role> iPage = roleMapper.selectPage(page, null);
        return iPage;
    }

    // 修改角色权限信息
    @Override
    public void updateRole(Role role) {
        UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role", role.getRole());
        roleMapper.update(role, updateWrapper);
    }

    @Override
    public void save(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void del(String role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", role);
        roleMapper.delete(queryWrapper);
    }

    @Override
    public Result getRoles(String role) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.select("roleName,roleDesc").eq("role", role);
        List<Map<String, Object>> list = roleMapper.selectMaps(wrapper);
        String s = String.valueOf(list.get(0).get("roleDesc"));
        List<String> stringList = new ArrayList<>();
        int begin = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == ',' || s.charAt(i) == ' ' || s.charAt(i) == '、' || s.charAt(i) == '，') {
                if (begin != i) {
                    stringList.add(s.substring(begin, i));
                }
                begin = i + 1;
            }
        }
        stringList.add(s.substring(begin));
        list.get(0).put("roleDesc", stringList);
        return Result.ok().data("roleName", list.get(0).get("roleName")).data("roleDesc", list.get(0).get("roleDesc"));
    }

    @Override
    public void batchInsert(List<Role> list) {
        roleMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Long selectCount(Wrapper<Role> queryWrapper) {
        return roleMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<Role>> P selectPage(P page, Wrapper<Role> queryWrapper) {
        return roleMapper.selectPage(page, queryWrapper);
    }
}
