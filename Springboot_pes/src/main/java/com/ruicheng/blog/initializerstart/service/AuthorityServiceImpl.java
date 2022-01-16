/**
 *
 */
package com.ruicheng.blog.initializerstart.service;

import com.ruicheng.blog.initializerstart.domain.Authority;
import com.ruicheng.blog.initializerstart.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Authority 服务.
 *
 * @since 1.0.0 2017年3月30日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.getById(id);
    }

}
