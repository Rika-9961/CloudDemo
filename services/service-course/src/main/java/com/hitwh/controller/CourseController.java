package com.hitwh.controller;

import com.hitwh.dto.pojo.CourseDTO;
import com.hitwh.dto.pojo.CourseListDTO;
import com.hitwh.dto.vo.CourseListVO;
import com.hitwh.dto.vo.CourseVO;
import com.hitwh.entity.Course;
import com.hitwh.service.CourseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 课程管理
 */
@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    /**
     * 课程查询
     *
     * @param classesListDTO 课程分页查询 {@link CourseListDTO}
     * @return 分页查询的课程信息
     */
    @PostMapping("/list")
    public ResponseEntity<CourseListVO> getPage(@RequestBody CourseListDTO classesListDTO) {
        Pageable pageable = PageRequest.of(classesListDTO.getPageNumber(), classesListDTO.getPageSize());
        CourseListVO classesList = courseService.searchCourse(
                classesListDTO.getName(),
                classesListDTO.getCourseType(),
                pageable);
        return ResponseEntity.ok().body(classesList);
    }

    /**
     * 查询回显
     *
     * @param id 课程id
     * @return 该id课程信息
     */

    @GetMapping("/show/{id}")
    public ResponseEntity<CourseVO> show(@PathVariable Long id) {
        CourseVO classes = courseService.getCourseById(id);
        return ResponseEntity.status(HttpStatus.OK).body(classes);
    }

    /**
     * 修改课程
     *
     * @param updateCourse 修改的课程信息 {@link Course}
     * @return 修改后的课程信息
     */
    @PutMapping("/update")
    public ResponseEntity<CourseVO> update(@RequestBody CourseDTO updateCourse) {
        CourseVO classes = courseService.updateCourse(updateCourse);
        if (classes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(classes);
    }

    /**
     * 删除课程
     *
     * @param id 删除的课程id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("删除");
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 新增课程
     *
     * @param classes 课程信息 {@link Course}
     * @return 新增的课程信息
     */
    @PostMapping("/add")
    public ResponseEntity<CourseVO> add(@RequestBody CourseDTO classes) {
        CourseVO addCourse = courseService.addCourse(classes);
        return ResponseEntity.status(HttpStatus.CREATED).body(addCourse);
    }

}
