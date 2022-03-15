package com.ruicheng.blog.initializerstart.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 成绩表
 *
 * @author ：Ruicheng
 * @date ：Created in 2022/1/16 15:37
 */
@Getter
@Setter
@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long Id;

    private String studentPid;

    private double score;

    public Score(User user) {
        this.score = 0.0;
        this.studentPid = user.getPid();
    }

    public Score(User user, double score) {
        this.score = score;
        this.studentPid = user.getPid();
    }

    protected Score() {
    }

    public Long getStudentId() {
        return Long.parseLong(studentPid);
    }

    public String getStudentPid(){
        return this.studentPid;
    }
}
