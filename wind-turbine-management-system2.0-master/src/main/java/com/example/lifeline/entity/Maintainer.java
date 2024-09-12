package com.example.lifeline.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@TableName("maintainer")
public class Maintainer {

    @TableField(value = "uname")
    @ExcelProperty("姓名")
    private String uname;

    @TableId(value = "jobNumber", type = IdType.INPUT)
    @ExcelProperty("工号")
    private String jobNumber;

    @TableField(value = "role")
    @ExcelProperty("角色")
    private String role;

    @TableField(value = "maintenanceContents", updateStrategy = FieldStrategy.IGNORED)
    @ExcelProperty("工作内容")
    private String maintenanceContents = "无";

    @JsonFormat(pattern = "yyyy-mm-dd HH:MM:SS")
    @TableField(value = "startProcessTime", updateStrategy = FieldStrategy.IGNORED)
    @ExcelProperty("工作开始时间")
    private String startProcessTime = "无";

}
