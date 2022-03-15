package com.ruicheng.blog.initializerstart.controller;

import com.ruicheng.blog.initializerstart.domain.Class;
import com.ruicheng.blog.initializerstart.domain.User;
import com.ruicheng.blog.initializerstart.service.ClassService;
import com.ruicheng.blog.initializerstart.service.UserService;
import com.ruicheng.blog.initializerstart.util.ConstraintViolationExceptionHandler;
import com.ruicheng.blog.initializerstart.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")  // 指定角色权限才能操作方法
public class ClassController {

    @Autowired
    private ClassService classService;

    @Autowired
    private UserService userService;

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
    @GetMapping("/{id}")
    public ModelAndView view(@PathVariable("id") Long id, Model model) {
        Class c = classService.getClassById(id);
        Set<User> teachers = c.getTeachers();
        List<User> students = c.getStudentList();

        model.addAttribute("class", c);
        model.addAttribute("title", "班级信息");
        model.addAttribute("adminList", teachers);
        model.addAttribute("studentList", students);

        return new ModelAndView("classes/view", "classModel", model);
    }

    /**
     * 获取 form 表单页面
     * @param model
     * @return
     */
    @GetMapping("/add")
    public ModelAndView addForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        String currentTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        Class newClass = new Class(null, null, currentUser.getUsername(), currentTime, null);
        model.addAttribute("class", newClass);
        return new ModelAndView("classes/form", "classModel", model);
    }

    /**
     * 新建班级
     * @param c
     * @return
     */
    @PostMapping
    public ResponseEntity<Response> saveOrUpdateClass(Class c) {
        System.out.println("save class " + c.getClassname() + c.getInfo() );
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
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", c));
    }

    /**
     * 修改用户信息
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        Class c = classService.getClassById(id);
        model.addAttribute("class", c);
        model.addAttribute("title", "修改班级");
        return new ModelAndView("classes/form", "classModel", model);
    }

    /**
     * 删除班级
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model) {
        try {
            classService.removeClass(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new Response(true, "处理成功"));
    }


    /**
     * 班级添加用户
     */
    @GetMapping("/{classid}/add{Role}")
    public ModelAndView addStudent(@PathVariable("classid") Long id, @PathVariable("Role") String role, Model model) {
        Class c = classService.getClassById(id);
        String title;
        List<User> selectedList;
        if (role.equals("Student")) {
            title = "添加学生";
            selectedList = c.getStudentList();
        } else {
            title = "添加管理员";
            selectedList = new ArrayList<>(c.getTeachers());
        }
        ArrayList<User> userList = (ArrayList<User>) userService.listUsers();
        for (User user : userList) {
            user.setTmpField("False");
        }
        for (User user : selectedList) {
            user.setTmpField("True");
        }
        userService.saveAll(userList);
        userService.saveAll(selectedList);
        userList = (ArrayList<User>) userService.listUsers();
        System.out.println(userList);
        model.addAttribute("title", title);
        model.addAttribute("role", role);
        model.addAttribute("c", c);
        model.addAttribute("userList", userList);
        return new ModelAndView("classes/addUser", "classModel", model);
    }

    /**
     * 班内增加用户
     */
    @GetMapping("/{classid}/add{role}/{id}")
    public ModelAndView addUserToClass(@PathVariable("classid") Long classid, @PathVariable("role") String role, @PathVariable("id") Long userid) {
        Class currentClass = classService.getClassById(classid);
        User userToBeAdded = userService.getUserById(userid);
        if (role.equals("Admin"))
            currentClass.addTeacher(userToBeAdded);
        else if (role.equals("Student"))
            currentClass.addStudent(userToBeAdded);
        classService.saveClass(currentClass);
        return new ModelAndView("redirect:/classes/{classid}/add{role}");
    }


    /**
     * 班内删除用户
     */
    @GetMapping("/{classid}/delete{role}/{id}")
    public ModelAndView removeUserFromClass(@PathVariable("classid") Long classid, @PathVariable("role") String role, @PathVariable("id") Long userid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        Class currentClass = classService.getClassById(classid);
        User userToBeRemoved = userService.getUserById(userid);
        if (currentUser.getUsername().equals(currentClass.getCreator()) || !role.equals("admin")) {
            if (!userid.equals(currentUser.getId()) || !role.equals("admin")) {
                if (role.equals("Admin"))
                    currentClass.removeTeacher(userToBeRemoved);
                else if (role.equals("Student"))
                    currentClass.removeStudent(userToBeRemoved);
            }
        }
        classService.saveClass(currentClass);
        return new ModelAndView("redirect:/classes/{classid}");
    }
}
