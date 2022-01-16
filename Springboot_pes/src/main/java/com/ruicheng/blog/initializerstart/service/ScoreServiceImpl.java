package com.ruicheng.blog.initializerstart.service;

import com.ruicheng.blog.initializerstart.domain.Score;
import com.ruicheng.blog.initializerstart.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/16 18:26
 */
@Service
public class ScoreServiceImpl implements ScoreService{
    @Autowired
    ScoreRepository scoreRepository;

    public void save(Score score){
        scoreRepository.save(score);
    }
}
