package com.ruicheng.blog.initializerstart.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/16 15:54
 */
@Entity
public class Exam {
    @Id
    private Long Id;

    @OneToMany
    private List<Score> scoreList;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }
}
