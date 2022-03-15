package com.ruicheng.blog.initializerstart.controller;

import com.ruicheng.blog.initializerstart.domain.Echarts;
import com.ruicheng.blog.initializerstart.service.EchartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Hello控制器
 * For Test Only
 *
 * @author ：Ruicheng
 * @date ：Created in 2021/11/9 17:35
 */
@RestController
public class HelloController {
    @Autowired
    EchartService echartService;

    @GetMapping("/hello")
    public ModelAndView Hello(Model model) {
        return new ModelAndView("echart", "model", model);
    }
}
