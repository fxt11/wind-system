package com.example.lifeline.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@TableName("preventivemanage")
public class PreventiveManage {

    @TableField(value = "workTask")
    @ExcelProperty("检修内容")
    private String workTask;

    @TableField(value = "maintenanceRole")
    @ExcelProperty("检修人员")
    private String maintenanceRole;

    @TableId(value = "maintenanceTurbine", type = IdType.INPUT)
    @ExcelProperty("检修机组")
    private String maintenanceTurbine;

    @TableField(value = "periodicMaintenance")
    @ExcelProperty("定期检修类型") // （半年检、年检、技改）
    private String periodicMaintenance;

    @TableField(value = "time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty("检修开始时间")
    private String time;

    @TableField(value = "comment")
    @ExcelProperty("备注")
    private String comment;

}
