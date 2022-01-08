package com.ruicheng.blog.initializerstart.repository;

import com.ruicheng.blog.initializerstart.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 资源库：用户的存储和操作
 * @author ：Ruicheng
 * @date ：Created in 2021/11/12 11:12
 */
public interface UserRepository {
    User saveOrUpdateUser(User user);

    /**
     * 根据ID删除用户
     * @param id
     */
    void deleteUser(Long id);
    
    User getUserbyId(Long id);

    /**
     * 返回用户列表
     * @return
     */
    List<User> listUsers();


    //  增删改查
    UserDetails findByEmail(String email);

    UserDetails findByPid(String pid);

    Page<User> findByNameLike(String name, Pageable pageable);

    List<User> findAll();

    User getOne(Long id);

    User save(User user);

    void deleteInBatch(List<User> users);

    void delete(Long id);
}
