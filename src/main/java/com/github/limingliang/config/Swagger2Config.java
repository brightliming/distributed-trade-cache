package com.github.limingliang.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    //读取配置文件中的enable，true为显示，false为隐藏
    @Value("${swagger2.enable}")
    private boolean enable;

    @Bean
    public Docket createDocke(){
        return new Docket(DocumentationType.SWAGGER_2)
                //进入swagger-ui的信息
                .apiInfo(apiInfo())
                .select()
                //暴露所有controller类的所在的包路径
                .apis(RequestHandlerSelectors.basePackage("com.github.limingliang.controller"))
                .paths(PathSelectors.any())
                .build()
                .enable(enable);
    }
    //进入swagger-ui的信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //该项目的名字
                .title("分布式交易表缓存系统")
                //该项目的描述
                .description("分布式交易表缓存")
                .version("1.0")
                .build();
    }
}
