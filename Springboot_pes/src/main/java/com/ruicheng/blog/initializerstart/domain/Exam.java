package com.ruicheng.blog.initializerstart.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author ：Ruicheng
 * @date ：Created in 2022/1/16 15:54
 */
@Getter
@Setter
@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long Id;
    @NotEmpty
    private String createTime;
    @OneToMany
    private List<Score> scoreList;

    private Long classId; // 哪个班考的试

    protected Exam() {};

    public Exam(Long classId, String createTime, List<User> studentList) {
        this.createTime = createTime;
        this.classId = classId;
        this.scoreList = new ArrayList<>();
        for (User u : studentList) {
            this.scoreList.add(new Score(u));
        }
    }

    public double getScoreByPid(String pid) {
        for (Score s : this.scoreList) {
            if (s.getStudentPid().equals(pid))
                return s.getScore();
        }
        return 0;
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
}
