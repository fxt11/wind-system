package com.example.lifeline.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.User;
import com.example.lifeline.mapper.UserMapper;
import com.example.lifeline.service.UserService;
import com.example.lifeline.utils.result.Result;
import com.example.lifeline.utils.token.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DefaultPasswordEncoderImpl passwordEncoder;

    String pass;

    public Result login(User user) {
        List<User> list = getAllUsers();
        HashMap<String, String> uid_password = new HashMap<>();
        for (User u : list) {
            uid_password.put(u.getUid(), u.getPassword());
        }
        if (!(uid_password.containsKey(user.getUid()))) {
            //用户账号不存在的情况
            Result response = Result.error();
            response.setMessage("用户不存在！");
            return response;
        } else if (!passwordEncoder.matches(user.getPassword(), uid_password.get(user.getUid()))) {
            //用户密码错误的情况
            Result response = Result.error();
            response.setMessage("密码错误！");
            return response;
        } else {
            //成功则返回带有token的数据包
            //根据用户uid生成token
            String token = JwtUtils.generateToken(user.getUid());
            pass = user.getPassword();
            //返回数据包
            return Result.ok().data("token", token);
        }
    }

    //获取用户信息
    public Result info(String token) {
        String uid = JwtUtils.getClaimsByToken(token).getSubject();
        String name = getName(uid);
        String avatar = getAvatar(uid);
        String password = pass;
        String role = getRoles(uid);
        List<String> roles = new ArrayList<>();
        roles.add(role);
        return Result.ok().data("uid", uid).data("name", name).data("avatar", avatar).data("password", password).data("roles", roles);
    }

    //修改用户名
    public Result submit(User newUser) {
        String name = newUser.getName();
        updatenamebyid(newUser);
        return Result.ok().data("name", name);
    }

    //接收上传的头像保存到本地
    public String upload(MultipartFile img) {
        //1. 取出文件原始名称
        String originalFilename = img.getOriginalFilename();
        //2. 为了防止文件名称重复导致覆盖，给每个文件定义一个唯一的名称
        String newFileName = UUID.randomUUID().toString().replace("-", "") + originalFilename;
        //3. 获取程序运行目录，此为本机运行环境
//        String dirPath = System.getProperty("user.dir") + "/src/main/resources/static";
        //以下为配置在服务器上面的时候，将头像存入jar同级目录下
        String dirPath = "/usr/local/wind-turbine-management-system/static";
        //4. 拼接文件存储路径，本机环境下最终存储到项目的static目录下
        String path = "/" + newFileName;
        File destFile = new File(dirPath + path);
        //5. 如果static目录不存在则创建
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();
        try {
            //6. 将前端传来的文件存储到目标路径
            img.transferTo(destFile);
            //7. 将url路径返回给前端，用于显示图片
//            return "http://localhost:8081" + path;
            //服务器下就是生成
            return "http://101.43.186.37:8081" + path;
        } catch (IOException e) {
            return null;
        }
    }

    //对数据库里的头像进行更新
    public Result putavatar(User user) {
        String avatar = user.getAvatar();
        updatenamebyid(user);
        return Result.ok().data("avatar", avatar);
    }

    //修改密码
    public Result changepassword(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        updatenamebyid(user);
        return Result.ok().data("password", password);
    }

    //查询所有用户信息
    public List<User> getAllUsers() {
        return userMapper.selectList(null);
    }

    //根据uid查询用户的名字
    public String getName(String id) {
        return userMapper.selectById(id).getName();
    }

    //根据uid查询用户的头像
    public String getAvatar(String id) {
        return userMapper.selectById(id).getAvatar();
    }

    //根据uid查询用户的密码
    public String getPassword(String id) {
        return userMapper.selectById(id).getPassword();
    }

    //根据uid查询用户的密码
    public String getRoles(String id) {
        return userMapper.selectById(id).getRoles();
    }

    //根据uid更新数据库
    public void updatenamebyid(User newuser) {
        userMapper.updateById(newuser);
    }

    //创建用户
    public Result addUser(User user, int pageNum) {
        user.setPassword(passwordEncoder.encode("zghcb" + user.getUid()));
        userMapper.insert(user);
        return getUsers(pageNum);
    }

    //查看所有用户
    public Result getUsers(int pageNum) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("uid", "name", "roles");
        Page<User> page = new Page<>(pageNum, 10);
        IPage<User> iPage = userMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    //根据uid搜索人员信息
    public Result searchUserByUid(String uid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).select("uid", "name", "roles");
        Page<User> page = new Page<>(1, 10);
        IPage<User> iPage = userMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    //根据name搜索人员信息
    public Result searchUserByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name).select("uid", "name", "roles");
        Page<User> page = new Page<>(1, 10);
        IPage<User> iPage = userMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    //删除用户
    public Result deleteUser(String uid, int pageNum) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        userMapper.delete(wrapper);
        return getUsers(pageNum);
    }

    //修改用户身份
    public Result updateUserRole(User user, int pageNum) {
        userMapper.updateById(user);
        return getUsers(pageNum);
    }

    //批量插入数据
    @Override
    public void batchInsert(List<User> list) {
        userMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Long selectCount(Wrapper<User> queryWrapper) {
        return userMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<User>> P selectPage(P page, Wrapper<User> queryWrapper) {
        return userMapper.selectPage(page, queryWrapper);
    }

}
