package com.example.lifeline.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.lifeline.entity.User;
import com.example.lifeline.service.UserService;
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
@RequestMapping("/user")
@Api(tags = "用户操作")
public class UserController {

    @Autowired
    @Qualifier("UserService")
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    @OperationLogAnnotation(module = "userManagement", operationType = "POST", operationDesc = "用户登录")
    public Result login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping("/info")
    @OperationLogAnnotation(module = "userManagement", operationType = "GET", operationDesc = "获取用户信息")
    public Result info(String token) {
        return userService.info(token);
    }

    //修改用户名功能
    @PostMapping("/submit")
    @ApiOperation(value = "修改用户名")
    @OperationLogAnnotation(module = "userManagement", operationType = "POST", operationDesc = "修改用户名")
    public Result submit(@RequestBody User newUser) {
        return userService.submit(newUser);
    }

    //接收上传的头像保存到本地
    @RequestMapping("upload")
    @ApiOperation(value = "接收头像上传")
    @OperationLogAnnotation(module = "userManagement", operationType = "POST", operationDesc = "更新用户头像")
    public String upload(MultipartFile img) {
        return userService.upload(img);
    }

    //对数据库里的头像进行更新
    @PostMapping("/putavatar")
    @ApiOperation("数据库头像更新")
    public Result putavatar(@RequestBody User user) {
        return userService.putavatar(user);
    }

    @PostMapping("/changepassword")
    @ApiOperation(value = "修改密码")
    @OperationLogAnnotation(module = "userManagement", operationType = "POST", operationDesc = "修改密码")
    public Result changepassword(@RequestBody User user) {
        return userService.changepassword(user);
    }

    //登出按钮,清空token
    @PostMapping("/logout")
    @ApiOperation(value = "登出")
    @OperationLogAnnotation(module = "userManagement", operationType = "GET", operationDesc = "用户登出")
    public Result logout() {
        return Result.ok();
    }

    @GetMapping("/extemplate")
    @ApiOperation(value = "导出用户信息表模板")
    @OperationLogAnnotation(module = "userManagement", operationType = "GET", operationDesc = "导出用户信息表模板")
    public void downloadModule(HttpServletResponse response) throws IOException {
        //从数据库中读出所有数据
        List<User> userList = new ArrayList<>();
        //返回输出流excel格式
        String sheetName = "用户信息表模板";
        String fileName = sheetName.concat(".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //设置字符集为utf-8
        response.setCharacterEncoding("UTF-8");
        //使前端可以获取文件名解码转成中文
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //调用EasyExcel的write方法生成表格返回给用户
        EasyExcel.write(response.getOutputStream(), User.class).autoCloseStream(Boolean.FALSE).sheet("用户信息表模板").doWrite(userList);
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出用户信息excel")
    @OperationLogAnnotation(module = "userManagement", operationType = "GET", operationDesc = "导出用户信息表")
    public void exportExcel(HttpServletResponse response) throws IOException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        new ExportListener<>(userService)
                .exportExcel(response, "用户信息表", User.class, queryWrapper);
    }

    @PostMapping(value = "/import")
    @ApiOperation(value = "导入用户信息excel")
    @OperationLogAnnotation(module = "userManagement", operationType = "POST", operationDesc = "导入用户信息表")
    public void importExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), User.class,
                new ImportListener<>(userService)).sheet().headRowNumber(1).doRead();
    }

}
