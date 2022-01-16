package com.ruicheng.blog.initializerstart.repository;

import com.ruicheng.blog.initializerstart.domain.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/15 18:33
 */
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Integer> {
}
