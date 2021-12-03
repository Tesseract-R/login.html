package com.ruicheng.blog.initializerstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruicheng.blog.initializerstart.domain.Authority;

import java.util.Optional;


/**
 * Authority 仓库.
 * @author Ruicheng
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
}
