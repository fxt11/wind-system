package com.example.lifeline.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\";
//        //配置静态资源访问路径
        registry.addResourceHandler("/**").addResourceLocations("file:" + path);
        //以上为本机运行时，将头像放在lifeline包下的静态资源static文件夹上

        //以下为配置服务器时的资源文件路径，路径在部署jar包同级目录。
//        String path = "/usr/local/wind-turbine-management-system/static/";
//        File dir = new File(path);
        //如果不存在则创建目录
//        if(!dir.exists()){
//        dir.mkdirs();
//        }
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/","file:static/");
    }
}
