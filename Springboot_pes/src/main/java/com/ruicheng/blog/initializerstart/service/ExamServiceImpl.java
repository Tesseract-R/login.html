package com.ruicheng.blog.initializerstart.service;

import com.ruicheng.blog.initializerstart.domain.Exam;
import com.ruicheng.blog.initializerstart.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/16 18:28
 */
@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamRepository examRepository;

    @Override
    public void save(Exam exam) {
        examRepository.save(exam);
    }

//    @Override
//    public List<Exam> getExamByClassId(Long classId) {
//        List<Exam> examList = examRepository.findAll();
//        List<Exam> rtnList = new ArrayList<>();
//        for (Exam exam: examList){
//            if (exam.getClassId().equals(classId))
//                rtnList.add(exam);
//        }
//        rtnList.sort(new Comparator<Exam>() {
//            @Override
//            public int compare(Exam o1, Exam o2) {
//                return (int) (o1.getId()-o2.getId());
//            }
//        });
//        return rtnList;
//    }
}
