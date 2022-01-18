package com.ruicheng.blog.initializerstart.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    
    private String createTime;

    @OneToOne
    private Class c;

    @Column(columnDefinition = "varchar(2048)")
    private String evaluation;

    protected Evaluation(){};

    public Evaluation(Class c, User user) {
        this.evaluation = "";
        this.c = c;
        this.student = user;
    }

    public Evaluation(Class c, User user, String evaluation) {
        this.evaluation = evaluation;
        this.student = user;
        this.c = c;
    }

}
