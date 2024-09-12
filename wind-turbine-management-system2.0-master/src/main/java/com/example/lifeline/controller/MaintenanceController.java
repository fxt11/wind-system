package com.example.lifeline.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.lifeline.entity.Maintenance;
import com.example.lifeline.service.MaintenanceService;
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

@RestController    //接口方法返回对象，转换json文本
@RequestMapping("/maintenance")     //localhost：8088/maintenance
@Api(tags = "设备维修管理")
public class MaintenanceController {

    @Autowired
    @Qualifier("MaintenanceService")
    private MaintenanceService maintenanceService;

    //显示维修全部信息
    @GetMapping("/list")
    @ApiOperation(value = "展示维修信息列表")
    @OperationLogAnnotation(module = "maintenanceManagement", operationType = "GET", operationDesc = "获得所有维修信息")
    public Result show(@RequestParam(defaultValue = "1") int pageNum) {
        return Result.ok().data("page", maintenanceService.show(pageNum));
    }

    //根据机组号查询维修信息
    @GetMapping("/select")
    @ApiOperation(value = "维修查询")
    @OperationLogAnnotation(module = "maintenanceManagement", operationType = "GET", operationDesc = "查询维修工作")
    public Result select(@RequestParam String maintenanceTurbine, @RequestParam(defaultValue = "1") int pageNum) {
        return Result.ok().data("page", maintenanceService.select(maintenanceTurbine, pageNum));
    }

    //新增维修信息
    @PostMapping("/add")
    @ApiOperation(value = "新建维修信息")
    @OperationLogAnnotation(module = "maintenanceManagement", operationType = "POST", operationDesc = "添加维修工作")
    // @RequestBody是把json转换为java对象（maintenance）
    public Result add(@RequestBody Maintenance maintenance) {
        maintenanceService.add(maintenance);
        return Result.ok();
    }

    //删除维修信息
    @PostMapping("/delete")
    @ApiOperation(value = "删除维修信息")
    @OperationLogAnnotation(module = "maintenanceManagement", operationType = "DELETE", operationDesc = "删除维修工作")
    public Result delete(@RequestParam String maintenanceTurbine) {
        maintenanceService.delete(maintenanceTurbine);
        return Result.ok();
    }

    //修改维修信息
    @PostMapping("/update")
    @ApiOperation(value = "修改维修信息")
    @OperationLogAnnotation(module = "maintenanceManagement", operationType = "POST", operationDesc = "修改维修工作")
    public Result update(@RequestBody Maintenance maintenance, @RequestParam(defaultValue = "1") int pageNum) {
        maintenanceService.update(maintenance);
        return show(pageNum);
    }

    @GetMapping("/extemplate")
    @ApiOperation(value = "导出维修信息表模板")
    @OperationLogAnnotation(module = "maintenanceManagement", operationType = "GET", operationDesc = "导出维修信息表模板")
    public void downloadModule(HttpServletResponse response) throws IOException {
        //从数据库中读出所有数据
        List<Maintenance> maintenanceList = new ArrayList<>();
        //返回输出流excel格式
        String sheetName = "维修信息表模板";
        String fileName = sheetName.concat(".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //设置字符集为utf-8
        response.setCharacterEncoding("UTF-8");
        //使前端可以获取文件名解码转成中文
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //调用EasyExcel的write方法生成表格返回给用户
        EasyExcel.write(response.getOutputStream(), Maintenance.class).autoCloseStream(Boolean.FALSE).sheet("维修信息表模板").doWrite(maintenanceList);
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出维修信息excel")
    @OperationLogAnnotation(module = "maintenanceManagement", operationType = "GET", operationDesc = "导出维修信息表")
    public void exportExcel(HttpServletResponse response) throws IOException {
        LambdaQueryWrapper<Maintenance> queryWrapper = new LambdaQueryWrapper<>();
        new ExportListener<>(maintenanceService)
                .exportExcel(response, "维修信息表", Maintenance.class, queryWrapper);
    }

    @PostMapping(value = "/import")
    @ApiOperation(value = "导入维修信息excel")
    @OperationLogAnnotation(module = "maintenanceManagement", operationType = "POST", operationDesc = "导入维修信息表")
    public void importExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Maintenance.class,
                new ImportListener<>(maintenanceService)).sheet().headRowNumber(1).doRead();
    }

}
