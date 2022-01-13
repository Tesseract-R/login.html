package com.ruicheng.blog.initializerstart.controller;

import com.ruicheng.blog.initializerstart.domain.Class;
import com.ruicheng.blog.initializerstart.domain.User;
import com.ruicheng.blog.initializerstart.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

/**
 * 班级管理控制器
 *
 * @author ：Ruicheng
 * @date ：Created in 2022/1/12 16:39
 */
@Controller
@RequestMapping("/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    /**
     * 查询所有班级
     *
     * @param model
     * @return
     */
    @GetMapping
    public ModelAndView list(@RequestParam(value = "async", required = false) boolean async,
                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                             @RequestParam(value = "name", required = false, defaultValue = "") String name,
                             Model model) {

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Class> page = classService.listClassesByNameLike(name, pageable);
        List<Class> list = page.getContent();    // 当前所在页面数据列表

        model.addAttribute("page", page);
        model.addAttribute("title", "班级列表");
        model.addAttribute("classList", list);
        return new ModelAndView(async ? "classes/list :: #mainContainerRepleace" : "classes/list", "classModel", model);
    }

    /**
     * 查询班级接口
     *
     * @param model
     * @return
     */
    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id, Model model) {
        Class c = classService.getClassById(id);
        Set<User> teachers = c.getTeachers();
        Set<User> students = c.getStudents();

        model.addAttribute("class", c);
        model.addAttribute("title", "班级信息");
        model.addAttribute("adminList", teachers);
        model.addAttribute("studentList", students);

        return new ModelAndView("classes/view", "classModel", model);
    }

    /**
     * 获取创建表单页面
     *
     * @param model
     * @return
     */
    @GetMapping("/addClass")
    public ModelAndView createForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        String currentTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        Class newClass = new Class(null, null, currentUser.getUsername(), currentTime, null);
        model.addAttribute("class", newClass);
        model.addAttribute("title", "创建新班级");
        return new ModelAndView("classes/form", "classModel", model);
    }

    /**
     * 新建班级
     *
     * @param c
     * @return
     */
    @PostMapping
    public ModelAndView saveOrUpdateClass(Class c) {
        try {
            if (c.getId() == null) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                User currentUser = (User) auth.getPrincipal();
                c.addTeacher(currentUser);
                c.setUserNum(c.getStudents().size() + c.getTeachers().size()); // 更新班级人数
                classService.saveClass(c);
            } else {
                c.setTeachers(classService.getClassById(c.getId()).getTeachers());
                c.setStudents(classService.getClassById(c.getId()).getStudents());
                c.setUserNum(c.getStudents().size() + c.getTeachers().size());
                classService.saveClass(c);
            }
        } catch (ConstraintViolationException e) {
        }
        return new ModelAndView("redirect:/classes");
    }

    /**
     * 修改用户信息
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        Class c = classService.getClassById(id);
        model.addAttribute("class", c);
        model.addAttribute("title", "修改班级");
        return new ModelAndView("classes/form", "classModel", model);
    }

    /**
     * 删除用户
     */
    @GetMapping("/delete/{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        classService.removeClass(id);
        return new ModelAndView("redirect:/classes");
    }


}
