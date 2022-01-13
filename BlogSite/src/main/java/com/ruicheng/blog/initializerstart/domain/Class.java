package com.ruicheng.blog.initializerstart.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 课程实体
 *
 * @author ：Ruicheng
 * @date ：Created in 2022/1/13 20:41
 */
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

    private int userNum;

    @Size(min = 2, max = 200)
    @Column(nullable = false, length = 20) // 映射为字段，值不能为空
    private String info;

    protected Class() {
    }

    ;

    public Class(Long id, String classname, String creator, String createTime, String info) {
        this.id = id;
        this.classname = classname;
        this.info = info;
        this.creator = creator;
        this.createTime = createTime;
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
        this.userNum = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Set<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<User> teachers) {
        this.teachers = teachers;
    }

    public void addTeacher(User newTeacher) {
        this.teachers.add(newTeacher);
        this.userNum += 1;
    }

    public void addStudent(User newStudent) {
        this.students.add(newStudent);
        this.userNum += 1;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }
}
