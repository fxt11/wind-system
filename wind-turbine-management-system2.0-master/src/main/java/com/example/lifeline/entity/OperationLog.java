package com.example.lifeline.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@TableName("operationlog")
public class OperationLog {

    @TableField("ip")
    @ExcelProperty("IP地址")
    String IP;

    @TableField("url")
    @ExcelProperty("请求地址")
    String url;

    @TableField("uid")
    @ExcelProperty("操作者uid")
    String uid;

    @TableField("module")
    @ExcelProperty("操作模块")
    String module;

    @TableField("timeCost")
    @ExcelProperty("请求耗时")
    Long timeCost;

    @TableField("classMethod")
    @ExcelProperty("请求方法")
    String classMethod;

    @TableField("operationType")
    @ExcelProperty("请求类型")
    String operationType;

    @TableField("operationDesc")
    @ExcelProperty("操作描述")
    String operationDesc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("operationTime")
    @ExcelProperty("操作时间")
    Timestamp operationTime;

    @TableField("exception")
    @ExcelProperty("异常情况")
    String exception;

}
