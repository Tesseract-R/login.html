package com.ruicheng.blog.initializerstart.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Echarts
 *
 * @author ：Ruicheng
 * @date ：Created in 2022/1/17 19:36
 */
@Data
@Entity
@Table(name = "echarts")
public class Echarts  implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private Integer value;
}
