package com.example.lifeline.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@TableName("faultmanage")
public class FaultManage {

    @TableField(value = "faultName")
    @ExcelProperty("故障名称")
    private String faultName;

    @TableId(value = "faultId", type = IdType.INPUT)
    @ExcelProperty("故障代码")
    private Integer faultId;

    @TableField(value = "faultTurbine")
    @ExcelProperty("故障机组")
    private String faultTurbine;

    @TableField(value = "faultDescribe")
    @ExcelProperty("故障描述")
    private String faultDescribe;

    @TableField(value = "faultType")
    @ExcelProperty("故障类别")
    private String faultType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "faultReportingTime")
    @ExcelProperty("故障报出时间")
    private String faultReportingTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "maintenanceTime", insertStrategy = FieldStrategy.IGNORED)
    @ExcelProperty("维护停机时间")
    private String maintenanceTime = "无";

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "resetRunningTime", insertStrategy = FieldStrategy.IGNORED)
    @ExcelProperty("复位运行时间")
    private String resetRunningTime = "无";

    @TableField(value = "faultLocationOne")
    @ExcelProperty("故障一级位置")
    private String faultLocationOne;

    @TableField(value = "faultLocationTwo")
    @ExcelProperty("故障二级位置")
    private String faultLocationTwo;

    @TableField(value = "faultLocationThree")
    @ExcelProperty("故障三级位置")
    private String faultLocationThree;

    @TableField(value = "checkItems")
    @ExcelProperty("排查项目")
    private String checkItems;

    @TableField(value = "faultCause")
    @ExcelProperty("故障原因")
    private String faultCause;

}
