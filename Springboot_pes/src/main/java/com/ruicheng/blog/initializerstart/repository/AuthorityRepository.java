package com.ruicheng.blog.initializerstart.repository;

import com.ruicheng.blog.initializerstart.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Authority 仓库.
 *
 * @author Ruicheng
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
