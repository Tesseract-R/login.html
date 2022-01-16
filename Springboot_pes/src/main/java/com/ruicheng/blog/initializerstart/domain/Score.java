package com.ruicheng.blog.initializerstart.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 成绩表
 *
 * @author ：Ruicheng
 * @date ：Created in 2022/1/16 15:37
 */
@Entity
public class Score {
    @Id
    private Long Id;

    private Long studentId;
    private Long score;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
