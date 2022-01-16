package com.ruicheng.blog.initializerstart.service;

import com.ruicheng.blog.initializerstart.domain.Class;
import com.ruicheng.blog.initializerstart.domain.User;
import com.ruicheng.blog.initializerstart.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/13 21:34
 */
@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Transactional
    @Override
    public Class saveClass(Class c) {
        System.out.println("课程存储");
        System.out.println(c.getStudents());
        return classRepository.save(c);
    }

    @Transactional
    @Override
    public void removeClass(Class c) {
        classRepository.delete(c);
    }

    @Transactional
    @Override
    public void removeClass(Long id) {
        classRepository.delete(classRepository.findOneById(id));
    }

    @Transactional
    @Override
    public Class getClassById(Long id) {
        return classRepository.findOneById(id);
    }

    @Transactional
    @Override
    public List<Class> listClasses() {
        return classRepository.findAll();
    }

    @Transactional
    @Override
    public Page<Class> listClassesByNameLike(String name, Pageable pageable) {
        // 模糊查询
        name = "%" + name + "%";
        Page<Class> classes = classRepository.findByClassnameLike(name, pageable);
        return classes;
    }

    @Override
    public void removeUserFromClass(Class c, String role, User user) {
        if (role.equals("Student")){
            c.removeStudent(user);
        }
        if (role.equals("Admin")) {
            c.removeTeacher(user);
        }
        System.out.println("课程成员删除: " + user);
    }

    @Override
    public Boolean hasUserAsTeacher(Class c, User u) {
        for (User user: c.getTeachers()){
            if (user.getId().equals(u.getId()))
                    return true;
        }
        return false;
    }

    @Override
    public Boolean hasUserAsStudent(Class c, User u) {
        for (User user: c.getStudents()){
            if (user.getId().equals(u.getId()))
                return true;
        }
        return false;
    }
}
