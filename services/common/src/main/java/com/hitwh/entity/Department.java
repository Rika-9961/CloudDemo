package com.hitwh.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_time")
    private LocalDateTime lastTime;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted = false;


    @PreUpdate
    protected void updateLastTime(){
        this.lastTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastTime=" + lastTime +
                ", deleted=" + deleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getLastTime(), that.getLastTime()) && Objects.equals(getDeleted(), that.getDeleted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLastTime(), getDeleted());
    }

}