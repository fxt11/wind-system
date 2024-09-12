package com.example.lifeline.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.lifeline.entity.Turbine;
import com.example.lifeline.service.TurbineService;
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
@RequestMapping("/Turbine")
@Api(tags = "机组管理")
public class TurbineController {

    @Autowired
    @Qualifier("TurbineService")
    private TurbineService turbineService;

    @GetMapping("/all_turbine_info")
    @ApiOperation(value = "显示机组全部信息")
    @OperationLogAnnotation(module = "turbine", operationType = "GET", operationDesc = "显示机组全部信息")
    public Result getAllTurbineInfo(@RequestParam(defaultValue = "1") int pageNum) {
        return turbineService.getAllTurbines(pageNum);
    }

    @GetMapping("/turbine_info")
    @ApiOperation(value = "查询某一个机组")
    @OperationLogAnnotation(module = "turbine", operationType = "GET", operationDesc = "查询某一个机组")
    public Result getTurbineInfo(@RequestParam String windFieldName, @RequestParam String turbineId) {
        return turbineService.getTurbine(windFieldName, turbineId);
    }

    @PostMapping("/add_turbine")
    @ApiOperation(value = "新增一个机组")
    @OperationLogAnnotation(module = "turbine", operationType = "POST", operationDesc = "新增一个机组")
    public Result addTurbine(@RequestBody Turbine turbine, @RequestParam(defaultValue = "1") int pageNum) throws Exception {
        return turbineService.addTurbine(turbine, pageNum);
    }

    @PostMapping("/delete_turbine")
    @ApiOperation(value = "删除一个机组")
    @OperationLogAnnotation(module = "turbine", operationType = "POST", operationDesc = "删除一个机组")
    public Result deleteTurbine(@RequestParam String windFieldName, @RequestParam String turbineId) {
        return turbineService.deleteTurbine(windFieldName, turbineId);
    }

    @PostMapping("/edit_turbine")
    @ApiOperation(value = "修改一个机组")
    @OperationLogAnnotation(module = "turbine", operationType = "POST", operationDesc = "修改一个机组")
    public Result editTurbine(@RequestBody Turbine turbine, @RequestParam(defaultValue = "1") int pageNum) {
        return turbineService.editTurbine(turbine, pageNum);
    }

    @GetMapping("/get_normal_turbine")
    @ApiOperation(value = "获取所有正常机组")
    @OperationLogAnnotation(module = "turbine", operationType = "GET", operationDesc = "获取所有正常机组")
    public Result getAllNormalTurbine(@RequestParam(defaultValue = "1") int pageNum) {
        return turbineService.getAllNormalTurbines(pageNum);
    }

    @GetMapping("/get_fault_turbine")
    @ApiOperation(value = "获取所有故障机组")
    @OperationLogAnnotation(module = "turbine", operationType = "GET", operationDesc = "获取所有故障机组")
    public Result getAllFaultTurbine(@RequestParam(defaultValue = "1") int pageNum) {
        return turbineService.getAllFaultTurbines(pageNum);
    }

    @GetMapping("/extemplate")
    @ApiOperation(value = "导出机组信息表模板")
    @OperationLogAnnotation(module = "TurbineManage", operationType = "GET", operationDesc = "导出机组信息表模板")
    public void downloadModule(HttpServletResponse response) throws IOException {
        //从数据库中读出所有数据
        List<Turbine> turbineList = new ArrayList<>();
        //返回输出流excel格式
        String sheetName = "机组信息表模板";
        String fileName = sheetName.concat(".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //设置字符集为utf-8
        response.setCharacterEncoding("UTF-8");
        //使前端可以获取文件名解码转成中文
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //调用EasyExcel的write方法生成表格返回给用户
        EasyExcel.write(response.getOutputStream(), Turbine.class).autoCloseStream(Boolean.FALSE).sheet("机组信息表模板").doWrite(turbineList);
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出机组信息Excel")
    @OperationLogAnnotation(module = "TurbineManage", operationType = "GET", operationDesc = "导出机组信息表")
    public void exportExcel(HttpServletResponse response) throws IOException {
        LambdaQueryWrapper<Turbine> queryWrapper = new LambdaQueryWrapper<>();
        new ExportListener<>(turbineService)
                .exportExcel(response, "机组信息表", Turbine.class, queryWrapper);
    }

    @PostMapping(value = "/import")
    @ApiOperation(value = "导入机组信息Excel")
    @OperationLogAnnotation(module = "TurbineManage", operationType = "POST", operationDesc = "导入机组信息表")
    public void importExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Turbine.class,
                new ImportListener<>(turbineService)).sheet().headRowNumber(1).doRead();
    }
}
