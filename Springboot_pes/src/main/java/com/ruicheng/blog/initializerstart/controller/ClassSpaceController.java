package com.ruicheng.blog.initializerstart.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruicheng.blog.initializerstart.domain.*;
import com.ruicheng.blog.initializerstart.domain.Class;
import com.ruicheng.blog.initializerstart.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequestWrapper;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 用户主页空间控制器.
 *
 * @author Ruicheng
 */
@RestController
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

    @Autowired
    private ScoreService scoreService;

    @Autowired
    EchartService echartService;

    @GetMapping
    public ModelAndView listClassSpace(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        List<Class> list = classService.listClasses();    // 当前所在页面数据列表
        List<Class> myClassAdmin = new ArrayList<>();
        List<Class> myClassStu = new ArrayList<>();

        for (Class c : list) {
            if (classService.hasUserAsStudent(c, user))
                myClassStu.add(c);
            if (classService.hasUserAsTeacher(c, user))
                myClassAdmin.add(c);
        }

        model.addAttribute("classListS", myClassStu);
        model.addAttribute("classListA", myClassAdmin);
        model.addAttribute("title", "我的班级");

        return new ModelAndView("classspace/list", "classModel", model);
    }

    @GetMapping("profile/{id}")
    public ModelAndView getClassInfo(@PathVariable("id") Long classId, Model model) {
        Class c = classService.getClassById(classId);
        for (User user: c.getStudentList()){
            user.setTmpField("False");
            userService.saveUser(user);
        }
        for (User user: c.getAttentionList()){
            user.setTmpField("True");
            userService.saveUser(user);
        }
        c = classService.getClassById(classId);
        model.addAttribute("class", c);
        return new ModelAndView("classspace/profile", "classModel", model);
    }

    @GetMapping("inputScore/{id}")
    public ModelAndView getInputScore(@PathVariable("id") Long classId, Model model) {
        Class c = classService.getClassById(classId);
        String currentTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        Exam exam = new Exam(c.getId(), currentTime, c.getStudentList());
        model.addAttribute("class", c);
        model.addAttribute("exam", exam);
        return new ModelAndView("classspace/inputScore", "classModel", model);
    }

//    @PostMapping("inputScore/{classid}")
//    public ModelAndView inputScore(@PathVariable("classid") Long classId, HttpServletRequestWrapper requestWrapper) {
//        System.out.println(requestWrapper.getQueryString());
//        System.out.println(requestWrapper.getRequestURI());

//        System.out.println(exam);
//        Class c = classService.getClassById(classId);
//        c.setExamList(c.addExam(exam));
//        classService.saveClass(c);

//        return new ModelAndView("redirect:/c/profile/"+classId);
//    }

    @GetMapping("submit/{classid}")
    public ModelAndView inputScore(@PathVariable("classid") Long classId, HttpServletRequestWrapper requestWrapper) {
        String queryString = requestWrapper.getQueryString();
        ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(queryString.split("&")));
        List<Score> scoreList = new ArrayList<>();

        while (!stringList.isEmpty()) {
            Long studentId = Long.parseLong(stringList.remove(0).split("=")[1]);
            User student = userService.getUserById(studentId);
            double score = Double.parseDouble(stringList.remove(0).split("=")[1]);
            Score newScore = new Score(student, score);
            scoreService.save(newScore);
            scoreList.add(newScore);
        }

        Class c = classService.getClassById(classId);
        String currentTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        Exam exam = new Exam(c.getId(), currentTime, c.getStudentList());
        exam.setScoreList(scoreList);
        examService.save(exam);
        c.setExamList(c.addExam(exam));
        classService.saveClass(c);

        return new ModelAndView("redirect:/c/profile/" + classId);
    }

    @RequestMapping(value="/getExams/{id}", method = RequestMethod.GET, produces = "application/json")
    public String examStat(@PathVariable("id") Long id) throws JsonProcessingException {
        System.out.println("获取数据");
        Class c = classService.getClassById(id);
        ObjectMapper mapper = new ObjectMapper();
        List<String> returnList = new ArrayList<>();
        for (Exam exam: c.getExamList()){
            returnList.add(exam.getCreateTime());
        }
        String json = mapper.writeValueAsString(c.getExamList());
        return json;
    }


    @GetMapping("/attention/{classid}/{userid}")
    public ModelAndView addAttention(@PathVariable("classid") Long classid, @PathVariable("userid") Long userid){
        Class c = classService.getClassById(classid);
        User student = userService.getUserById(userid);
        c.getAttentionList().add(student);
        classService.saveClass(c);

        return new ModelAndView("redirect:/c/profile/"+classid);
    }
}
