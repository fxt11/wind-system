package com.example.lifeline.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.Newparts;
import com.example.lifeline.service.NewpartsService;
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
@RequestMapping("/NewpartsManage")
@Api(tags = "新件管理")
public class NewpartsController {

    @Autowired
    @Qualifier("NewpartsService")
    private NewpartsService newpartsService;

    @GetMapping("/deviceinfo")
    @ApiOperation(value = "显示新件信息")
    @OperationLogAnnotation(module = "newPartsManagement", operationType = "GET", operationDesc = "新件信息")
    public Result getAllNewParts(@RequestParam(defaultValue = "1") Integer pageNum) {
        return Result.ok().data("page", newpartsService.getAllNewParts(pageNum));
    }

    @GetMapping("/selectTurbineId")
    @ApiOperation(value = "查询新件（使用新件的风机设备号码）")
    @OperationLogAnnotation(module = "newPartsManagement", operationType = "GET", operationDesc = "使用新件的风机设备号码查询新件")
    public Result select(@RequestParam Integer turbineId) {
        IPage<Newparts> iPage = new Page<>();
        try {
            iPage = newpartsService.selectNewParts(turbineId);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        return Result.ok().data("page", iPage);
    }

    @GetMapping("/selectPartName")
    @ApiOperation(value = "查询新件（新件名称）")
    @OperationLogAnnotation(module = "newPartsManagement", operationType = "GET", operationDesc = "使用新件名称查询新件")
    public Result select(@RequestParam String partName) {
        IPage<Newparts> iPage = new Page<>();
        try {
            iPage = newpartsService.selectNewParts(partName);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        return Result.ok().data("page", iPage);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加新件")
    @OperationLogAnnotation(module = "newPartsManagement", operationType = "POST", operationDesc = "添加新件")
    public Result add(@RequestBody Newparts newparts, @RequestParam(defaultValue = "1") Integer pageNum) {
        try {
            newpartsService.addNewparts(newparts);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        return getAllNewParts(pageNum).message("添加成功");
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除备件")
    @OperationLogAnnotation(module = "newPartsManagement", operationType = "POST", operationDesc = "删除新件")
    public Result delete(@RequestBody Newparts newparts) {
        try {
            newpartsService.deleteNewparts(newparts);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        return Result.ok().message("删除成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改备件信息")
    @OperationLogAnnotation(module = "newPartsManagement", operationType = "POST", operationDesc = "修改备件信息")
    public Result update(@RequestBody Newparts newparts, @RequestParam(defaultValue = "1") Integer pageNum) {
        try {
            newpartsService.updateNewparts(newparts);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        return getAllNewParts(pageNum).message("修改成功");
    }

    @GetMapping("/extemplate")
    @ApiOperation(value = "导出新件信息表模板")
    @OperationLogAnnotation(module = "newPartsManagement", operationType = "GET", operationDesc = "导出新件信息表模板")
    public void downloadModule(HttpServletResponse response) throws IOException {
        //从数据库中读出所有数据
        List<Newparts> newPartList = new ArrayList<>();
        //返回输出流excel格式
        String sheetName = "新件信息表模板";
        String fileName = sheetName.concat(".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //设置字符集为utf-8
        response.setCharacterEncoding("UTF-8");
        //使前端可以获取文件名解码转成中文
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //调用EasyExcel的write方法生成表格返回给用户
        EasyExcel.write(response.getOutputStream(), Newparts.class).autoCloseStream(Boolean.FALSE).sheet("新件信息表模板").doWrite(newPartList);
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出新件信息excel")
    @OperationLogAnnotation(module = "newPartsManagement", operationType = "GET", operationDesc = "导出新件信息表")
    public void exportExcel(HttpServletResponse response) throws IOException {
        LambdaQueryWrapper<Newparts> queryWrapper = new LambdaQueryWrapper<>();
        new ExportListener<>(newpartsService)
                .exportExcel(response, "新件信息表", Newparts.class, queryWrapper);
    }

    @PostMapping(value = "/import")
    @ApiOperation(value = "导入新件信息excel")
    @OperationLogAnnotation(module = "newPartsManagement", operationType = "POST", operationDesc = "导入新件信息表")
    public void importExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Newparts.class,
                new ImportListener<>(newpartsService)).sheet().headRowNumber(1).doRead();
    }

}
