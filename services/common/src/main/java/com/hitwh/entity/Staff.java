package com.hitwh.entity;


import com.hitwh.entity.enumeration.StaffType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "staff")
public class Staff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "staff_id", nullable = false, unique = true)
    private String staffId;

//    @Column(name = "picture")
//    private String picture;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "job")
    private StaffType job;

    @Column(name = "start")
    private LocalDate start;

    @Column(name = "last_time")
    private LocalDateTime lastTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToMany(mappedBy = "teachers", fetch = FetchType.EAGER)
    private List<Course> courses  = new ArrayList<>();

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted = false;

    @PreUpdate
    protected void updateLastTime(){
        this.lastTime = LocalDateTime.now();
    }


}