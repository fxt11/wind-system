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
@TableName("selectview")
public class SelectView {

    @TableField(value = "faultName")
    @ExcelProperty("故障名称")
    private String faultName;

    @TableId(value = "faultId", type = IdType.INPUT)
    @ExcelProperty("故障代码")
    private Integer faultId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "faultReportingTime")
    @ExcelProperty("故障报出时间")
    private String faultReportingTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "resetRunningTime", insertStrategy = FieldStrategy.IGNORED)
    @ExcelProperty("复位运行时间")
    private String resetRunningTime;

    @TableField(value = "loss")
    @ExcelProperty("经济损失")
    private Double loss;

    @TableField(value = "faultTurbine")
    @ExcelProperty("故障机组")
    private String faultTurbine;

}
