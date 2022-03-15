package com.ruicheng.blog.initializerstart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ajax
 *
 * @author ：Ruicheng
 * @date ：Created in 2022/3/8 18:03
 */

@Controller
@RequestMapping("/ajax")
public class AjaxController {
    @RequestMapping("/a1")
    public void ajax(String name, HttpServletResponse response) throws IOException{
        response.getWriter().print("true");
    }

}
