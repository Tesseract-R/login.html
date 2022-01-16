package com.ruicheng.blog.initializerstart.service;

import com.ruicheng.blog.initializerstart.domain.Exam;
import com.ruicheng.blog.initializerstart.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/16 18:28
 */
@Service
public class ExamServiceImpl implements ExamService{
    @Autowired
    ExamRepository examRepository;

    public void save(Exam exam){
        examRepository.save(exam);
    }
}
