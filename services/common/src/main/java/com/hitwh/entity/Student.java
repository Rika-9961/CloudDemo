package com.hitwh.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hitwh.entity.enumeration.EducationBackgroundType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_background", nullable = false)
    private EducationBackgroundType educationBackground;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "dis_score")
    private Double disScore = 0.0;

    @Column(name = "dis_time")
    private int disTime = 0;

    @Column(name = "last_time")
    private LocalDateTime lastTime;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted = false;

    @ManyToOne(targetEntity = Class.class)
    @JoinColumn(name = "class_id")
    private Class clazz;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "student_course",
            joinColumns = {@JoinColumn(name = "student_id", nullable = false, referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id", nullable = false, referencedColumnName = "id")})
    private List<Course> courses = new ArrayList<>();

    @PreUpdate
    protected void updateLastTime() {
        this.lastTime = LocalDateTime.now();
    }

}