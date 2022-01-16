package com.ruicheng.blog.initializerstart.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/16 15:54
 */
@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long Id;

    @NotEmpty
    private String createTime;

    @OneToMany
    private List<Score> scoreList;

    protected Exam(){};

    public Exam(String createTime, List<User> studentList){
        this.createTime = createTime;
        this.scoreList = new ArrayList<>();
        for(User u: studentList){
            this.scoreList.add(new Score(u));
        }
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public List<Score> getScoreList() {
        scoreList.sort(new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return (int) (o1.getStudentId() - o2.getStudentId());
            }
        });
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }
}
