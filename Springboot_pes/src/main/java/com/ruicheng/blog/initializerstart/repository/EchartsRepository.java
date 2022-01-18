package com.ruicheng.blog.initializerstart.repository;

import com.ruicheng.blog.initializerstart.domain.Echarts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/17 19:38
 */
@Repository
public interface EchartsRepository extends JpaRepository<Echarts, Integer> {
}
