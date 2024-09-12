package com.example.lifeline.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.lifeline.entity.FaultManage;
import com.example.lifeline.entity.Maintainer;
import com.example.lifeline.service.MaintainerService;
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
@RequestMapping("/maintainer")
@Api(tags = "维修人员管理")
public class MaintainerController {

    @Autowired
    @Qualifier("MaintainerService")
    private MaintainerService maintainerService;

    //根据姓名查询维修人员
    @GetMapping("/select")
    @ApiOperation(value = "通过姓名查询")
    @OperationLogAnnotation(module = "maintainerManagement", operationType = "GET", operationDesc = "根据姓名搜索维修人员")
    //前端发送姓名，查询信息
    public Result select(@RequestParam String uname) {
        return Result.ok().data("page", maintainerService.select(uname));
    }

    //根据工号查询维修人员
    @GetMapping("/selectNum")
    @ApiOperation(value = "通过工号查询")
    @OperationLogAnnotation(module = "maintainerManagement", operationType = "GET", operationDesc = "根据工号搜索维修人员")
    //前端发送工号，查询信息
    public Result selectNum(@RequestParam String jobNumber) {
        return Result.ok().data("page", maintainerService.selectNum(jobNumber));
    }

    //信息分页全显示
    @GetMapping("/list")
    @ApiOperation(value = "人员信息")
    @OperationLogAnnotation(module = "maintainerManagement", operationType = "GET", operationDesc = "获得所有维修人员")
    //列表显示
    public Result list(@RequestParam(defaultValue = "1") int pageNum) {
        return Result.ok().data("page", maintainerService.page(pageNum));
    }

    //新增人员
    @PostMapping("/save")
    @ApiOperation(value = "新增人员")
    @OperationLogAnnotation(module = "maintainerManagement", operationType = "POST", operationDesc = "添加维修人员")
    //前端传入新增成员信息
    public Result save(@RequestBody Maintainer maintainer) {
        maintainer.setStartProcessTime(null);
        maintainer.setMaintenanceContents(null);
        maintainerService.save(maintainer);
        return Result.ok();
    }

    //删除人员
    @PostMapping("/del")
    @ApiOperation(value = "删除人员")
    @OperationLogAnnotation(module = "maintainerManagement", operationType = "DELETE", operationDesc = "删除维修人员")
    //前端传入该成员工号
    public Result save(@RequestParam String jobNumber) {
        maintainerService.del(jobNumber);
        return Result.ok();
    }

    //修改权限
    @PostMapping("/edit")
    @ApiOperation(value = "修改权限")
    @OperationLogAnnotation(module = "maintainerManagement", operationType = "POST", operationDesc = "修改维修人员")
    //前端传入该成员工号,role，这里好像只能放一个@RequestBody，发消息时就用Maintainer封装一下
    public Result update(@RequestBody Maintainer maintainer) {
        maintainer.setStartProcessTime(null);
        maintainer.setMaintenanceContents(null);
        maintainerService.update(maintainer);
        return Result.ok();
    }

    //新增维修内容，未确定人员
    @PostMapping("/update")
    @ApiOperation(value = "增加维修内容")
    @OperationLogAnnotation(module = "maintainerManagement", operationType = "POST", operationDesc = "添加维修内容")
    public Result updateMaintain(@RequestBody Maintainer maintainer) {
        maintainer.setUname(null);
        maintainerService.update(maintainer);
        return Result.ok();
    }

    @GetMapping("/extemplate")
    @ApiOperation(value = "导出维修工作表模板")
    @OperationLogAnnotation(module = "maintainerManagement", operationType = "GET", operationDesc = "导出维修工作信息表模板")
    public void downloadModule(HttpServletResponse response) throws IOException {
        //从数据库中读出所有数据
        List<Maintainer> maintainerManagesList = new ArrayList<>();
        //返回输出流excel格式
        String sheetName = "维修工作信息表模板";
        String fileName = sheetName.concat(".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //设置字符集为utf-8
        response.setCharacterEncoding("UTF-8");
        //使前端可以获取文件名解码转成中文
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //调用EasyExcel的write方法生成表格返回给用户
        EasyExcel.write(response.getOutputStream(), Maintainer.class).autoCloseStream(Boolean.FALSE).sheet("维修工作信息表模板").doWrite(maintainerManagesList);
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出维修工作excel")
    @OperationLogAnnotation(module = "maintainerManagement", operationType = "GET", operationDesc = "导出维修工作信息表")
    public void exportExcel(HttpServletResponse response) throws IOException {
        LambdaQueryWrapper<Maintainer> queryWrapper = new LambdaQueryWrapper<>();
        new ExportListener<>(maintainerService)
                .exportExcel(response, "维修工作信息表", Maintainer.class, queryWrapper);
    }

    @PostMapping(value = "/import")
    @ApiOperation(value = "导入维修工作excel")
    @OperationLogAnnotation(module = "maintainerManagement", operationType = "POST", operationDesc = "导入维修工作信息表")
    public void importExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Maintainer.class,
                new ImportListener<>(maintainerService)).sheet().headRowNumber(1).doRead();
    }

}


