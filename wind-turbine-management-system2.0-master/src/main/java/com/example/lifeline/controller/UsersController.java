package com.example.lifeline.controller;

import com.example.lifeline.entity.User;
import com.example.lifeline.service.UserService;
import com.example.lifeline.utils.log.OperationLogAnnotation;
import com.example.lifeline.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Api(tags = "人员管理")
public class UsersController {
    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    @ApiOperation(value = "添加人员")
    @OperationLogAnnotation(module = "userManagement", operationType = "POST", operationDesc = "添加人员")
    public Result addUser(@RequestBody User user, @RequestParam(defaultValue = "1") int pageNum) {
        return userService.addUser(user, pageNum);
    }

    @GetMapping("/info")
    @ApiOperation(value = "查看所有人员信息")
    @OperationLogAnnotation(module = "userManagement", operationType = "GET", operationDesc = "查看所有人员信息")
    public Result getUsers(@RequestParam(defaultValue = "1") int pageNum) {
        return userService.getUsers(pageNum);
    }

    @GetMapping("/searchByUid")
    @ApiOperation(value = "根据uid搜索人员信息")
    @OperationLogAnnotation(module = "userManagement", operationType = "GET", operationDesc = "根据uid搜索人员信息")
    public Result getUserByUid(@RequestParam String uid) {
        return userService.searchUserByUid(uid);
    }

    @GetMapping("/searchByName")
    @ApiOperation(value = "根据name搜索人员信息")
    @OperationLogAnnotation(module = "userManagement", operationType = "GET", operationDesc = "根据name搜索人员信息")
    public Result getUsersByName(@RequestParam String name) {
        return userService.searchUserByName(name);
    }

    @GetMapping("/deleteUser")
    @ApiOperation(value = "删除用户")
    @OperationLogAnnotation(module = "userManagement", operationType = "GET", operationDesc = "删除用户")
    public Result deleteUser(@RequestParam String uid, @RequestParam(defaultValue = "1") int pageNum) {
        return userService.deleteUser(uid, pageNum);
    }

    @PostMapping("/updateRoles")
    @ApiOperation(value = "修改用户身份")
    @OperationLogAnnotation(module = "userManagement", operationType = "POST", operationDesc = "修改用户身份")
    public Result deleteUser(@RequestBody User user, @RequestParam(defaultValue = "1") int pageNum) {
        return userService.updateUserRole(user, pageNum);
    }

}
