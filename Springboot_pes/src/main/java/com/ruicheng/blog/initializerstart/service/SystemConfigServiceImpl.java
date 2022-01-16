package com.ruicheng.blog.initializerstart.service;

import com.ruicheng.blog.initializerstart.domain.SystemConfig;
import com.ruicheng.blog.initializerstart.repository.SystemConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统管理服务
 *
 * @author ：Ruicheng
 * @date ：Created in 2022/1/15 18:32
 */
@Service
public class SystemConfigServiceImpl {
    @Autowired
    SystemConfigRepository systemConfigRepository;

    public SystemConfig getSystemConfig() {
        return (SystemConfig) systemConfigRepository.findById(1).get();
    }

    public void setSystemConfig(SystemConfig systemConfig) {
        systemConfigRepository.save(systemConfig);
    }


}
