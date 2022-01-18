package com.ruicheng.blog.initializerstart.repository;

import com.ruicheng.blog.initializerstart.domain.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/17 22:53
 */
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
