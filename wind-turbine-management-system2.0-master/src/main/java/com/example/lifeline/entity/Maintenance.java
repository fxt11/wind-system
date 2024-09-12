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
@TableName("maintenance")
public class Maintenance {

    @TableField(value = "maintenanceLevel")
    @ExcelProperty("维修等级")
    private String maintenanceLevel;

    @TableField(value = "maintenanceType")
    @ExcelProperty("维修类型")
    private String maintenanceType;

    @TableField(value = "maintenanceAction")
    @ExcelProperty("维修动作")
    private String maintenanceAction;

    @TableId(value = "maintenanceTurbine", type = IdType.INPUT)
    @ExcelProperty("维修机组")
    private String maintenanceTurbine;

    @TableField(value = "maintenanceTime")
    @JsonFormat(pattern = "yyyy-mm-dd HH:MM:SS")
    @ExcelProperty("维修开始时间")
    private String maintenanceTime;

    @TableField(value = "maintenanceMan")
    @ExcelProperty("维修人员")
    private String maintenanceMan;

}
