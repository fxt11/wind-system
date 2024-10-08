package com.example.lifeline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {
    @Bean(value = "dockerBean")
    public Docket dockerBean() {
        //指定使用Swagger2规范
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //描述字段支持Markdown语法
                        .title("风电机组关键设备全寿命周期管理系统")
                        .description("# wind-turbine-management-system ")
                        .termsOfServiceUrl("https://127.0.0.1:8081")
                        .contact(new Contact("物联网大实验小组", "", ""))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("1.X 版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.example.lifeline.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
