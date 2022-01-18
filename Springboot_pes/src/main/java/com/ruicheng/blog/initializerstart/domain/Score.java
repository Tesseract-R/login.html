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

    @OneToOne
    private User student;

    private double score;

    public Score(User user) {
        this.score = 0.0;
        this.student = user;
    }

    public Score(User user, double score) {
        this.score = score;
        this.student = user;
    }

    protected Score() {
    }

    public Long getStudentId() {
        return Long.parseLong(student.getPid());
    }

    public String getStudentPid(){
        return this.student.getPid();
    }


}
