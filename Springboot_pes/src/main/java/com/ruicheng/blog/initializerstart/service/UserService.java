package com.ruicheng.blog.initializerstart.service;

import com.ruicheng.blog.initializerstart.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * User 服务接口.
 *
 * @author Ruicheng
 */
public interface UserService {
    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    User saveUser(User user);

    void saveAll(Collection<User> userList);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    void removeUser(Long id);

    /**
     * 删除列表里面的用户
     *
     * @param users
     * @return
     */
    void removeUsersInBatch(List<User> users);

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 获取用户列表
     *
     * @return
     */
    List<User> listUsers();

    /**
     * 根据用户名进行分页模糊查询
     *
     * @param name, pageable
     * @return
     */
    Page<User> listUsersByNameLike(String name, Pageable pageable);


    boolean verifyUser(User user);
}
