package com.hitwh.service.impl;

import com.hitwh.dto.pojo.CourseDTO;
import com.hitwh.dto.vo.CourseListVO;
import com.hitwh.dto.vo.CourseVO;
import com.hitwh.entity.Course;
import com.hitwh.entity.Staff;
import com.hitwh.entity.enumeration.CourseType;
import com.hitwh.feign.StaffFeignClient;
import com.hitwh.repository.CourseRepository;
import com.hitwh.service.CourseService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private StaffFeignClient staffFeignClient;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public CourseListVO searchCourse(String name, CourseType courseType, Pageable pageable) {
        Specification<Course> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            Optional.ofNullable(courseType)
                    .map(ct -> criteriaBuilder.equal(root.get("courseType"), ct))
                    .ifPresent(predicates::add);
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        Long total = courseRepository.count(specification);
        Page<CourseVO> map = courseRepository.findAll(specification, pageable).map(CourseVO::new);
        return new CourseListVO(total, map.getContent());
    }

    @Override
    public CourseVO getCourseById(Long id) {
        Optional<Course> optional = courseRepository.findByIdAndDeletedFalse(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("查无此人");
        }
        return new CourseVO(optional.get());
    }

    @Override
    public void deleteCourse(Long id) {
        Optional<Course> optional = courseRepository.findByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            Course course = optional.get();
            course.setDeleted(true);
            course.setLastTime(LocalDateTime.now());
            courseRepository.save(course);
        }
    }

    @Override
    public CourseVO addCourse(CourseDTO course) {
        List<Staff> teachers = new ArrayList<>();
        for(Long i : course.getTeacherId()) {
            Optional.ofNullable(modelMapper.map(staffFeignClient.show(i), Staff.class)).ifPresent(teachers::add);
//            staffRepository.findByIdAndDeletedFalse(i).ifPresent(teachers::add);
        }
        Course addCourse = new Course();
        addCourse.setName(course.getName());
        addCourse.setCourseId(course.getCourseId());
        addCourse.setCourseType(course.getCourseType());
        addCourse.setAddress(course.getAddress());
        addCourse.setTime(course.getTime());
        addCourse.setClassHour(course.getClassHour());
        addCourse.setCreditHour(course.getCreditHour());
        addCourse.setCapacity(course.getCapacity());
        addCourse.setTeachers(teachers);
        Optional<Course> optional = courseRepository.findByNameAndDeletedFalse(addCourse.getName());
        if (optional.isPresent()) {
            throw new RuntimeException("员工已存在");
        }
        addCourse.setLastTime(LocalDateTime.now());
        return new CourseVO(courseRepository.save(addCourse));
    }

    @Override
    public CourseVO updateCourse(CourseDTO updateCourse) {
        Optional<Course> optional = courseRepository.findByIdAndDeletedFalse(updateCourse.getId());
        if (optional.isEmpty()) {
            return null;
        }
        Course course = optional.get();
        Optional.ofNullable(updateCourse.getName()).ifPresent(course::setName);
        Optional.ofNullable(updateCourse.getCourseId()).ifPresent(course::setCourseId);
        Optional.ofNullable(updateCourse.getCourseType()).ifPresent(course::setCourseType);
        Optional.ofNullable(updateCourse.getAddress()).ifPresent(course::setAddress);
        Optional.ofNullable(updateCourse.getTime()).ifPresent(course::setTime);
        Optional.ofNullable(updateCourse.getClassHour()).ifPresent(course::setClassHour);
        Optional.ofNullable(updateCourse.getCreditHour()).ifPresent(course::setCreditHour);
        Optional.ofNullable(updateCourse.getCapacity()).ifPresent(course::setCapacity);
        List<Staff> teachers = new ArrayList<>();
        for(Long i : updateCourse.getTeacherId()) {
            Optional.ofNullable(modelMapper.map(staffFeignClient.show(i), Staff.class)).ifPresent(teachers::add);
        }
        course.setTeachers(teachers);
        course.setLastTime(LocalDateTime.now());
        return new CourseVO(courseRepository.save(course));
    }
    @Override
    public Integer getTotalNumber() {
        return courseRepository.findAllByDeletedFalse().size();
    }
}
