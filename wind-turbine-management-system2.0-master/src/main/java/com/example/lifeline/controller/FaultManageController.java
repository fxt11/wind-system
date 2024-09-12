package com.example.lifeline.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.lifeline.entity.FaultManage;
import com.example.lifeline.service.FaultManageService;
import com.example.lifeline.utils.excel.ExportListener;
import com.example.lifeline.utils.excel.ImportListener;
import com.example.lifeline.utils.log.OperationLogAnnotation;
import com.example.lifeline.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/fault")
@Api(tags = "故障管理表")
public class FaultManageController {

    @Autowired
    @Qualifier("FaultManageService")
    private FaultManageService faultManageService;

    // 获取所有故障位置一级的故障信息及故障的数量
    @GetMapping("/pie/info_one")
    @ApiOperation(value = "获取所有一级故障信息")
    @OperationLogAnnotation(module = "faultmanage", operationType = "GET", operationDesc = "获取所有一级故障信息")
    public Result info_one() {
        return faultManageService.getAllFaultOne();
    }

    // 获取所有故障位置二级的故障信息及故障的数量
    @GetMapping("/pie/info_two")
    @ApiOperation(value = "获取所有二级故障信息")
    @OperationLogAnnotation(module = "faultmanage", operationType = "GET", operationDesc = "获取所有二级故障信息")
    public Result info_two(@RequestParam String faultLocationOne) {
        return faultManageService.getAllFaultTwo(faultLocationOne);
    }

    // 获取所有需要排查的项目及其重要程度(数量表示)
    @GetMapping("/pie/info_check")
    @ApiOperation(value = "获取所有排查项目")
    @OperationLogAnnotation(module = "faultmanage", operationType = "GET", operationDesc = "获取所有排查项目")
    public Result info_check() {
        return faultManageService.getAllCheckItems();
    }

    @GetMapping("/col/info_type")
    @ApiOperation(value = "获取各个故障类型的比例")
    @OperationLogAnnotation(module = "faultmanage", operationType = "GET", operationDesc = "获取各个故障类型的比例")
    public Result info_type() {
        return faultManageService.getAllFaultType();
    }

    @GetMapping("/table/all")
    @ApiOperation(value = "获取所有故障设备的信息")
    @OperationLogAnnotation(module = "faultmanage", operationType = "GET", operationDesc = "获取所有故障设备的信息")
    public Result getAllFaultInfo(@RequestParam(defaultValue = "1") int pageNum) {
        return faultManageService.getAllInfo(pageNum);
    }

    @PostMapping("/table/one")
    @ApiOperation(value = "获取单个故障设备的信息")
    @OperationLogAnnotation(module = "faultmanage", operationType = "POST", operationDesc = "获取单个故障设备的信息")
    public Result getFaultInfo(@RequestParam String faultId) {
        return faultManageService.getInfo(faultId);
    }

    @PostMapping("/addFaultInfo")
    @ApiOperation(value = "添加故障")
    @OperationLogAnnotation(module = "faultmanage", operationType = "POST", operationDesc = "添加故障")
    public Result addDevice(@RequestBody FaultManage faultManage) {
        faultManage.setResetRunningTime(null);
        faultManage.setMaintenanceTime(null);
        return faultManageService.addFault(faultManage);
    }

    @GetMapping("/getNoRepair")
    @ApiOperation(value = "查询所有未被维修的设备")
    @OperationLogAnnotation(module = "faultmanage", operationType = "GET", operationDesc = "查询所有未被维修的设备")
    public Result getOneInfo(@RequestParam(defaultValue = "1") int pageNum) {
        return faultManageService.getOneInfo(pageNum);
    }

    @GetMapping("/getNonRepair")
    @ApiOperation(value = "查询所有未完成维修的设备")
    @OperationLogAnnotation(module = "faultmanage", operationType = "GET", operationDesc = "查询所有未完成维修的设备")
    public Result getTwoInfo(@RequestParam(defaultValue = "1") int pageNum) {
        return faultManageService.getTwoInfo(pageNum);
    }

    @PostMapping("/table/oneNo")
    @ApiOperation(value = "获取单个未安排维修设备的信息")
    @OperationLogAnnotation(module = "faultmanage", operationType = "POST", operationDesc = "获取单个未安排维修设备的信息")
    public Result getNoFaultInfo(@RequestParam String faultId) {
        return faultManageService.getOneNoInfo(faultId);
    }

    @PostMapping("/table/oneNon")
    @ApiOperation(value = "获取单个未完成维修设备的信息")
    @OperationLogAnnotation(module = "faultmanage", operationType = "POST", operationDesc = "获取单个未完成维修设备的信息")
    public Result getNonFaultInfo(@RequestParam String faultId) {
        return faultManageService.getOneNonInfo(faultId);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新维修时间")
    @OperationLogAnnotation(module = "faultmanage", operationType = "POST", operationDesc = "更新维修时间")
    public Result updateFaultInfo(@RequestBody FaultManage faultManage) {
        return faultManageService.updateFaultInfo(faultManage);
    }

    @PostMapping("/okupdate")
    @ApiOperation(value = "更新维修完成时间")
    @OperationLogAnnotation(module = "faultmanage", operationType = "POST", operationDesc = "更新维修完成时间")
    public Result updateOkFaultInfo(@RequestBody FaultManage faultManage) {
        return faultManageService.updateOkFaultInfo(faultManage);
    }

    @GetMapping("/pie/oldParts")
    @ApiOperation(value = "获取备件消耗")
    @OperationLogAnnotation(module = "oldparts", operationType = "GET", operationDesc = "获取备件消耗")
    public Result getOldParts() {
        return faultManageService.getAllParts();
    }

    @GetMapping("/pie/economy")
    @ApiOperation(value = "获取经济消耗")
    @OperationLogAnnotation(module = "faultmanage,oldparts", operationType = "GET", operationDesc = "获取经济消耗")
    public Result getEconomyConsume() throws Exception {
        return faultManageService.getEconomyConsume();
    }

    @GetMapping("/extemplate")
    @ApiOperation(value = "导出故障信息表模板")
    @OperationLogAnnotation(module = "faultmanage", operationType = "GET", operationDesc = "导出故障信息表模板")
    public void downloadModule(HttpServletResponse response) throws IOException {
        //从数据库中读出所有数据
        List<FaultManage> faultManagesList = new ArrayList<>();
        //返回输出流excel格式
        String sheetName = "故障信息表模板";
        String fileName = sheetName.concat(".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //设置字符集为utf-8
        response.setCharacterEncoding("UTF-8");
        //使前端可以获取文件名解码转成中文
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //调用EasyExcel的write方法生成表格返回给用户
        EasyExcel.write(response.getOutputStream(), FaultManage.class).autoCloseStream(Boolean.FALSE).sheet("故障信息表模板").doWrite(faultManagesList);
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出故障信息excel")
    @OperationLogAnnotation(module = "faultmanage", operationType = "GET", operationDesc = "导出故障信息表")
    public void exportExcel(HttpServletResponse response) throws IOException {
        LambdaQueryWrapper<FaultManage> queryWrapper = new LambdaQueryWrapper<>();
        new ExportListener<>(faultManageService)
                .exportExcel(response, "故障信息表", FaultManage.class, queryWrapper);
    }

    @PostMapping(value = "/import")
    @ApiOperation(value = "导入故障信息excel")
    @OperationLogAnnotation(module = "faultmanage", operationType = "POST", operationDesc = "导入故障信息表")
    public void importExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), FaultManage.class,
                new ImportListener<>(faultManageService)).sheet().headRowNumber(1).doRead();
    }

}
