package com.example.lifeline.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@TableName("rolemanage")
public class Role {
    @TableField(value = "role")
    @ExcelProperty("角色")
    private String role;

    @TableField(value = "roleName")
    @ExcelProperty("角色名称")
    private String roleName;

    @TableField(value = "roleDesc")
    @ExcelProperty("角色描述")
    private String roleDesc;
}
