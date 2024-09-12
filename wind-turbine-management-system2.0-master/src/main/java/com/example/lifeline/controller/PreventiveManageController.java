package com.example.lifeline.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.lifeline.entity.PreventiveManage;
import com.example.lifeline.service.PreventiveManageService;
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
@RequestMapping("/preventiveManage")
@Api(tags = "预防性检修管理")
@CrossOrigin
public class PreventiveManageController {

    @Autowired
    @Qualifier("PreventiveManageService")
    PreventiveManageService preventiveManageService;

    @GetMapping("/all")
    @ApiOperation(value = "展示预防性检修清单")
    @OperationLogAnnotation(module = "preventiveManage", operationType = "GET", operationDesc = "展示预防性检修清单")
    public Result show(@RequestParam(defaultValue = "1") int pageNum) {
        return Result.ok().data("page", preventiveManageService.show(pageNum));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新建预防性检修")
    @OperationLogAnnotation(module = "preventiveManage", operationType = "POST", operationDesc = "新建预防性检修")
    public Result add(@RequestBody PreventiveManage preventiveManage) {
        try {
            preventiveManageService.addPrevent(preventiveManage);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        return Result.ok().message("检修安排成功！");
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除预防性检修")
    @OperationLogAnnotation(module = "preventiveManage", operationType = "GET", operationDesc = "删除预防性检修")
    public Result delete(@RequestParam String maintenanceTurbine) {
        preventiveManageService.deletePrevent(maintenanceTurbine);
        return Result.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改预防性检修")
    @OperationLogAnnotation(module = "preventiveManage", operationType = "PUT", operationDesc = "修改预防性检修")
    public Result update(@RequestBody PreventiveManage preventiveManage, @RequestParam(defaultValue = "1") int pageNum) {
        preventiveManageService.updatePrevent(preventiveManage);
        return show(pageNum).message("修改成功！");
    }

    @GetMapping("/extemplate")
    @ApiOperation(value = "导出检修信息表模板")
    @OperationLogAnnotation(module = "preventiveManage", operationType = "GET", operationDesc = "导出检修信息表模板")
    public void downloadModule(HttpServletResponse response) throws IOException {
        //从数据库中读出所有数据
        List<PreventiveManage> preventmanageList = new ArrayList<>();
        //返回输出流excel格式
        String sheetName = "检修信息表模板";
        String fileName = sheetName.concat(".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //设置字符集为utf-8
        response.setCharacterEncoding("UTF-8");
        //使前端可以获取文件名解码转成中文
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //调用EasyExcel的write方法生成表格返回给用户
        EasyExcel.write(response.getOutputStream(), PreventiveManage.class).autoCloseStream(Boolean.FALSE).sheet("检修信息表模板").doWrite(preventmanageList);
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出检修信息excel")
    @OperationLogAnnotation(module = "preventiveManage", operationType = "GET", operationDesc = "导出检修信息表")
    public void exportExcel(HttpServletResponse response) throws IOException {
        LambdaQueryWrapper<PreventiveManage> queryWrapper = new LambdaQueryWrapper<>();
        new ExportListener<>(preventiveManageService)
                .exportExcel(response, "检修信息表", PreventiveManage.class, queryWrapper);
    }

    @PostMapping(value = "/import")
    @ApiOperation(value = "导入检修信息excel")
    @OperationLogAnnotation(module = "preventiveManage", operationType = "POST", operationDesc = "导入检修信息表")
    public void importExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), PreventiveManage.class,
                new ImportListener<>(preventiveManageService)).sheet().headRowNumber(1).doRead();
    }

}
