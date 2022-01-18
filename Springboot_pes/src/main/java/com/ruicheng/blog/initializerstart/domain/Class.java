package com.ruicheng.blog.initializerstart.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * 课程实体
 *
 * @author ：Ruicheng
 * @date ：Created in 2022/1/13 20:41
 */
@Getter
@Setter
@Entity
public class Class implements Serializable {
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id;

    @NotEmpty(message = "课程名不能为空")
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20) // 映射为字段，值不能为空
    private String classname;

    @Size(min = 2, max = 20)
    @Column(length = 20) // 映射为字段
    private String creator;

    @NotEmpty
    private String createTime;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "class_t", joinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> teachers; // 指导教师

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "class_s", joinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> students; // 选这门课的学生

    @ManyToMany
    private Set<User> attentionList;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Set<Exam> examList;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Set<Evaluation> evaluationList;

    private int userNum;

    @Size(min = 2, max = 200)
    @Column(nullable = false, length = 20) // 映射为字段，值不能为空
    private String info;

    protected Class() {
    }

    public Class(Long id, String classname, String creator, String createTime, String info) {
        this.id = id;
        this.classname = classname;
        this.info = info;
        this.creator = creator;
        this.createTime = createTime;
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
        this.examList = new HashSet<>();
        this.userNum = 0;
    }

    public List<Exam> getExamList() {
        ArrayList<Exam> rtnList = new ArrayList<>(this.examList);
        rtnList.sort(new Comparator<Exam>() {
            @Override
            public int compare(Exam o1, Exam o2) {
                return (int) (o1.getId() - o2.getId());
            }
        });
        return rtnList;
    }

    public Set<Exam> addExam(Exam exam) {
        examList.add(exam);
        return this.examList;
    }

    public void addTeacher(User newTeacher) {
        this.teachers.add(newTeacher);
        this.userNum += 1;
    }

    public void addStudent(User newStudent) {
        this.students.add(newStudent);
        this.userNum += 1;
    }

    public void removeStudent(User user) {
        this.students.removeIf(u -> user.getId().equals(u.getId()));
        this.userNum -= 1;
        System.out.println(this.students);
    }

    public void removeTeacher(User user) {
        this.teachers.removeIf(u -> user.getId().equals(u.getId()));
        this.userNum -= 1;
    }

    public List<User> getStudents() {
        List<User> studentList = new ArrayList<>(this.students);
        studentList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.parseInt(o1.getPid()) - Integer.parseInt(o2.getPid());
            }
        });
        return studentList;
    }

}
