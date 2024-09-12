package com.example.lifeline.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@TableName("userinfo")
public class User {

    @TableId(value = "uid", type = IdType.INPUT)
    @ExcelProperty("账号")
    private String uid;

    @TableField(value = "name")
    @ExcelProperty("姓名")
    private String name;

    @TableField(value = "password")
    @ExcelProperty("密码")
    private String password;

    @TableField(value = "avatar")
    @ExcelProperty("头像")
    private String avatar;

    @TableField(value = "roles")
    @ExcelProperty("角色")
    private String roles;

}
