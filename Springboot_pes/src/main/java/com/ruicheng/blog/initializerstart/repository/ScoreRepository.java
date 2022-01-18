package com.ruicheng.blog.initializerstart.repository;

import com.ruicheng.blog.initializerstart.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/16 18:25
 */
@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
