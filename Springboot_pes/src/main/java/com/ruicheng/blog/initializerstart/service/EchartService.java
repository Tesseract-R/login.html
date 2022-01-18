package com.ruicheng.blog.initializerstart.service;

import com.ruicheng.blog.initializerstart.domain.Echarts;
import com.ruicheng.blog.initializerstart.repository.EchartsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/17 19:41
 */
@Service
public class EchartService {
    @Autowired
    EchartsRepository echartsRepository;
    public List<Echarts> getdemo(){
        return echartsRepository.findAll();
    }
}
