package com.example.lifeline.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@TableName("windfield")
public class WindField {
    @TableId(value = "windFieldName", type = IdType.INPUT)
    @ExcelProperty("风场名称")
    private String windFieldName;

    @TableField(value = "totalNum")
    @ExcelProperty("机组总数")
    private Integer totalNum;

    @TableField(value = "subCompany")
    @ExcelProperty("分公司名称")
    private String subCompany;

    @TableField(value = "totalProduction")
    @ExcelProperty("全场发电量")
    private Double totalProduction;
}
