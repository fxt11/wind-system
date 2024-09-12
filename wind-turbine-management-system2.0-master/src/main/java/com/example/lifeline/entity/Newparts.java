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
@TableName("newpartsmanage")
public class Newparts {

    @TableField(value = "factoryMaterialId")
    @ExcelProperty("厂家物料编码")
    private String factoryMaterialId;

    @TableField(value = "SAPMaterialId")
    @ExcelProperty("SAP物料编码")
    private String SAPMaterialId;

    @TableField(value = "stationMaterialId")
    @ExcelProperty("场站物料编码")
    private String stationMaterialId;

    @TableId(value = "newpartsId", type = IdType.INPUT)
    @ExcelProperty("新件编号")
    private Integer newpartsId;

    @TableField(value = "partName")
    @ExcelProperty("中文名称")
    private String partName;

    @TableField(value = "specificationsModels")
    @ExcelProperty("规格型号")
    private String specificationsModels;

    @TableField(value = "quantity")
    @ExcelProperty("数量")
    private Integer quantity;

    @TableField(value = "unit")
    @ExcelProperty("单位")
    private Integer unit;

    @TableField(value = "unitPrice")
    @ExcelProperty("单价")
    private double unitPrice;

    @TableField(value = "total")
    @ExcelProperty("总价")
    private double total;

    @TableField(value = "warehouseEntryTime")
    @JsonFormat(pattern = "yyyy-mm-dd HH:MM:SS")
    @ExcelProperty("入库时间")
    private String warehouseEntryTime;

//    @TableField(value = "deliveryTime")
//    @JsonFormat(pattern = "yyyy-mm-dd HH:MM:SS")
//    @ExcelProperty("出库时间")
//    private String deliveryTime;

}
