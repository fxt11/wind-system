package com.example.lifeline.controller;

import com.example.lifeline.service.SelectViewService;
import com.example.lifeline.utils.log.OperationLogAnnotation;
import com.example.lifeline.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/view")
@Api(tags = "综合查询视图")
public class SelectViewController {

    @Autowired
    @Qualifier("SelectViewService")
    private SelectViewService selectViewService;

    // 获取所有故障位置一级的故障信息及故障的数量
    @GetMapping("/info")
    @ApiOperation(value = "获取所有查询信息")
    @OperationLogAnnotation(module = "selectView", operationType = "GET", operationDesc = "获取所有查询信息")
    public Result info_one(@RequestParam(defaultValue = "1") int pageNum) {
        return selectViewService.getAllInfo(pageNum);
    }

    @GetMapping("selectname")
    @ApiOperation(value = "故障名称查询")
    @OperationLogAnnotation(module = "selectView", operationType = "GET", operationDesc = "故障名称查询")
    public Result selectNamebyName(@RequestParam String faultName) {
        return selectViewService.selectName(faultName);
    }

    @GetMapping("selectid")
    @ApiOperation(value = "机组编号查询")
    @OperationLogAnnotation(module = "selectView", operationType = "GET", operationDesc = "机组编号查询")
    public Result selectNamebyId(@RequestParam String turbineId) {
        return selectViewService.selectId(turbineId);
    }

}
