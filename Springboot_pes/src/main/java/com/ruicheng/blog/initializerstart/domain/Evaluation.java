package com.ruicheng.blog.initializerstart.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 评价
 *
 * @author ：Ruicheng
 * @date ：Created in 2022/1/17 22:45
 */
@Getter
@Setter
@Entity
public class Evaluation {

    @Id
    private Long id;

    @OneToOne
    private User student;

    private String evaluation;

    protected Evaluation(){};

    public Evaluation(User user) {
        this.evaluation = "";
        this.student = user;
    }

    public Evaluation(User user, String evaluation) {
        this.evaluation = evaluation;
        this.student = user;
    }

}
