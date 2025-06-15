package com.hitwh.entity;


import com.hitwh.entity.enumeration.CourseType;
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
@Table(name = "course")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "course_id", unique = true, nullable = false)
    private String courseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_type", nullable = false)
    private CourseType courseType;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "start", nullable = false)
    private String time;

    @Column(name = "class_hour", nullable = false)
    private Double classHour;

    @Column(name = "credit_hour", nullable = false)
    private Double creditHour;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "course_teacher",
            joinColumns = @JoinColumn(name = "course_id", nullable = false))
    private List<Staff> teachers = new ArrayList<>();

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    private List<Student> students = new ArrayList<>();

    @Column(name = "last_time")
    private LocalDateTime lastTime;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted = false;

    @PreUpdate
    protected void updateLastTime() {
        this.lastTime = LocalDateTime.now();
    }


}
