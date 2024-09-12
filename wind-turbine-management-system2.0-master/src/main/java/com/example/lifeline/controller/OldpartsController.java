package com.example.lifeline.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.Oldparts;
import com.example.lifeline.service.OldpartsService;
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
@RequestMapping("OldpartsManage")
@Api(tags = "返修件管理")
public class OldpartsController {

    @Autowired
    @Qualifier("OldpartsService")
    OldpartsService oldpartsService;

    @GetMapping("/deviceinfo")
    @ApiOperation(value = "展示返修件信息")
    @OperationLogAnnotation(module = "oldPartsManagement", operationType = "GET", operationDesc = "展示返修件信息")
    public Result getAllOldParts(@RequestParam(defaultValue = "1") Integer pageNum) {
        return Result.ok().data("page", oldpartsService.getAllOldParts(pageNum));
    }

    @GetMapping("/selectTurbineId")
    @ApiOperation(value = "查询返修件（使用返修件的风机设备号码）")
    @OperationLogAnnotation(module = "oldPartsManagement", operationType = "GET", operationDesc = "使用返修件的风机设备号码查询返修件")
    public Result select(@RequestParam Integer turbineId) {
        IPage<Oldparts> iPage = new Page<>();
        try {
            iPage = oldpartsService.selectOldParts(turbineId);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        return Result.ok().data("page", iPage);
    }

    @GetMapping("/selectPartName")
    @ApiOperation(value = "查询返修件（返修件名称）")
    @OperationLogAnnotation(module = "oldPartsManagement", operationType = "GET", operationDesc = "返修件名称查询返修件")
    public Result select(@RequestParam String partName) {
        IPage<Oldparts> iPage = new Page<>();
        try {
            iPage = oldpartsService.selectOldParts(partName);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        return Result.ok().data("page", iPage);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加返修件")
    @OperationLogAnnotation(module = "oldPartsManagement", operationType = "POST", operationDesc = "添加返修件")
    public Result add(@RequestBody Oldparts oldparts, @RequestParam(defaultValue = "1") Integer pageNum) {
        try {
            oldpartsService.addOldparts(oldparts);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        return getAllOldParts(pageNum).message("添加成功");
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除返修件")
    @OperationLogAnnotation(module = "oldPartsManagement", operationType = "POST", operationDesc = "删除返修件")
    public Result delete(@RequestParam Integer oldpartsId) {
        try {
            oldpartsService.deleteOldparts(oldpartsId);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        return Result.ok().message("删除成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改返修件信息")
    @OperationLogAnnotation(module = "oldPartsManagement", operationType = "POST", operationDesc = "修改返修件信息")
    public Result update(@RequestBody Oldparts oldparts, @RequestParam(defaultValue = "1") Integer pageNum) {
        try {
            oldpartsService.updateOldparts(oldparts);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        return getAllOldParts(pageNum).message("修改成功");
    }

    @GetMapping("/extemplate")
    @ApiOperation(value = "导出返修件信息表模板")
    @OperationLogAnnotation(module = "oldPartsManagement", operationType = "GET", operationDesc = "导出返修件信息表模板")
    public void downloadModule(HttpServletResponse response) throws IOException {
        //从数据库中读出所有数据
        List<Oldparts> oldpartsList = new ArrayList<>();
        //返回输出流excel格式
        String sheetName = "返修件信息表模板";
        String fileName = sheetName.concat(".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //设置字符集为utf-8
        response.setCharacterEncoding("UTF-8");
        //使前端可以获取文件名解码转成中文
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //调用EasyExcel的write方法生成表格返回给用户
        EasyExcel.write(response.getOutputStream(), Oldparts.class).autoCloseStream(Boolean.FALSE).sheet("返修件信息表模板").doWrite(oldpartsList);
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出返修件信息excel")
    @OperationLogAnnotation(module = "oldPartsManagement", operationType = "GET", operationDesc = "导出返修件信息表")
    public void exportExcel(HttpServletResponse response) throws IOException {
        LambdaQueryWrapper<Oldparts> queryWrapper = new LambdaQueryWrapper<>();
        new ExportListener<>(oldpartsService)
                .exportExcel(response, "返修件信息表", Oldparts.class, queryWrapper);
    }

    @PostMapping(value = "/import")
    @ApiOperation(value = "导入返修件信息excel")
    @OperationLogAnnotation(module = "oldPartsManagement", operationType = "POST", operationDesc = "导入返修件信息")
    public void importExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Oldparts.class,
                new ImportListener<>(oldpartsService)).sheet().headRowNumber(1).doRead();
    }

}
