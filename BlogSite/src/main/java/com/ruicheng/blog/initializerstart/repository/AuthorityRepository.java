package com.ruicheng.blog.initializerstart.repository;

import com.ruicheng.blog.initializerstart.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Authority 仓库.
 *
 * @author Ruicheng
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
