package com.example.lifeline.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.lifeline.entity.Role;
import com.example.lifeline.service.RoleManageService;
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
@RequestMapping("/RoleManage")
@Api(tags = "权限管理")
public class RoleManageControllor {
    @Autowired
    @Qualifier("RoleManageService")
    private RoleManageService roleManageService;

    @GetMapping("/roleinfo")
    @ApiOperation(value = "显示权限信息")
    @OperationLogAnnotation(module = "roleManagement", operationType = "GET", operationDesc = "权限信息")
    public Result getAllRole(@RequestParam(defaultValue = "1") Integer pageNum) {
        return Result.ok().data("page", roleManageService.getAllRole(pageNum));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改权限信息")
    @OperationLogAnnotation(module = "roleManagement", operationType = "POST", operationDesc = "修改权限信息")
    public Result update(@RequestBody Role role, @RequestParam(defaultValue = "1") Integer pageNum) {
        try {
            roleManageService.updateRole(role);
        } catch (Exception e) {
            return Result.error().message(e.getMessage());
        }
        return getAllRole(pageNum).message("修改成功");
    }

    @PostMapping("/roleadd")
    @ApiOperation(value = "增加权限信息")
    @OperationLogAnnotation(module = "roleManagement", operationType = "POST", operationDesc = "增加权限信息")
    public Result save(@RequestBody Role role) {
        roleManageService.save(role);
        return Result.ok();
    }

    @GetMapping("/del")
    @ApiOperation(value = "删除权限信息")
    @OperationLogAnnotation(module = "roleManagement", operationType = "GET", operationDesc = "删除权限信息")
    public Result delete(@RequestParam String role) {
        roleManageService.del(role);
        return Result.ok();
    }

    @GetMapping("/dynamic_search")
    @ApiOperation(value = "动态获取权限信息")
    @OperationLogAnnotation(module = "roleManagement", operationType = "GET", operationDesc = "动态获取权限信息")
    public Result getRoles(@RequestParam String role) {
        return roleManageService.getRoles(role);
    }

    @GetMapping("/extemplate")
    @ApiOperation(value = "导出权限信息表模板")
    @OperationLogAnnotation(module = "roleManagement", operationType = "GET", operationDesc = "导出权限信息表模板")
    public void downloadModule(HttpServletResponse response) throws IOException {
        //从数据库中读出所有数据
        List<Role> roleList = new ArrayList<>();
        //返回输出流excel格式
        String sheetName = "权限信息表模板";
        String fileName = sheetName.concat(".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //设置字符集为utf-8
        response.setCharacterEncoding("UTF-8");
        //使前端可以获取文件名解码转成中文
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //调用EasyExcel的write方法生成表格返回给用户
        EasyExcel.write(response.getOutputStream(), Role.class).autoCloseStream(Boolean.FALSE).sheet("权限信息表模板").doWrite(roleList);
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出权限信息excel")
    @OperationLogAnnotation(module = "roleManagement", operationType = "GET", operationDesc = "导出权限信息表")
    public void exportExcel(HttpServletResponse response) throws IOException {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        new ExportListener<>(roleManageService)
                .exportExcel(response, "权限信息表",Role.class, queryWrapper);
    }

    @PostMapping(value = "/import")
    @ApiOperation(value = "导入权限信息excel")
    @OperationLogAnnotation(module = "roleManagement", operationType = "POST", operationDesc = "导入权限信息表")
    public void importExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Role.class,
                new ImportListener<>(roleManageService)).sheet().headRowNumber(1).doRead();
    }

}
