package com.ruicheng.blog.initializerstart.controller;

import com.ruicheng.blog.initializerstart.domain.*;
import com.ruicheng.blog.initializerstart.domain.Class;
import com.ruicheng.blog.initializerstart.service.ClassService;
import com.ruicheng.blog.initializerstart.service.ExamService;
import com.ruicheng.blog.initializerstart.service.UserService;
import com.ruicheng.blog.initializerstart.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


/**
 * 用户主页空间控制器.
 *
 * @author Ruicheng
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    @Autowired
    private ExamService examService;

    @GetMapping
    public String redirectUserSpace() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        System.out.println(user);
        return "redirect:/u/" + user.getId() + "/profile";
    }

//	@GetMapping("/{username}")
//	public String userSpace(@PathVariable("username") String username) {
//		System.out.println("username:" + username);
//		return "u";
//	}

    @GetMapping("/{id}/profile")
//    @PreAuthorize("authentication.id.equals(#id) or hasRole('ADMIN')")
    public ModelAndView profile(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        User targetUser = userService.getUserById(id);
        model.addAttribute("user", targetUser);

        HashMap<Class, List<Double>> scoreMap = new HashMap<>();
        HashMap<Class, List<Evaluation>> evaMap = new HashMap<>();
        for (Class c: classService.listClasses()){
            if ((classService.hasUserAsTeacher(c,currentUser) || currentUser.getId().equals(targetUser.getId())) && classService.hasUserAsStudent(c,targetUser)){
                List<Exam> exam = c.getExamList();
                List<Double> tmpList = new ArrayList<>();
                for (Exam e: exam){
                    tmpList.add(e.getScoreByPid(targetUser.getPid()));
                }
                scoreMap.put(c,tmpList);

                Set<Evaluation> evaluationList = c.getEvaluationList();
                List<Evaluation> tmpListEva = new ArrayList<>();
                for (Evaluation eva: evaluationList){
                    if (eva.getStudent().getId().equals(targetUser.getId()))
                        tmpListEva.add(eva);
                }
                tmpListEva.sort(new Comparator<Evaluation>() {
                    @Override
                    public int compare(Evaluation o1, Evaluation o2) {
                        return (int) (o2.getId() - o1.getId());
                    }
                });
                evaMap.put(c,tmpListEva);
            }
        }
        model.addAttribute("classEvalistMap", evaMap);
        model.addAttribute("classScorelistMap", scoreMap);
        return new ModelAndView("/userspace/profile", "userModel", model);
    }

    /**
     * 保存个人设置
     *
     * @param user
     * @param id
     * @return
     */
    @PostMapping("/{id}/profile")
    @PreAuthorize("authentication.id.equals(#id)")
    public String saveProfile(@PathVariable("id") Long id, User user) {
        User originalUser = userService.getUserById(user.getId());
        originalUser.setEmail(user.getEmail());
        originalUser.setUsername(user.getUsername());

        // 判断密码是否做了变更
        String rawPassword = originalUser.getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(user.getPassword());
        boolean isMatch = encoder.matches(rawPassword, encodePasswd);
        if (!isMatch) {
            originalUser.setEncodePassword(user.getPassword());
        }

        userService.saveUser(originalUser);
        return "redirect:/u/" + id + "/profile";
    }

    /**
     * 获取编辑头像的界面
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("/userspace/avatar", "userModel", model);
    }


    /**
     * 获取编辑头像的界面
     *
     * @param username
     * @param user
     * @return
     */
    @PostMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, User user) {
        String avatarUrl = user.getAvatar();
        User originalUser = userService.getUserById(user.getId());
        originalUser.setAvatar(avatarUrl);
        userService.saveUser(originalUser);

        return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
    }


    // blog => 学生成绩+评价
    @GetMapping("/{username}/blogs")
    public String listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                   @RequestParam(value = "category", required = false) Long category,
                                   @RequestParam(value = "keyword", required = false) String keyword) {

        if (category != null) {

            System.out.print("category:" + category);
            System.out.print("selflink:" + "redirect:/u/" + username + "/blogs?category=" + category);
            return "/u";

        } else if (keyword != null && keyword.isEmpty() == false) {

            System.out.print("keyword:" + keyword);
            System.out.print("selflink:" + "redirect:/u/" + username + "/blogs?keyword=" + keyword);
            return "/u";
        }

        System.out.print("order:" + order);
        System.out.print("selflink:" + "redirect:/u/" + username + "/blogs?order=" + order);
        return "/u";
    }

    // 历史评价
    @GetMapping("/{username}/blogs/{id}")
    public String listBlogsByOrder(@PathVariable("id") Long id) {

        System.out.print("blogId:" + id);
        return "/blog";
    }


    @GetMapping("/{username}/blogs/edit")
    public String editBlog() {

        return "echart";
    }
}
