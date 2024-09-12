package com.example.lifeline.service;

import com.example.lifeline.entity.User;
import com.example.lifeline.service.baseservice.BaseService;
import com.example.lifeline.utils.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService extends BaseService<User> {

    //用户登录
    Result login(User user);

    //获取用户信息
    Result info(String token);

    //修改用户名
    Result submit(User newUser);

    //接收上传的头像保存到本地
    String upload(MultipartFile img);

    //对数据库里的头像进行更新
    Result putavatar(User user);

    //修改密码
    Result changepassword(User user);

    //查询所有用户信息
    List<User> getAllUsers();

    //根据uid查询用户的名字
    String getName(String id);

    //根据uid查询用户的头像
    String getAvatar(String id);

    //根据uid查询用户的密码
    String getPassword(String id);

    //根据uid查询用户的角色
    String getRoles(String id);

    //根据uid更新数据库
    void updatenamebyid(User newuser);

    //添加人员
    Result addUser(User user, int pageNum);

    //查看所有人员信息
    Result getUsers(int pageNum);

    //根据uid搜索人员信息
    Result searchUserByUid(String uid);

    //根据name搜索人员信息
    Result searchUserByName(String name);

    //删除用户
    Result deleteUser(String uid, int pageNum);

    //修改用户身份
    Result updateUserRole(User user, int pageNum);
}
