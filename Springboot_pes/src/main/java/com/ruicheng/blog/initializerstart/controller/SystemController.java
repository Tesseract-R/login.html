package com.ruicheng.blog.initializerstart.controller;

import com.ruicheng.blog.initializerstart.domain.SystemConfig;
import com.ruicheng.blog.initializerstart.service.SystemConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * 系统配置控制器
 *
 * @author ：Ruicheng
 * @date ：Created in 2022/1/15 18:01
 */
@RestController
@RequestMapping("/system")
@PreAuthorize("hasRole('ADMIN')")  // 指定角色权限才能操作方法
public class SystemController {
    @Autowired
    SystemConfigServiceImpl systemConfigService;


    @GetMapping
    public ModelAndView showPage(Model model) {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        model.addAttribute("title", "系统设置");
        model.addAttribute("systemConfig", systemConfig);
        return new ModelAndView("system", "systemModel", model);
    }

    @PostMapping
    public ModelAndView updateystem(SystemConfig systemConfig, Model model) {
        systemConfigService.setSystemConfig(systemConfig);
        model.addAttribute("title", "系统设置");
        model.addAttribute("systemConfig", systemConfig);
        return new ModelAndView("system", "systemModel", model);
    }
}
