package com.example.lifeline.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@TableName("turbine")
public class Turbine {
    @MppMultiId
    @TableId(value = "windFieldName", type = IdType.INPUT)
    @ExcelProperty("风场名称")
    private String windFieldName;

    @MppMultiId
    @TableField(value = "turbineId")
    @ExcelProperty("机组编号")
    private String turbineId;

    @TableField(value = "manufacturer")
    @ExcelProperty("制造厂商")
    private String manufacturer;

    @TableField(value = "turbineType")
    @ExcelProperty("机组型号")
    private String turbineType;

    @TableField(value = "turbineStatus")
    @ExcelProperty("机组状态")
    private String turbineStatus;

    @TableField(value = "turbineProduction")
    @ExcelProperty("机组发电量")
    private Double turbineProduction;
}
