package com.ruicheng.blog.initializerstart.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello控制器
 *
 * @author ：Ruicheng
 * @date ：Created in 2021/11/9 17:35
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String Hello(){
        return "Hello World!";
    }
}
