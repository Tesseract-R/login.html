package com.ruicheng.blog.initializerstart.controller;


import com.ruicheng.blog.initializerstart.domain.Class;
import com.ruicheng.blog.initializerstart.domain.Exam;
import com.ruicheng.blog.initializerstart.domain.User;
import com.ruicheng.blog.initializerstart.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 用户主页空间控制器.
 *
 * @author Ruicheng
 */
@Controller
@RequestMapping("/c")
public class ClassSpaceController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    @Autowired
    private ExamService examService;

    @GetMapping
    public ModelAndView listClassSpace(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        List<Class> list = classService.listClasses();    // 当前所在页面数据列表
        List<Class> myClassAdmin = new ArrayList<>();
        List<Class> myClassStu = new ArrayList<>();

        for (Class c: list){
            if (classService.hasUserAsStudent(c,user))
                myClassStu.add(c);
            if (classService.hasUserAsTeacher(c,user))
                myClassAdmin.add(c);
        }

        model.addAttribute("classListS", myClassStu);
        model.addAttribute("classListA", myClassAdmin);
        model.addAttribute("title", "我的班级");

        return new ModelAndView("classspace/list", "classModel", model);
    }

    @GetMapping("profile/{id}")
    public ModelAndView getClassInfo(@PathVariable("id") Long classId, Model model){
        Class c = classService.getClassById(classId);
        model.addAttribute("class", c);
        return new ModelAndView("classspace/profile", "classModel", model);
    }

    @GetMapping("inputScore/{id}")
    public ModelAndView getInputScore(@PathVariable("id") Long classId, Model model){
        Class c = classService.getClassById(classId);
        String currentTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        Exam exam = new Exam(currentTime, c.getStudents());
        model.addAttribute("class", c);
        model.addAttribute("exam", exam);
        return new ModelAndView("classspace/inputScore", "classModel", model);
    }

    @PostMapping("inputScore/{classid}")
    public ModelAndView inputScore(@PathVariable("classid") Long classId, @RequestBody Exam exam) {
        System.out.println(exam);
        Class c = classService.getClassById(classId);
//        c.setExamList(c.addExam(exam));
//        classService.saveClass(c);

        return new ModelAndView("redirect:/c/profile/"+classId);
    }

}