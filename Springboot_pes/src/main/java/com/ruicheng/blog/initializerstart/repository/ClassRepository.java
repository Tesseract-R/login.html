package com.ruicheng.blog.initializerstart.repository;

import com.ruicheng.blog.initializerstart.domain.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * 课程资源库
 *
 * @author ：Ruicheng
 * @date ：Created in 2022/1/13 21:16
 */
@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    default Class findOneById(Long id) {
        Optional<Class> classList = findById(id);
        return classList.orElse(null);
    }

    Page<Class> findByClassnameLike(String name, Pageable pageable);
}
