package com.ruicheng.blog.initializerstart.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/18 0:48
 */
@RestController
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/eva")
    public ModelAndView viewEvaluation(Model model){
        model.addAttribute("title", "看板 - 个人评价");

        return new ModelAndView("Board/view", "boardModel", model);
    }



}
