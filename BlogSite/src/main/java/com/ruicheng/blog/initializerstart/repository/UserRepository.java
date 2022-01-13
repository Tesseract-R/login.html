package com.ruicheng.blog.initializerstart.repository;

import com.ruicheng.blog.initializerstart.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

/**
 * 资源库：用户的存储和操作
 *
 * @author ：Ruicheng
 * @date ：Created in 2021/11/12 11:12
 */
public interface UserRepository extends JpaRepository<User, Long> {

    default User findOneById(Long id) {
        Optional<User> userList = findById(id);
        return userList.orElse(null);
    }

    default UserDetails findByEmail(String email) {
        List<User> userList = (List<User>) findAll();
        for (User user : userList) {
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    default UserDetails findByPid(String pid) {
        List<User> userList = (List<User>) findAll();
        for (User user : userList) {
            if (user.getPid().equals(pid))
                return user;
        }
        return null;
    }

    default User findByUsername(String username) {
        List<User> userList = (List<User>) findAll();
        for (User user : userList) {
            if (user.getUsername().equals(username))
                return user;
            if (user.getPid().equals(username))
                return user;
            if (user.getEmail().equals(username))
                return user;
        }
        return null;
    }

    ;

    Page<User> findByUsernameLike(String username, Pageable pageable);
}
