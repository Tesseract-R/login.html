package com.ruicheng.blog.initializerstart.repository;

import com.ruicheng.blog.initializerstart.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 用户资源库 实现类
 * @author ：Ruicheng
 * @date ：Created in 2021/11/15 12:38
 */
@Repository
public class UserRepositoryImpl implements UserRepository{
    private static AtomicLong counter =new AtomicLong(); // 用来计数，保证用户唯一ID
    private final ConcurrentMap<Long, User> userMap = new ConcurrentHashMap<>();

    @Override
    public User saveOrUpdateUser(User user) {
        Long id = user.getId();
        if (id == null) {
            id = counter.incrementAndGet();
            user.setId(id);
        }
        this.userMap.put(id, user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        this.userMap.remove(id);
    }

    @Override
    public User getUserbyId(Long id) {
        return this.userMap.get(id);
    }

    @Override
    public List<User> listUsers() {
        return new ArrayList<User>(this.userMap.values());
    }

    @Override
    public UserDetails findByUsername(String username) {
        return null;
    }

    @Override
    public Page<User> findByNameLike(String name, Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User getOne(Long id) {
        return null;
    }

    @Override
    public User save(User user) {
        return saveOrUpdateUser(user);
    }

    @Override
    public void deleteInBatch(List<User> users) {

    }

    @Override
    public void delete(Long id) {

    }
}
