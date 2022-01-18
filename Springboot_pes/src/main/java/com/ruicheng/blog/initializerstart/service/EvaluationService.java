package com.ruicheng.blog.initializerstart.service;

import com.ruicheng.blog.initializerstart.domain.Evaluation;
import com.ruicheng.blog.initializerstart.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/17 22:53
 */
@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    public void save(Evaluation evaluation){
        evaluationRepository.save(evaluation);
    }
}
