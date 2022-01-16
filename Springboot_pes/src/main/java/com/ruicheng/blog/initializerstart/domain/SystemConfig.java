package com.ruicheng.blog.initializerstart.domain;

import org.springframework.context.annotation.Scope;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 系统配置
 *
 * @author ：Ruicheng
 * @date ：Created in 2022/1/15 18:01
 */
@Entity
@Scope("Singleton")
public class SystemConfig {
    private static final SystemConfig systemConfig = new SystemConfig();
    @Id
    private int id;
    private int reportSendReportDate; // 发送定期报告  （单位：天）
    private int warningSendReportDate; // 发送监测警告周期
    private int warningSendReportRate;   // 给多少比例的学生发送

    public SystemConfig() {
        this.id = 1;
        this.reportSendReportDate = 7;
        this.warningSendReportDate = 7;
        this.warningSendReportRate = 10;
    }

    public static SystemConfig getSystemConfig() {
        return systemConfig;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReportSendReportDate() {
        return reportSendReportDate;
    }

    public void setReportSendReportDate(int reportSendReportDate) {
        this.reportSendReportDate = reportSendReportDate;
    }

    public int getWarningSendReportDate() {
        return warningSendReportDate;
    }

    public void setWarningSendReportDate(int warningSendReportDate) {
        this.warningSendReportDate = warningSendReportDate;
    }

    public int getWarningSendReportRate() {
        return warningSendReportRate;
    }

    public void setWarningSendReportRate(int warningSendReportRate) {
        this.warningSendReportRate = warningSendReportRate;
    }
}
