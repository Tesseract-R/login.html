package com.ruicheng.blog.initializerstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author ：Ruicheng
 * @date ：Created in 2021/11/9 17:34
 * @description：Hello 控制器
 * @modified By：
 * @version: 1.0$
 */

@SpringBootApplication
public class Application extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //这里配置静态资源文件的路径导包都是默认的直接导入就可以
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        super.addResourceHandlers(registry);
    }

}
